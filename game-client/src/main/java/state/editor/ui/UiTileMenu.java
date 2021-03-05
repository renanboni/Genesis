package state.editor.ui;

import game.settings.EditorSettings;
import map.Tile;
import ui.*;
import ui.clickable.UiCheckBox;
import ui.clickable.UiTileToggle;

import java.awt.*;

import static game.Game.SPRITE_SIZE;

public class UiTileMenu extends VerticalContainer {
    public UiTileMenu(EditorSettings editorSettings) {
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.END));

        setPadding(new Spacing(5));

        UiTabContainer tileContainer = new UiTabContainer();
        tileContainer.addTab("grass", getTileSet("grass"));
        tileContainer.addTab("concrete", getTileSet("concrete"));
        tileContainer.addTab("dirt", getTileSet("dirt"));
        tileContainer.addTab("water", getTileSet("water"));
        tileContainer.setPadding(new Spacing());

        addUiComponent(tileContainer);
        addUiComponent(new UiCheckBox("Autotile", editorSettings.getAutoTile()));
    }

    private UIContainer getTileSet(String tileSet) {
        UIContainer main = new HorizontalContainer();
        main.setMargin(new Spacing());
        main.setPadding(new Spacing());

        Tile tile = new Tile(tileSet);

        int tilesX = tile.getImage().getWidth(null) / SPRITE_SIZE;
        int tilesY = tile.getImage().getHeight(null) / SPRITE_SIZE;

        for (int x = 0; x < tilesX; x++) {
            UIContainer column = new VerticalContainer();
            column.setPadding(new Spacing());
            column.setMargin(new Spacing());

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
