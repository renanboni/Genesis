package state.editor;

import core.Size;
import game.settings.GameSettings;
import input.Input;
import input.mouse.action.TilePlacer;
import map.GameMap;
import map.Tile;
import state.State;
import state.editor.ui.UiButtonMenu;
import state.editor.ui.UiRenderSettings;
import state.editor.ui.UiTileMenu;
import ui.Alignment;
import ui.UiTabContainer;

public class EditorState extends State {

    public EditorState(Size windowSize, Input input, GameSettings settings) {
        super(windowSize, input, settings);

        this.gameMap = new GameMap(new Size(16, 16), spriteLibrary);
        settings.getRenderSettings().getShouldRenderGrid().setValue(true);

        getMouseHandler().setPrimaryButtonAction(new TilePlacer(new Tile(getSpriteLibrary(), "grass")));

        uiContainers.add(new UiButtonMenu());
        uiContainers.add(new UiRenderSettings(settings.getRenderSettings(), gameMap));

        UiTabContainer toolsContainer = new UiTabContainer();
        toolsContainer.setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.END));
        toolsContainer.addTab("TILES", new UiTileMenu(getSpriteLibrary(), settings.getEditorSettings()));
        toolsContainer.addTab("SCENERY",new UiRenderSettings(settings.getRenderSettings(), gameMap));
        uiContainers.add(toolsContainer);
    }

    @Override
    protected void handleInput() {

    }
}
