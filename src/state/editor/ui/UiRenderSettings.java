package state.editor.ui;

import core.Size;
import game.settings.RenderSettings;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiCheckBox;

public class UiRenderSettings extends VerticalContainer {

    public UiRenderSettings(Size windowSize, RenderSettings settings) {
        super(windowSize);
        setAlignment(new Alignment(Alignment.Position.END, Alignment.Position.START));

        addUiComponent(new UiText("Render settings"));
        addUiComponent(new UiCheckBox("GRID", settings.getShouldRenderGrid()));
    }
}
