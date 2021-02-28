package io;

import gfx.SpriteLibrary;
import map.GameMap;

import java.io.*;
import java.net.URL;

public class MapIO {

    public static void save(GameMap map) {
        final URL urlToResourcesFolder = MapIO.class.getResource("/");
        File mapsFolder = new File(urlToResourcesFolder.getFile() + "/maps/");

        if (!mapsFolder.exists()) {
            mapsFolder.mkdir();
        }

        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(mapsFolder.toString() + "/map.ism"))) {
            bufferWriter.write(map.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameMap load(SpriteLibrary spriteLibrary) {
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(MapIO.class.getResource("/maps/map.ism").getFile()))) {
            GameMap gameMap = new GameMap();

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferReader.readLine()) != null) {
                builder.append(System.lineSeparator());
                builder.append(line);
            }

            gameMap.applySerializedData(builder.toString());
            gameMap.reloadGraphics(spriteLibrary);
            return gameMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
