package ui;

import core.Position;
import core.Size;
import gfx.ImageUtils;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIContainer extends UiComponent {

    private Color backgroundColor;

    public UIContainer() {
        super();
        backgroundColor = Color.RED;
        calculateSize();
        calculatePosition();
    }

    private void calculateSize() {
        size = new Size(padding.getHorizontal(), padding.getVertical());
    }

    private void calculatePosition() {
        position = new Position(margin.getLeft(), margin.getTop());
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics2D.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        calculateSize();
        calculatePosition();
    }
}
