package input.mouse.action;

import input.mouse.MouseConsumer;
import state.State;
import ui.UiImage;

public abstract class MouseAction implements MouseConsumer {

    public abstract void update(State state);
    public abstract UiImage getSprite();
}
