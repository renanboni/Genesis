package state.menu.ui;

import state.GameState;
import state.editor.EditorState;
import state.menu.MenuState;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiButton;

import java.awt.*;

public class UiMainMenu extends VerticalContainer {

    public UiMainMenu() {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        setBackgroundColor(Color.DARK_GRAY);
        centerChildren = true;

        addUiComponent(new UiText("Genesis"));
        addUiComponent(new UiButton("PLAY", (state) -> state.setNextState(new GameState(state.getWindowSize(), state.getInput(), state.getSettings()))));
        addUiComponent(new UiButton("OPTIONS", (state) -> ((MenuState) state).enterMenu(new UiOptionMenu())));
        addUiComponent(new UiButton("EDITOR", (state) -> state.setNextState(new EditorState(state.getWindowSize(), state.getInput(), state.getSettings()))));
        addUiComponent(new UiButton("EXIT", (state) -> System.exit(0)));
    }
}
