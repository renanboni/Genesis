package map;

import gfx.SpriteLibrary;

import java.awt.*;

public class Tile {

    private final Image sprite;
    private Image image;
    private int tileIndex;

    public Tile(SpriteLibrary spriteLibrary) {
        this(spriteLibrary, "woodfloor");
    }

    public Tile(SpriteLibrary spriteLibrary, String tileName) {
        this.sprite = spriteLibrary.getImage(tileName);
        generateSprite();
    }

    private void generateSprite() {

    }

    private Tile(Image sprite) {
        this.sprite = sprite;
    }

    public static Tile copyOf(Tile tile) {
        return new Tile(tile.getSprite());
    }

    public Image getSprite() {
        return sprite;
    }
}
