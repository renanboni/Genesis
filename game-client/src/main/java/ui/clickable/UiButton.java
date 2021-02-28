package ui.clickable;

import core.Size;
import state.State;
import ui.Spacing;
import ui.UIContainer;
import ui.UiText;
import ui.VerticalContainer;

import java.awt.*;

public class UiButton extends UiClickable {

    private final UIContainer container;
    private final UiText label;

    private final ClickAction clickAction;

    public UiButton(String label, ClickAction clickAction) {
        this.label = new UiText(label);
        this.clickAction = clickAction;

        setMargin(new Spacing(5, 0, 0, 0));

        container = new VerticalContainer();
        container.setCenterChildren(true);
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
    public void onClick(State state) {
        if (hasFocus) {
            clickAction.execute(state);
        }
    }

    @Override
    protected void onFocus(State state) {

    }

    @Override
    public void onDrag(State state) {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }
}
