package state.editor.ui;

import core.Size;
import game.settings.RenderSettings;
import map.GameMap;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiCheckBox;
import ui.clickable.UiMiniMap;

public class UiRenderSettings extends VerticalContainer {

    public UiRenderSettings(Size windowSize, RenderSettings settings, GameMap gameMap) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.END, Alignment.Position.START));
        setCenterChildren(true);
        addUiComponent(new UiMiniMap(gameMap));
        addUiComponent(new UiText("Render settings"));
        addUiComponent(new UiCheckBox("GRID", settings.getShouldRenderGrid()));
    }
}
