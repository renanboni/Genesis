package map;

import gfx.SpriteLibrary;

import java.io.*;
import java.net.URL;

public class MapIO {

    public static void save(GameMap map) {
        final URL urlToResourcesFolder = MapIO.class.getResource("/");
        File mapsFolder = new File(urlToResourcesFolder.getFile() + "/maps/");

        if (!mapsFolder.exists()) {
            mapsFolder.mkdir();
        }

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(mapsFolder.toString() + "/map.ism"))) {
            objectOutputStream.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameMap load(SpriteLibrary spriteLibrary) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MapIO.class.getResource("/maps/map.ism").getFile()))) {
            GameMap gameMap = (GameMap) objectInputStream.readObject();
            gameMap.reloadGraphics(spriteLibrary);
            return gameMap;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
