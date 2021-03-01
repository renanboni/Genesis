package state.menu.ui;

import core.Size;
import state.menu.MenuState;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiButton;

public class UiOptionMenu extends VerticalContainer {
    public UiOptionMenu(Size windowSize) {
        super(windowSize);

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        addUiComponent(new UiText("Genesis"));
        addUiComponent(new UiButton("BACK", (state) -> ((MenuState)state).enterMenu(new UiMainMenu(windowSize))));
    }
}
