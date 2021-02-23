package ui.clickable;

import core.Position;
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
    private Position pixelOffset;
    private int pixelsPerGrid;

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

        if (state.getTime().secondsDividableBy(0.25)) {
            generateMap(state.getGameMap());
        }

        Camera camera = state.getCamera();
        cameraViewBounds = new Rectangle(
                (int) (camera.getPosition().getX() * ratio + pixelOffset.intX()),
                (int) (camera.getPosition().getY() * ratio + pixelOffset.intY()),
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

        for (int x = 0; x < gameMap.getTiles().length; x++) {
            for (int y = 0; y < gameMap.getTiles()[0].length; y++) {
                graphics.drawImage(
                        gameMap.getTiles()[x][y].getSprite().getScaledInstance(pixelsPerGrid, pixelsPerGrid, Image.SCALE_AREA_AVERAGING),
                        x * pixelsPerGrid + pixelOffset.intX(),
                        y * pixelsPerGrid + pixelOffset.intY(),
                        null
                );
            }
        }
    }

    private void calculateRatio(GameMap gameMap) {
        ratio = Math.min(
                getSize().getWidth() / gameMap.getWidth(),
                getSize().getHeight() / gameMap.getHeight()
        );

        pixelsPerGrid = (int) Math.round(Game.SPRITE_SIZE * ratio);

        pixelOffset = new Position(
                (size.getWidth() - gameMap.getTiles().length * pixelsPerGrid) / 2,
                (size.getHeight() - gameMap.getTiles()[0].length * pixelsPerGrid) / 2
        );
    }

    @Override
    public void onClick(State state) {

    }

    @Override
    protected void onFocus(State state) {

    }

    @Override
    public void onDrag(State state) {
        Position mousePosition = Position.copyOf(state.getInput().getMousePosition());
        mousePosition.subtract(absolutePosition);
        mousePosition.subtract(pixelOffset);

        state.getCamera().setPosition(new Position(
                mousePosition.getX() / ratio - cameraViewBounds.getSize().getWidth() / ratio / 2,
                mousePosition.getY() / ratio - cameraViewBounds.getSize().getHeight() / ratio / 2)
        );
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
