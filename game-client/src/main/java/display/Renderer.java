package display;

import core.Position;
import game.Game;
import map.GameMap;
import state.State;

import java.awt.*;

public class Renderer {

    public static void render(State state, Graphics graphics) {
        renderMap(state, graphics);
        renderGameObjects(state, graphics);
        renderUi(state, graphics);
    }

    private static void renderUi(State state, Graphics graphics) {
        state.getMouseHandler().getPrimaryButtonUI().ifPresent(uiImage -> graphics.drawImage(
                uiImage.getSprite(),
                uiImage.getAbsolutePosition().intX(),
                uiImage.getAbsolutePosition().intY(),
                null
        ));

        state.getUiCanvas().getComponents().forEach(uiContainer -> graphics.drawImage(
                uiContainer.getSprite(),
                uiContainer.getRelativePosition().intX(),
                uiContainer.getRelativePosition().intY(),
                null
        ));
    }

    private static void renderGameObjects(State state, Graphics graphics) {
        Camera camera = state.getCamera();

        state.getGameObjects()
                .stream()
                .filter(camera::isInView)
                .forEach(gameObject -> graphics.drawImage(
                        gameObject.getSprite(),
                        gameObject.getRenderPosition(camera).intX(),
                        gameObject.getRenderPosition(camera).intY(),
                        null
                ));
    }

    private static void renderMap(State state, Graphics graphics) {
        GameMap map = state.getGameMap();
        Camera camera = state.getCamera();

        Position start = map.getViewableStartingGridPosition(camera);
        Position end = map.getViewableEndingGridPosition(camera);

        for (int x = start.intX(); x < end.intX(); x++) {
            for (int y = start.intY(); y < end.intY(); y++) {
                graphics.drawImage(
                        map.getTiles()[x][y].getSprite(),
                        x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE - camera.getPosition().intY(),
                        null
                );
            }
        }

        if (state.getSettings().getRenderSettings().getShouldRenderGrid().get()) {
            graphics.setColor(Color.GRAY);

            for (int x = start.intX(); x < end.intX(); x++) {
                graphics.drawLine(
                        x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        start.intY() * Game.SPRITE_SIZE - camera.getPosition().intY(),
                        x * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        end.intY() * Game.SPRITE_SIZE - camera.getPosition().intY()
                );
            }

            for (int y = start.intY(); y < end.intY(); y++) {
                graphics.drawLine(
                        start.intX() * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE - camera.getPosition().intY(),
                        end.intX() * Game.SPRITE_SIZE - camera.getPosition().intX(),
                        y * Game.SPRITE_SIZE - camera.getPosition().intY()
                );
            }
        }
    }
}
