package state.editor.ui;

import core.Size;
import state.menu.MenuState;
import ui.HorizontalContainer;
import ui.clickable.UiButton;

public class UiButtonMenu extends HorizontalContainer {

    public UiButtonMenu(Size windowSize) {
        super(windowSize);

        addUiComponent(new UiButton("MAIN MENU", state -> state.setNextState(new MenuState(state.getCamera().getSize(), state.getInput(), state.getSettings()))));
    }
}
