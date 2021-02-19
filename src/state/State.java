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
import map.GameMap;
import ui.UIContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class State {

    protected final List<GameObject> gameObjects;
    protected final List<UIContainer> uiContainers;
    protected final SpriteLibrary spriteLibrary;
    private final GameSettings settings;
    protected GameMap gameMap;
    protected Input input;
    protected Camera camera;
    protected Time time;
    protected Size windowsSize;
    private State nextState;

    public State(Size windowSize, Input input, GameSettings settings) {
        this.settings = settings;
        this.windowsSize = windowSize;
        this.input = input;
        this.gameObjects = new ArrayList<>();
        this.uiContainers = new ArrayList<>();
        this.spriteLibrary = new SpriteLibrary();
        this.camera = new Camera(windowSize);
        this.time = new Time();
    }

    public void update(Game game) {
        time.update();
        sortObjectsByPosition();
        updateGameObjects();
        List.copyOf(uiContainers).forEach(uiContainer -> uiContainer.update(this));
        camera.update(this);
        handleMouseInput();

        if (nextState != null) {
            game.enterState(nextState);
        }
    }

    private void handleMouseInput() {
        if (input.isMouseClicked()) {
            System.out.printf("MOUSE CLICKED AT POSITION: x:%d y:%d\n", input.getMousePosition().intX(), input.getMousePosition().intY());
        }

        input.clearMouseClick();
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

    public SpriteLibrary getSpriteLibrary() {
        return spriteLibrary;
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
}
