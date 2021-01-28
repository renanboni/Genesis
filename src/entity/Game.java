package entity;

import core.Size;
import display.Display;
import input.Input;
import state.GameState;
import state.State;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final Display display;

    private Input input;

    private State state;

    public Game(int width, int height) {
        input = new Input();
        this.display = new Display(width, height, input);
        state = new GameState(new Size(width, height), input);
    }

    public void render() {
        display.render(state);
    }

    public void update() {
        state.update();
    }
}
