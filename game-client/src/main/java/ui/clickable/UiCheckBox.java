package ui.clickable;

import core.Size;
import game.settings.Value;
import gfx.ImageUtils;
import state.State;
import ui.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UiCheckBox extends UiComponent {

    private final UIContainer container;

    public UiCheckBox(String label, Value<Boolean> value) {
        this.container = new HorizontalContainer();
        container.addUiComponent(new CheckBox(value));
        container.addUiComponent(new UiText(label));
        container.setPadding(new Spacing());
        setMargin(new Spacing(5, 0, 0, 0));
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void update(State state) {
        container.update(state);
        size = container.getSize();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
    }

    private static class CheckBox extends UiClickable {

        private final Value<Boolean> value;
        private Color color = Color.GRAY;

        private CheckBox(Value<Boolean> value) {
            this.value = value;
            this.size = new Size(20, 20);
            this.margin = new Spacing(0, 5, 0, 0);
        }

        @Override
        public void update(State state) {
            super.update(state);

            color = value.get() ? Color.WHITE : Color.GRAY;

            if (hasFocus) {
                color = Color.LIGHT_GRAY;

                if (isPressed) {
                    color = Color.DARK_GRAY;
                }
            }
        }

        @Override
        public void onClick(State state) {
            if (hasFocus) {
                value.setValue(!value.get());
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
            BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
            Graphics2D graphics = (Graphics2D) sprite.getGraphics();

            graphics.setColor(color);

            if (value.get()) {
                graphics.fillRect(0, 0, size.getWidth(), size.getHeight());
            } else {
                graphics.drawRect(0, 0, size.getWidth() - 1, size.getHeight() - 1);
            }

            graphics.dispose();
            return sprite;
        }
    }
}
