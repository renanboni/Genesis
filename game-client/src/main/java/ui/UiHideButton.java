package ui;

import core.Size;
import state.State;
import ui.clickable.ClickAction;
import ui.clickable.UiButton;

import java.awt.*;

public class UiHideButton extends UiButton {

    private UIContainer parentContainer;
    private UiComponent componentToHide;

    public UiHideButton(UIContainer parentContainer, UiComponent componentToHide) {
        super("", addOrRemove(parentContainer, componentToHide));
        this.parentContainer = parentContainer;
        this.componentToHide = componentToHide;
        container.setFixedSize(new Size(30, 30));
        backgroundColor = Color.DARK_GRAY;
    }

    @Override
    public void update(State state) {
        super.update(state);
        label.setText("");

        if(!parent.hasComponent(componentToHide)) {
            label.setText("");
        }
    }

    private static ClickAction addOrRemove(UIContainer parentContainer, UiComponent componentToHide) {
        return state -> {
            if (parentContainer.hasComponent(componentToHide)) {
                parentContainer.removeComponent(componentToHide);
            } else {
                parentContainer.addUiComponent(componentToHide);
            }
        };
    }
}
