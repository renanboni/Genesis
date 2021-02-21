package map;

import gfx.SpriteLibrary;

import java.awt.*;

public class Tile {

    private final Image sprite;

    public Tile(SpriteLibrary spriteLibrary) {
        this(spriteLibrary, "woodfloor");
    }

    public Tile(SpriteLibrary spriteLibrary, String tileName) {
        this.sprite = spriteLibrary.getImage(tileName);
    }

    public Image getSprite() {
        return sprite;
    }
}
