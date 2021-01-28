package state;

import core.Size;
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

    public State(Input input) {
        this.input = input;
        this.gameObjects = new ArrayList<>();
        this.spriteLibrary = new SpriteLibrary();
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);
    }

    public void update() {
        gameObjects.forEach(GameObject::update);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getGameMap() {
        return gameMap;
    }
}
