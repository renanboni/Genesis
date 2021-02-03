package display;

import core.CollisionBox;
import entity.GameObject;
import state.State;

import java.awt.*;

public class DebugRenderer {

    public static void render(State state, Graphics graphics) {
        Camera camera = state.getCamera();

        state.getGameObjects()
                .stream()
                .filter(camera::isInView)
                .map(GameObject::getCollisionBox)
                .forEach(collisionBox -> drawCollisionBox(collisionBox, graphics, camera));
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
