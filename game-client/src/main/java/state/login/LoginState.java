package state.login;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import main.Client;
import map.GameMap;
import state.State;
import state.login.ui.UiLoginMenu;

public class LoginState extends State {

    public LoginState(Size windowSize, Input input, GameSettings settings, Client client) {
        super(windowSize, input, settings);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        uiCanvas.addUIComponent(new UiLoginMenu(client));
    }

    @Override
    protected void handleInput() {

    }
}
