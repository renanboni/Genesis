package gfx;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private final static String PATH_TO_UNITS = "/sprites/units";

    private static Map<String, SpriteSet> units = new HashMap<>();

    public SpriteLibrary() {
        loadSpritesFromDisk();
    }

    public SpriteSet getUnit(String name) {
        return units.get(name);
    }

    private void loadSpritesFromDisk() {
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
}
