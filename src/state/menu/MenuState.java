package state.menu;

import core.Size;
import input.Input;
import map.GameMap;
import state.State;
import state.menu.ui.UiMainMenu;
import ui.UIContainer;

public class MenuState extends State {
    public MenuState(Size windowSize, Input input) {
        super(windowSize, input);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        uiContainers.add(new UiMainMenu(windowSize));
    }

    public void enterMenu(UIContainer container) {
        uiContainers.clear();
        uiContainers.add(container);
    }
}
