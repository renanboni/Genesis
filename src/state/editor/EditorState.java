package state.editor;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import map.GameMap;
import state.State;
import state.editor.ui.UiButtonMenu;
import state.editor.ui.UiRenderSettings;

public class EditorState extends State {

    public EditorState(Size windowSize, Input input, GameSettings settings) {
        super(windowSize, input, settings);

        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);
        settings.getRenderSettings().getShouldRenderGrid().setValue(true);

        uiContainers.add(new UiButtonMenu(windowSize));
        uiContainers.add(new UiRenderSettings(windowSize, settings.getRenderSettings()));
    }
}
