package ui.input;

import core.Size;
import game.settings.Value;
import input.KeyInputConsumer;
import state.State;
import ui.*;
import ui.clickable.UiClickable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class UiTextInput extends UiClickable implements KeyInputConsumer {

    private final Value<String> value;

    private UIContainer container;
    private UIContainer borderContainer;
    private UIContainer contentContainer;

    public UiTextInput(String label, Value<String> value) {
        this.value = value;
        this.margin = new Spacing(0);
        this.padding = new Spacing(0);

        borderContainer = new VerticalContainer();
        borderContainer.setPadding(new Spacing(2));
        borderContainer.setMargin(new Spacing(0));

        contentContainer = new HorizontalContainer();
        contentContainer.setCenterChildren(true);
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setBackgroundColor(Color.DARK_GRAY);
        contentContainer.setFixedSize(new Size(200, 30));

        borderContainer.addUiComponent(contentContainer);

        container = new VerticalContainer();
        container.setMargin(new Spacing(0));
        container.setPadding(new Spacing(0));
        container.addUiComponent(new UiText(label));
        container.addUiComponent(borderContainer);
    }

    @Override
    public void update(State state) {
        super.update(state);
        container.update(state);
        size = container.getSize();

        Color borderColor = Color.GRAY;

        if (state.getKeyInputConsumer() != null && state.getKeyInputConsumer().equals(this)) {
            borderColor = Color.WHITE;
        }

        borderContainer.setBackgroundColor(borderColor);
    }

    @Override
    public void onKeyPressed(int key) {
        String currentValue = value.get();

        if (key == KeyEvent.VK_BACK_SPACE) {
            if (!currentValue.isEmpty()) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
        } else if (key == KeyEvent.VK_SPACE) {
            currentValue += " ";
        } else {
            String keyText = KeyEvent.getKeyText(key);

            if (keyText.length() == 1) {
                currentValue += keyText;
            }
        }

        value.setValue(currentValue);

        contentContainer.clear();
        contentContainer.addUiComponent(new UiText(currentValue));
    }

    @Override
    protected void onFocus(State state) {

    }

    @Override
    public void onClick(State state) {
        state.setKeyInputConsumer(this);
    }

    @Override
    public void onDrag(State state) {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }
}
