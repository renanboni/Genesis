package entity;

import controller.EntityController;
import core.*;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

import java.awt.*;

public abstract class MovingEntity extends GameObject {

    protected EntityController entityController;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;

    protected Size collisionBoxSize;
    protected Vector2D directionVector;

    public MovingEntity(EntityController controller, SpriteLibrary spriteLibrary) {
        super();
        entityController = controller;
        motion = new Motion(2);
        direction = Direction.S;
        directionVector = new Vector2D(0, 0);
        collisionBoxSize = new Size(size.getWidth(), size.getHeight());
    }

    public void update(State state) {
        motion.update(entityController);
        handleMotion();
        animationManager.update(direction);

        handleCollisions(state);
        manageDirection();
        animationManager.playAnimation(decideAnimation());

        position.apply(motion);
    }

    protected boolean isFacing(Position other) {
        Vector2D direction = Vector2D.directionBetweenPositions(other, getPosition());
        double dotProduct = Vector2D.dotProduct(direction, directionVector);

        return dotProduct > 0;
    }

    private void handleCollisions(State state) {
        state.getCollidingGameObjects(this).forEach(this::handleCollision);
    }

    protected abstract void handleCollision(GameObject other);

    protected abstract void handleMotion();

    protected abstract String decideAnimation();

    private void manageDirection() {
        if (motion.isMoving()) {
            this.direction = Direction.fromMotion(motion);
            this.directionVector = motion.getDirection();
        }
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public EntityController getController() {
        return entityController;
    }

    @Override
    public CollisionBox getCollisionBox() {
        Position positionWithMotion = Position.copyOf(getPosition());
        positionWithMotion.apply(motion);
        positionWithMotion.subtract(collisionBoxOffset);

        return new CollisionBox(new Rectangle(
                positionWithMotion.intX(),
                positionWithMotion.intY(),
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight()
        ));
    }

    protected boolean withCollideX(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithXApplied = Position.copyOf(position);
        positionWithXApplied.applyX(motion);
        positionWithXApplied.subtract(collisionBoxOffset);

        return CollisionBox.of(positionWithXApplied, collisionBoxSize).collidesWith(otherBox);
    }

    protected boolean withCollideY(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithYApplied = Position.copyOf(position);
        positionWithYApplied.applyY(motion);
        positionWithYApplied.subtract(collisionBoxOffset);

        return CollisionBox.of(positionWithYApplied, collisionBoxSize).collidesWith(otherBox);
    }

    public void multiplySpeed(double speedMultiplier) {
        motion.multiply(speedMultiplier);
    }
}
