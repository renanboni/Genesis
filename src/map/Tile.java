package map;

import game.Game;
import gfx.SpriteLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private Image sprite;
    private Image image;
    private int tileIndex;

    public Tile(SpriteLibrary spriteLibrary) {
        this(spriteLibrary, "grass");
    }

    public Tile(SpriteLibrary spriteLibrary, String tileName) {
        this.image = spriteLibrary.getImage(tileName);
        generateSprite();
    }

    private Tile(Image image, int tileIndex) {
        this.image = image;
        this.tileIndex = tileIndex;
        generateSprite();
    }

    private void generateSprite() {
        sprite = ((BufferedImage) image).getSubimage(
                (tileIndex / (image.getWidth(null) / Game.SPRITE_SIZE)) * Game.SPRITE_SIZE,
                (tileIndex % (image.getWidth(null) / Game.SPRITE_SIZE)) * Game.SPRITE_SIZE,
                Game.SPRITE_SIZE,
                Game.SPRITE_SIZE
        );
    }

    public static Tile copyOf(Tile tile) {
        return new Tile(tile.getImage(), tile.getTileIndex());
    }

    public Image getImage() {
        return image;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        generateSprite();
    }

    public Image getSprite() {
        return sprite;
    }
}
