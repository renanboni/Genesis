package game;

import controller.GameController;
import core.Size;
import display.Display;
import game.settings.GameSettings;
import input.Input;
import main.Client;
import state.GameState;
import state.State;
import state.login.LoginState;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final Display display;
    private final Input input;
    private State state;
    private final GameSettings settings;
    private final GameController gameController;
    private final Client client;

    public Game(int width, int height, Client client) {
        this.client = client;
        this.input = new Input();
        this.settings = new GameSettings(true);
        this.state = new LoginState(new Size(width, height), input, settings, client);
        // this.state = new MenuState(new Size(width, height), input, settings);
        this.display = new Display(width, height, input, this::resize);
        this.gameController = new GameController(input);
    }

    public State getGameState() {
        return new GameState(state.getWindowSize(), state.getInput(), state.getSettings());
    }

    public void render() {
        display.render(state, settings.isDebugMode());
    }

    public void update() {
        client.update();
        state.update(this);
        gameController.update(this);
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void enterState(State nextState) {
        state = nextState;
    }

    public void resize(Size size) {
        state.resize(size);
    }
}
