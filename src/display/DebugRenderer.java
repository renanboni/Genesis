package display;

import core.CollisionBox;
import entity.GameObject;
import entity.humanoid.Humanoid;
import state.State;
import ui.UiText;

import java.awt.*;
import java.util.stream.Collectors;

public class DebugRenderer {

    public static void render(State state, Graphics graphics) {
        Camera camera = state.getCamera();

        state.getGameObjects()
                .stream()
                .filter(camera::isInView)
                .map(GameObject::getCollisionBox)
                .forEach(collisionBox -> drawCollisionBox(collisionBox, graphics, camera));

        drawEffects(state, graphics);
    }

    private static void drawEffects(State state, Graphics graphics) {
        Camera camera = state.getCamera();

        state.getGameObjectOfClass(Humanoid.class)
                .forEach(humanoid -> {
                    UiText effectsText = new UiText(
                            humanoid
                                    .getEffects()
                                    .stream()
                                    .map(effect -> effect.getClass().getSimpleName())
                                    .collect(Collectors.joining(","))
                    );

                    effectsText.update(state);

                    graphics.drawImage(
                            effectsText.getSprite(),
                            humanoid.getPosition().intX() - camera.getPosition().intX(),
                            humanoid.getPosition().intY() - camera.getPosition().intY(),
                            null
                    );
                });

    }

    private static void drawCollisionBox(CollisionBox collisionBox, Graphics graphics, Camera camera) {
        graphics.setColor(Color.RED);
        graphics.drawRect(
                (int) collisionBox.getBounds().getX() - camera.getPosition().intX(),
                (int) collisionBox.getBounds().getY() - camera.getPosition().intY(),
                collisionBox.getBounds().width,
                collisionBox.getBounds().height
        );
    }
}
