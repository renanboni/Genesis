package ui.clickable;

import core.Size;
import state.State;
import ui.UIContainer;
import ui.UiText;
import ui.VerticalContainer;

import java.awt.*;

public class UiButton extends UiClickable {

    private UIContainer container;
    private UiText label;

    private ClickAction clickAction;

    public UiButton(String label, ClickAction clickAction) {
        this.label = new UiText(label);
        this.clickAction = clickAction;

        container = new VerticalContainer(new Size(0, 0));
        container.addUiComponent(this.label);
        container.setFixedSize(new Size(150, 40));
    }

    @Override
    public void update(State state) {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color color = Color.GRAY;

        if (hasFocus) {
            color = Color.LIGHT_GRAY;
        }

        if (isPressed) {
            color = Color.DARK_GRAY;
        }

        container.setBackgroundColor(color);
    }

    @Override
    protected void onClick(State state) {
        clickAction.execute(state);
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }
}
