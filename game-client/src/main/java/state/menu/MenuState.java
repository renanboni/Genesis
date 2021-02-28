package state.menu;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import map.GameMap;
import state.State;
import state.menu.ui.UiMainMenu;
import ui.UIContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings settings) {
        super(windowSize, input, settings);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        settings.getRenderSettings().getShouldRenderGrid().setValue(false);
        uiCanvas.addUIComponent(new UiMainMenu());
    }

    @Override
    protected void handleInput() {

    }

    public void enterMenu(UIContainer container) {
        uiCanvas.clear();
        uiCanvas.addUIComponent(container);
    }
}
