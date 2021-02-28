package game;

import controller.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import input.Input;
import state.GameState;
import state.State;
import state.menu.MenuState;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final Display display;
    private final Input input;
    private State state;
    private final GameSettings settings;
    private final GameController gameController;

    public Game(int width, int height) {
        this.input = new Input();
        this.display = new Display(width, height, input);
        this.settings = new GameSettings(true);
        //this.state = new GameState(new Size(width, height), input, settings);
        // this.state = new LoginState(new Size(width, height), input, settings);
        this.state = new MenuState(new Size(width, height), input, settings);
        this.gameController = new GameController(input);
    }

    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public void update() {
        state.update(this);
        gameController.update(this);
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void enterState(State nextState) {
        state = nextState;
    }
}
