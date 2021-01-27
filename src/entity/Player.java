package entity;

import controller.Controller;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MovingEntity {

    public Player(Controller controller) {
        super(controller);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public Image getSprite() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setColor(Color.BLUE);
        graphics2D.fillRect(0, 0, size.getWidth(), size.getHeight());

        graphics2D.dispose();
        return image;
    }
}
