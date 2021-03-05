package gfx;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private final Map<String, SpriteSet> spriteSets = new HashMap<>();
    private final Map<String, Image> images = new HashMap<>();

    private static SpriteLibrary instance;

    private SpriteLibrary() {
        loadSpritesFromDisk();
    }

    public static SpriteLibrary getInstance() {
        if (instance == null) {
            instance = new SpriteLibrary();
        }

        return instance;
    }

    private void loadSpritesFromDisk() {
        loadSpriteSets("/sprites/units");
        loadImages("/sprites/tiles");
        loadImages("/sprites/effects");
    }

    private void loadImages(String pathToFolder) {
        String[] imagesInFolder = getImagesInFolder(pathToFolder);

        for (String fileName : imagesInFolder) {
            images.put(fileName.substring(0, fileName.length() - 4),
                    ImageUtils.loadImage(pathToFolder + "/" + fileName));
        }
    }

    private void loadSpriteSets(String path) {
        String[] folderNames = getFolderNames(path);

        for (String folderName : folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = path + "/" + folderName;

            String[] sheetsInFolder = getImagesInFolder(pathToFolder);

            for (String sheetName : sheetsInFolder) {
                spriteSet.addSheet(sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            spriteSets.put(folderName, spriteSet);
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

    public Image getImage(String name) {
        return images.get(name);
    }

    public SpriteSet getSpriteSet(String name) {
        return spriteSets.get(name);
    }
}
