package entity;

import core.CollisionBox;
import core.Size;
import gfx.ImageUtils;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectionCircle extends GameObject {

    private Color color;
    private BufferedImage sprite;

    public SelectionCircle() {
        color = Color.ORANGE;
        size = new Size(32, 32);
        initSprites();
    }

    @Override
    public void update(State state) {

    }

    @Override
    public CollisionBox getCollisionBox() {
        return CollisionBox.of(
                position,
                size
        );
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    private void initSprites() {
        sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics2D = sprite.createGraphics();
        graphics2D.setColor(color);
        graphics2D.fillOval(0, 0, size.getWidth(), size.getHeight());
        graphics2D.dispose();
    }
}
