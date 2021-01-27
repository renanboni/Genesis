package entity;

import controller.PlayerController;
import display.Display;
import game.GameObject;
import gfx.SpriteLibrary;
import input.Input;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final Display display;
    private final List<GameObject> gameObjects;
    private final Input input;
    private final SpriteLibrary spriteLibrary;

    public Game(int width, int height) {
        input = new Input();

        this.display = new Display(width, height, input);
        this.gameObjects = new ArrayList<>();
        this.spriteLibrary = new SpriteLibrary();
        this.gameObjects.add(new Player(new PlayerController(input), spriteLibrary));
    }

    public void render() {
        display.render(this);
    }

    public void update() {
        gameObjects.forEach(GameObject::update);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
