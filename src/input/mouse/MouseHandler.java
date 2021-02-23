package input.mouse;

import input.Input;
import input.mouse.action.MouseAction;
import state.State;
import ui.UiImage;

import java.util.Optional;

public class MouseHandler {

    private MouseConsumer activeConsumer;
    private MouseAction primaryButtonAction;

    public void update(State state) {
        Input input = state.getInput();

        handlePrimaryButton(state);
        handleActiveConsumer(state, input);

        cleanUp(input);
    }

    private void handlePrimaryButton(State state) {
        if (primaryButtonAction != null) {
            setActiveConsumer(primaryButtonAction);
            primaryButtonAction.update(state);
        }
    }

    private void cleanUp(Input input) {
        if (!input.isMousePressed()) {
            activeConsumer = null;
        }

        input.clearMouseClick();
    }

    private void handleActiveConsumer(State state, Input input) {
        if (activeConsumer != null) {
            if (input.isMouseClicked()) {
                activeConsumer.onClick(state);
            } else if (input.isMousePressed()) {
                activeConsumer.onDrag(state);
            }
        }
    }

    public MouseConsumer getActiveConsumer() {
        return activeConsumer;
    }

    public void setActiveConsumer(MouseConsumer activeConsumer) {
        if (this.activeConsumer == null) {
            this.activeConsumer = activeConsumer;
        }
    }

    public void setPrimaryButtonAction(MouseAction primaryButtonAction) {
        this.primaryButtonAction = primaryButtonAction;
    }

    public Optional<UiImage> getPrimaryButtonUI() {
        if (primaryButtonAction != null) {
            return Optional.ofNullable(primaryButtonAction.getSprite());
        }

        return Optional.empty();
    }

    public MouseAction getPrimaryButtonAction() {
        return primaryButtonAction;
    }
}
