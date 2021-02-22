package input.mouse.action;

import core.Position;
import map.Tile;
import state.State;
import ui.UiImage;

import static game.Game.SPRITE_SIZE;

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
        Position position = Position.copyOf(state.getInput().getMousePosition());
        position.add(state.getCamera().getPosition());

        gridX = position.intX() / SPRITE_SIZE;
        gridY = position.intY() / SPRITE_SIZE;

        position.subtract(new Position(position.intX() % SPRITE_SIZE, position.intY() % SPRITE_SIZE));
        position.subtract(state.getCamera().getPosition());

        preview.setAbsolutePosition(position);
    }

    @Override
    public UiImage getSprite() {
        return preview;
    }

    @Override
    public void onClick(State state) { }

    @Override
    public void onDrag(State state) {
        if (state.getGameMap().gridWithinBounds(gridX, gridY)) {
            state.getGameMap().setTile(gridX, gridY, Tile.copyOf(tile));
        }
    }
}
