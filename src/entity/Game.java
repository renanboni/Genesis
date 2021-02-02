package entity;

import controller.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import input.Input;
import state.GameState;
import state.State;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final Display display;
    private final Input input;
    private final State state;
    private final GameSettings settings;
    private final GameController gameController;

    public Game(int width, int height) {
        this.input = new Input();
        this.display = new Display(width, height, input);
        this.state = new GameState(new Size(width, height), input);
        this.settings = new GameSettings(true);
        this.gameController = new GameController(input);
    }

    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public void update() {
        state.update();
        gameController.update(this);
    }

    public GameSettings getSettings() {
        return settings;
    }
}
