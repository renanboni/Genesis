package gfx;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private Map<String, SpriteSet> units = new HashMap<>();
    private Map<String, Image> tiles = new HashMap<>();

    public SpriteLibrary() {
        loadSpritesFromDisk();
    }

    public SpriteSet getUnit(String name) {
        return units.get(name);
    }

    private void loadSpritesFromDisk() {
        loadUnits("/sprites/units");
        loadTiles("/sprites/tiles");
    }

    private void loadTiles(String pathToFolder) {
        String[] imagesInFolder = getImagesInFolder(pathToFolder);

        for (String fileName : imagesInFolder) {
            tiles.put(fileName.substring(0, fileName.length() - 4),
                    ImageUtils.loadImage(pathToFolder + "/" + fileName));
        }
    }

    private void loadUnits(String path) {
        String[] folderNames = getFolderNames(path);

        for (String folderName : folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = path + "/" + folderName;

            String[] sheetsInFolder = getImagesInFolder(pathToFolder);

            for (String sheetName : sheetsInFolder) {
                spriteSet.addSheet(sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            units.put(folderName, spriteSet);
        }
    }

    private String[] getImagesInFolder(String s) {
        URL resource = SpriteLibrary.class.getResource(s);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private String[] getFolderNames(String path) {
        URL resource = SpriteLibrary.class.getResource(path);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    public Image getTile(String name) {
        return tiles.get(name);
    }
}
