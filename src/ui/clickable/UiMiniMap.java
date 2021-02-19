package ui.clickable;

import core.Size;
import display.Camera;
import game.Game;
import gfx.ImageUtils;
import map.GameMap;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UiMiniMap extends UiClickable {

    private double ratio;
    private Rectangle cameraViewBounds;
    private BufferedImage mapImage;
    private Color color;

    public UiMiniMap(GameMap map) {
        size = new Size(128, 128);
        cameraViewBounds = new Rectangle(0, 0, 0, 0);
        color = Color.GRAY;

        calculateRatio(map);
        generateMap(map);
    }

    @Override
    public void update(State state) {
        super.update(state);

        Camera camera = state.getCamera();
        cameraViewBounds = new Rectangle(
                (int) (camera.getPosition().getX() * ratio),
                (int) (camera.getPosition().getY() * ratio),
                (int) (getSize().getWidth() * ratio),
                (int) (getSize().getHeight() * ratio)
        );

        color = Color.GRAY;

        if (hasFocus) {
            color = Color.WHITE;
        }
    }

    private void generateMap(GameMap gameMap) {
        mapImage = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = mapImage.createGraphics();

        int pixelsPerGrid = (int) Math.round(Game.SPRITE_SIZE * ratio);

        for (int x = 0; x < gameMap.getTiles().length; x++) {
            for (int y = 0; y < gameMap.getTiles()[0].length; y++) {
                graphics.drawImage(
                        gameMap.getTiles()[x][y].getSprite().getScaledInstance(pixelsPerGrid, pixelsPerGrid, 0),
                        x * pixelsPerGrid + (size.getWidth() - gameMap.getTiles().length * pixelsPerGrid) / 2,
                        y * pixelsPerGrid + (size.getHeight() - gameMap.getTiles()[0].length * pixelsPerGrid) / 2,
                        null
                );
            }
        }
    }

    private void calculateRatio(GameMap map) {
        ratio = Math.min(
                getSize().getWidth() / map.getWidth(),
                getSize().getHeight() / map.getHeight()
        );
    }

    @Override
    protected void onClick(State state) {

    }

    @Override
    public Image getSprite() {
        BufferedImage sprite = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_OPAQUE);
        Graphics2D graphics = sprite.createGraphics();

        graphics.drawImage(mapImage, 0, 0, null);

        graphics.setColor(color);

        graphics.drawRect(0, 0, getSize().getWidth() - 1, getSize().getHeight() - 1);

        graphics.draw(cameraViewBounds);

        graphics.dispose();
        return sprite;
    }
}
