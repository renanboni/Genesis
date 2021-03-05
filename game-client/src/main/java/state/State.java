package state;

import core.Position;
import core.Size;
import display.Camera;
import entity.GameObject;
import game.Game;
import game.Time;
import game.settings.GameSettings;
import gfx.SpriteLibrary;
import input.Input;
import input.KeyInputConsumer;
import input.mouse.MouseHandler;
import map.GameMap;
import io.MapIO;
import ui.UICanvas;
import ui.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class State {

    protected final List<GameObject> gameObjects;
    protected final List<UIContainer> uiContainers;
    private final GameSettings settings;
    protected GameMap gameMap;
    protected Input input;
    protected Camera camera;
    protected Time time;
    protected Size windowsSize;
    private State nextState;
    protected UICanvas uiCanvas;
    private final MouseHandler mouseHandler;
    private KeyInputConsumer keyInputConsumer;

    public State(Size windowSize, Input input, GameSettings settings) {
        this.settings = settings;
        this.windowsSize = windowSize;
        this.input = input;
        this.uiCanvas = new UICanvas(windowSize);
        this.gameObjects = new ArrayList<>();
        this.uiContainers = new ArrayList<>();
        this.camera = new Camera(windowSize);
        this.time = new Time();
        this.mouseHandler = new MouseHandler();
    }

    protected abstract void handleInput();

    public void update(Game game) {
        time.update();
        sortObjectsByPosition();
        updateGameObjects();
        camera.update(this);
        uiCanvas.update(this);
        mouseHandler.update(this);
        handleKeyInput();

        if (nextState != null) {
            game.enterState(nextState);
        }
    }

    private void handleKeyInput() {
        if (keyInputConsumer != null) {
            List<Integer> typedKeyBuffer = input.getTypedKeyBuffer();
            for (int i = 0, typedKeyBufferSize = typedKeyBuffer.size(); i < typedKeyBufferSize; i++) {
                int keyCode = typedKeyBuffer.get(i);
                keyInputConsumer.onKeyPressed(keyCode);
                input.clear();
            }
        } else {
            handleInput();
        }
    }

    private void updateGameObjects() {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update(this);
        }
    }

    private void sortObjectsByPosition() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Camera getCamera() {
        return camera;
    }

    public Time getTime() {
        return time;
    }

    public Position getRandomPosition() {
        return gameMap.getRandomPosition();
    }

    public List<GameObject> getCollidingGameObjects(GameObject gameObject) {
        return gameObjects
                .stream()
                .filter(other -> other.collidesWith(gameObject))
                .collect(Collectors.toList());
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public <T extends GameObject> List<T> getGameObjectOfClass(Class<T> clazz) {
        return gameObjects
                .stream()
                .filter(clazz::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    public void spawn(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public Input getInput() {
        return input;
    }

    public void setNextState(State nextState) {
        this.nextState = nextState;
    }

    public GameSettings getSettings() {
        return settings;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public void loadGameMap() {
        gameMap = MapIO.load();
    }

    public KeyInputConsumer getKeyInputConsumer() {
        return keyInputConsumer;
    }

    public void setKeyInputConsumer(KeyInputConsumer keyInputConsumer) {
        this.keyInputConsumer = keyInputConsumer;
    }

    public UICanvas getUiCanvas() {
        return uiCanvas;
    }

    public Size getWindowSize() {
        return windowsSize;
    }

    public void resize(Size size) {
        windowsSize = size;
        camera.resize(size);
        uiCanvas.resize(size);
    }
}
