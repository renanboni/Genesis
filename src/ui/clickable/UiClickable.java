package ui.clickable;

import core.Position;
import state.State;
import ui.UiComponent;

import java.awt.*;

public abstract class UiClickable extends UiComponent {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) {
        Position mousePosition = state.getInput().getMousePosition();
        hasFocus = getBounds().contains(mousePosition.intX(), mousePosition.intY());
        isPressed = hasFocus && state.getInput().isMousePressed();

        if (hasFocus && state.getInput().isMouseClicked()) {
            onClick();
        }
    }

    protected abstract void onClick();

    private Rectangle getBounds() {
        return new Rectangle(
                absolutePosition.intX(),
                absolutePosition.intY(),
                size.getWidth(),
                size.getHeight()
        );
    }
}
