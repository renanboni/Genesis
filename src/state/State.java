package state;

import core.Size;
import display.Camera;
import game.GameObject;
import gfx.SpriteLibrary;
import input.Input;
import map.GameMap;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected final List<GameObject> gameObjects;
    protected final SpriteLibrary spriteLibrary;
    protected GameMap gameMap;
    protected Input input;
    protected Camera camera;

    public State(Size windowSize, Input input) {
        this.input = input;
        this.gameObjects = new ArrayList<>();
        this.spriteLibrary = new SpriteLibrary();
        this.camera = new Camera(windowSize);
    }

    public void update() {
        gameObjects.forEach(GameObject::update);
        camera.update(this);
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
}
