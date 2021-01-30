package entity;

import core.Size;
import display.Display;
import game.settings.GameSettings;
import input.Input;
import state.GameState;
import state.State;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final Display display;
    private Input input;
    private State state;
    private GameSettings settings;

    public Game(int width, int height) {
        this.input = new Input();
        this.display = new Display(width, height, input);
        this.state = new GameState(new Size(width, height), input);
        this.settings = new GameSettings(true);
    }

    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public void update() {
        state.update();
    }
}
