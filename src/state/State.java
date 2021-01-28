package state;

import controller.PlayerController;
import entity.Player;
import game.GameObject;
import gfx.SpriteLibrary;
import input.Input;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected final List<GameObject> gameObjects;
    protected final SpriteLibrary spriteLibrary;
    protected Input input;

    public State(Input input) {
        this.input = input;
        this.gameObjects = new ArrayList<>();
        this.spriteLibrary = new SpriteLibrary();
    }

    public void update() {
        gameObjects.forEach(GameObject::update);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
