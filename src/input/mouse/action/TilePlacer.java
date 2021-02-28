package input.mouse.action;

import map.Tile;
import state.State;
import ui.UiImage;

public class TilePlacer extends MouseAction {

    private final Tile tile;
    private UiImage preview;
    private int gridX;
    private int gridY;

    public TilePlacer(Tile tile) {
        this.tile = tile;
        this.preview = new UiImage(tile.getSprite());
    }

    @Override
    public void update(State state) {
        preview.setAbsolutePosition(state.getInput().getMousePosition());
    }

    @Override
    public UiImage getSprite() {
        return preview;
    }

    @Override
    public void onClick(State state) { }

    @Override
    public void onDrag(State state) {

    }
}
