package gfx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SpriteSet {

    private final Map<String, Image> animationSheets;

    public SpriteSet() {
        this.animationSheets = new HashMap<>();
    }

    public SpriteSet(Image image) {
        this.animationSheets = new HashMap<>();
        addSheet("default", image);
    }

    public void addSheet(String name, Image animationSheet) {
        animationSheets.put(name, animationSheet);
    }

    public Image getOrGetDefault(String name) {
        if (animationSheets.containsKey(name)) {
            return animationSheets.get(name);
        }

        return animationSheets.get("default");
    }
}
