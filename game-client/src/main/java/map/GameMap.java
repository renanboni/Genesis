package map;

import core.Position;
import core.Size;
import display.Camera;
import game.Game;
import io.Persistable;
import java.util.Arrays;

public class GameMap implements Persistable {

    private static final int SAFETY_SPACE = 2;

    private Tile[][] tiles;

    public GameMap() {
    }

    public GameMap(Size size) {
        tiles = new Tile[size.getWidth()][size.getHeight()];
        initTiles();
    }

    private void initTiles() {
        for (Tile[] row : tiles) {
            Arrays.fill(row, new Tile());
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public double getWidth() {
        return tiles.length * Game.SPRITE_SIZE;
    }

    public double getHeight() {
        return tiles[0].length * Game.SPRITE_SIZE;
    }

    public Position getRandomPosition() {
        double x = Math.random() * tiles.length * Game.SPRITE_SIZE;
        double y = Math.random() * tiles[0].length * Game.SPRITE_SIZE;

        return new Position(x, y);
    }

    public Position getViewableStartingGridPosition(Camera camera) {
        return new Position(
                Math.max(0, camera.getPosition().getX() / Game.SPRITE_SIZE - SAFETY_SPACE),
                Math.max(0, camera.getPosition().getY() / Game.SPRITE_SIZE - SAFETY_SPACE)
        );
    }

    public Position getViewableEndingGridPosition(Camera camera) {
        return new Position(
                Math.min(tiles.length, camera.getPosition().getX() / Game.SPRITE_SIZE + camera.getSize().getWidth() / Game.SPRITE_SIZE + SAFETY_SPACE),
                Math.min(tiles[0].length, camera.getPosition().getY() / Game.SPRITE_SIZE + camera.getSize().getHeight() / Game.SPRITE_SIZE + SAFETY_SPACE)
        );
    }

    public boolean gridWithinBounds(int gridX, int gridY) {
        return gridX >= 0 && gridX < tiles.length && gridY >= 0 && gridY < tiles[0].length;
    }

    public void setTile(int gridX, int gridY, Tile tile) {
        tiles[gridX][gridY] = tile;
    }

    public void reloadGraphics() {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.reloadGraphics();
            }
        }
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName());
        builder.append(DELIMETER);
        builder.append(tiles.length);
        builder.append(DELIMETER);
        builder.append(tiles[0].length);
        builder.append(DELIMETER);

        builder.append(SECTION_DELIMETER);

        for (Tile[] tile : tiles) {
            for (int y = 0; y < tiles[0].length; y++) {
                builder.append(tile[y].serialize());
                builder.append(LIST_DELIMETER);
            }
            builder.append(COLUMN_DELIMETER);
        }

        return builder.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMETER);
        tiles = new Tile[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[2])];

        String tileSection = serializedData.split(SECTION_DELIMETER)[1];

        String[] columns = tileSection.split(COLUMN_DELIMETER);

        for (int x = 0; x < tiles.length; x++) {
            String[] serializedTiles = columns[x].split(LIST_DELIMETER);

            for (int y = 0; y < tiles[0].length; y++) {
                Tile tile = new Tile();
                tile.applySerializedData(serializedTiles[y]);

                tiles[x][y] = tile;
            }
        }
    }
}
