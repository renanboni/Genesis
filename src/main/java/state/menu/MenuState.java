package state.menu;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import map.MapIO;
import state.State;
import state.menu.ui.UiMainMenu;
import ui.UIContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input, GameSettings settings) {
        super(windowSize, input, settings);
        this.gameMap = MapIO.load(getSpriteLibrary());
        settings.getRenderSettings().getShouldRenderGrid().setValue(false);

        uiContainers.add(new UiMainMenu(windowSize));
    }

    public void enterMenu(UIContainer container) {
        uiContainers.clear();
        uiContainers.add(container);
    }
}
