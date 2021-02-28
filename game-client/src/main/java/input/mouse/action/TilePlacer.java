package input.mouse.action;

import core.Position;
import map.GameMap;
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

        if (state.getSettings().getEditorSettings().getAutoTile().get()) {
            autoTile(state);
        }
    }

    private void autoTile(State state) {
        GameMap gameMap = state.getGameMap();

        for (int x = gridX - 1; x < gridX + 2; x++) {
            for (int y = gridY - 1; y < gridY + 2; y++) {
                if (gameMap.gridWithinBounds(x, y)) {
                    Tile currentTile = gameMap.getTiles()[x][y];

                    int index = switch (getNeighborTiles(gameMap, x, y)) {
                        case "001011111", "000011011", "001011011", "000011111" -> 0;
                        case "000111111", "100111111", "001111111", "101111111" -> 1;
                        case "000110110", "100110110", "000110111", "100110111", "101111110" -> 2;
                        case "011011011", "011011111", "111011011", "111011111" -> 5;
                        case "110110110", "110110111", "111110110", "111110111" -> 7;
                        case "011011000", "111011000", "011011001", "111011001", "011111101" -> 10;
                        case "111111000", "111111001", "111111100", "111111101" -> 11;
                        case "110110000", "111110000", "110110100", "111110100" -> 12;
                        case "111111110" -> 15;
                        case "111111011" -> 16;
                        case "110111011" -> 17;
                        case "110111111" -> 20;
                        case "011111111" -> 21;
                        case "011111110" -> 22;
                        default -> 6;
                    };

                    Tile indexedTile = Tile.copyOf(currentTile);
                    indexedTile.setTileIndex(index);
                    state.getGameMap().setTile(x, y, indexedTile);
                }
            }
        }
    }

    private String getNeighborTiles(GameMap gameMap, int gridX, int gridY) {
        StringBuilder builder = new StringBuilder();
        Tile currentTile = gameMap.getTiles()[gridX][gridY];

        for (int x = gridX - 2; x < gridX + 2; x++) {
            for (int y = gridY - 1; y < gridY + 2; y++) {
                if (!gameMap.gridWithinBounds(x, y)) {
                    builder.append(1);
                    continue;
                }

                if (gameMap.getTiles()[x][y].getTileName().equals(currentTile.getTileName())) {
                    builder.append(1);
                } else {
                    builder.append(0);
                }
            }
        }

        return builder.toString();
    }
}












