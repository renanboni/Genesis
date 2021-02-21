package ui.clickable;

import core.Size;
import game.settings.Setting;
import gfx.ImageUtils;
import state.State;
import ui.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UiCheckBox extends UiComponent {

    private final UIContainer container;

    public UiCheckBox(String label, Setting<Boolean> setting) {
        this.container = new HorizontalContainer(new Size(0, 0));
        container.addUiComponent(new CheckBox(setting));
        container.addUiComponent(new UiText(label));
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

        private final Setting<Boolean> setting;
        private Color color = Color.GRAY;

        private CheckBox(Setting<Boolean> setting) {
            this.setting = setting;
            this.size = new Size(20, 20);
            this.margin = new Spacing(0, 5, 0, 0);
        }

        @Override
        public void update(State state) {
            super.update(state);

            color = setting.getValue() ? Color.WHITE : Color.GRAY;

            if (hasFocus) {
                color = Color.LIGHT_GRAY;

                if (isPressed) {
                    color = Color.DARK_GRAY;
                }
            }
        }

        @Override
        protected void onClick(State state) {
            setting.setValue(!setting.getValue());
        }

        @Override
        protected void onFocus(State state) {

        }

        @Override
        protected void onDrag(State state) {

        }

        @Override
        public Image getSprite() {
            BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
            Graphics2D graphics = (Graphics2D) sprite.getGraphics();

            graphics.setColor(color);

            if (setting.getValue()) {
                graphics.fillRect(0, 0, size.getWidth(), size.getHeight());
            } else {
                graphics.drawRect(0, 0, size.getWidth() - 1, size.getHeight() - 1);
            }

            graphics.dispose();
            return sprite;
        }
    }
}
