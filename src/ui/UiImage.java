package ui;

import core.Size;
import state.State;

import java.awt.*;

public class UiImage extends UiComponent {

    private final Image image;

    public UiImage(Image image) {
        this.image = image;
        this.size = new Size(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public Image getSprite() {
        return null;
    }

    @Override
    public void update(State state) {

    }
}
