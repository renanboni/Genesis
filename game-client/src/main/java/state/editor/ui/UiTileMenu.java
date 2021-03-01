package state.editor.ui;

import game.settings.EditorSettings;
import gfx.SpriteLibrary;
import map.Tile;
import ui.*;
import ui.clickable.UiCheckBox;
import ui.clickable.UiTileToggle;

import java.awt.*;

import static game.Game.SPRITE_SIZE;

public class UiTileMenu extends VerticalContainer {
    public UiTileMenu(SpriteLibrary spriteLibrary, EditorSettings editorSettings) {
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Position.START, Alignment.Position.END));

        setPadding(new Spacing(5));

        UiTabContainer tileContainer = new UiTabContainer();
        tileContainer.addTab("grass", getTileSet(spriteLibrary, "grass"));
        tileContainer.addTab("concrete", getTileSet(spriteLibrary, "concrete"));
        tileContainer.addTab("dirt", getTileSet(spriteLibrary, "dirt"));
        tileContainer.addTab("water", getTileSet(spriteLibrary, "water"));
        tileContainer.setPadding(new Spacing());

        addUiComponent(tileContainer);
        addUiComponent(new UiCheckBox("Autotile", editorSettings.getAutoTile()));
    }

    private UIContainer getTileSet(SpriteLibrary spriteLibrary, String tileSet) {
        UIContainer main = new HorizontalContainer();
        main.setMargin(new Spacing(0));
        main.setPadding(new Spacing(0));

        Tile tile = new Tile(spriteLibrary, tileSet);

        int tilesX = tile.getImage().getWidth(null) / SPRITE_SIZE;
        int tilesY = tile.getImage().getHeight(null) / SPRITE_SIZE;

        for (int x = 0; x < tilesX; x++) {
            UIContainer column = new VerticalContainer();
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
