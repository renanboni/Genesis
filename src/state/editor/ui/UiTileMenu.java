package state.editor.ui;

import core.Size;
import gfx.SpriteLibrary;
import map.Tile;
import ui.*;
import ui.clickable.UiTileToggle;

import java.awt.*;

import static game.Game.SPRITE_SIZE;

public class UiTileMenu extends HorizontalContainer {
    public UiTileMenu(Size windowSize, SpriteLibrary spriteLibrary) {
        super(windowSize);
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.END));

        addUiComponent(new UiTileToggle(new Tile(spriteLibrary, "grass")));
        addUiComponent(getTileSet(spriteLibrary, "concrete"));
        addUiComponent(getTileSet(spriteLibrary, "dirt"));
    }

    private UiComponent getTileSet(SpriteLibrary spriteLibrary, String tileSet) {
        UIContainer main = new HorizontalContainer(new Size(0, 0));
        main.setMargin(new Spacing(0));
        main.setPadding(new Spacing(0));

        Tile tile = new Tile(spriteLibrary, tileSet);

        int tilesX = tile.getImage().getWidth(null) / SPRITE_SIZE;
        int tilesY = tile.getImage().getHeight(null) / SPRITE_SIZE;

        for (int x = 0; x < tilesX; x++) {
            UIContainer column = new VerticalContainer(new Size(0, 0));
            column.setPadding(new Spacing(0));
            column.setMargin(new Spacing(0));

            for (int y = 0; y < tilesY; y++) {
                Tile indexedTile = Tile.copyOf(tile);
                indexedTile.setTileIndex(x * tilesX + y);

                UiTileToggle toggle = new UiTileToggle(indexedTile);
                column.addUiComponent(toggle);
            }

            main.addUiComponent(column);
        }

        return main;
    }
}
