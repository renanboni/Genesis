package state.menu.ui;

import core.Size;
import state.editor.EditorState;
import state.game.GameState;
import state.menu.MenuState;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiButton;

public class UiMainMenu extends VerticalContainer {

    public UiMainMenu(Size windowSize) {
        super(windowSize);

        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        addUiComponent(new UiText("Genesis"));
        addUiComponent(new UiButton("PLAY", (state) -> state.setNextState(new GameState(windowSize, state.getInput(), state.getSettings()))));
        addUiComponent(new UiButton("OPTIONS", (state) -> ((MenuState) state).enterMenu(new UiOptionMenu(windowSize))));
        addUiComponent(new UiButton("EDITOR", (state) -> state.setNextState(new EditorState(windowSize, state.getInput(), state.getSettings()))));
        addUiComponent(new UiButton("EXIT", (state) -> System.exit(0)));
    }
}
