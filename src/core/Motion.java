package core;

import controller.EntityController;

public class Motion {

    private Vector2D vector;
    private final double speed;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public void update(EntityController controller) {
        int deltaX = 0;
        int deltaY = 0;

        if (controller.isRequestingUp()) {
            deltaY--;
        }

        if (controller.isRequestingDown()) {
            deltaY++;
        }

        if (controller.isRequestingLeft()) {
            deltaX--;
        }

        if (controller.isRequestingRight()) {
            deltaX++;
        }

        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed);
    }

    public Vector2D getVector() {
        return vector;
    }

    public boolean isMoving() {
        return vector.length() > 0;
    }

    public void multiply(double speedMultiplier) {
        vector.multiply(speedMultiplier);
    }

    public void stop(boolean stopX, boolean stopY) {
        vector = new Vector2D(
                stopX ? 0 : vector.getX(),
                stopY ? 0 : vector.getX()
        );
    }

    public Vector2D getDirection() {
        Vector2D direction = Vector2D.copyOf(vector);
        direction.normalize();

        return direction;
    }
}
