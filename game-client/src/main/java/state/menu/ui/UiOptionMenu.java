package state.menu.ui;

import state.State;
import state.menu.MenuState;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiButton;

public class UiOptionMenu extends VerticalContainer {
    public UiOptionMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        addUiComponent(new UiText("Genesis"));
        addUiComponent(new UiButton("BACK", (state) -> ((MenuState)state).enterMenu(new UiMainMenu())));
    }

    @Override
    public void update(State state) {
        super.update(state);
    }
}
