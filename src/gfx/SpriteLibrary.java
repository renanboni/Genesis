package gfx;

import entity.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private final static String PATH_TO_UNITS = "/sprites/units";

    private Map<String, SpriteSet> units = new HashMap<>();
    private Map<String, Image> tiles = new HashMap<>();

    public SpriteLibrary() {
        loadSpritesFromDisk();
    }

    public SpriteSet getUnit(String name) {
        return units.get(name);
    }

    private void loadSpritesFromDisk() {
        loadUnits();
        loadTiles();
    }

    private void loadTiles() {
        BufferedImage image = new BufferedImage(Game.SPRITE_SIZE, Game.SPRITE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(0, 0, Game.SPRITE_SIZE, Game.SPRITE_SIZE);

        graphics2D.dispose();
        tiles.put("default", image);
    }

    private void loadUnits() {
        String[] folderNames = getFolderNames();

        for (String folderName : folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = PATH_TO_UNITS + "/" + folderName;

            String[] sheetsInFolder = getSheetsInFolder(pathToFolder);

            for (String sheetName : sheetsInFolder) {
                spriteSet.addSheet(sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            units.put(folderName, spriteSet);
        }
    }

    private String[] getSheetsInFolder(String s) {
        URL resource = SpriteLibrary.class.getResource(s);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private String[] getFolderNames() {
        URL resource = SpriteLibrary.class.getResource(SpriteLibrary.PATH_TO_UNITS);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    public Image getTile(String name) {
        return tiles.get(name);
    }
}
