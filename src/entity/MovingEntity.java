package entity;

import controller.Controller;
import core.*;
import entity.action.Action;
import entity.action.Cough;
import entity.effect.Effect;
import entity.effect.Sick;
import game.GameObject;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected List<Effect> effects;
    protected Optional<Action> action;
    protected Size collisionBoxSize;

    public MovingEntity(Controller controller, SpriteLibrary spriteLibrary) {
        super();
        this.controller = controller;
        this.motion = new Motion(2);
        direction = Direction.S;
        effects = new ArrayList<>();
        action = Optional.empty();
        collisionBoxSize = new Size(16, 28);
    }

    public void update(State state) {
        handleAction(state);
        handleMotion();
        animationManager.update(direction);

        effects.forEach(effect -> effect.update(state, this));

        handleCollisions(state);
        manageDirection();
        decideAnimation();

        position.apply(motion);

        cleanUp();
    }

    private void handleCollisions(State state) {
        state.getCollidingGameObjects(this).forEach(this::handleCollision);
    }

    protected abstract void handleCollision(GameObject other);

    private void handleMotion() {
        if (action.isEmpty()) {
            motion.update(controller);
        } else {
            motion.stop(true, true);
        }
    }

    private void handleAction(State state) {
        action.ifPresent(value -> value.update(state, this));
    }

    private void cleanUp() {
        List.copyOf(effects)
                .stream()
                .filter(Effect::shouldDelete)
                .forEach(effects::remove);

        if (action.isPresent() && action.get().isDone()) {
            action = Optional.empty();
        }
    }

    private void decideAnimation() {
        if (action.isPresent()) {
            animationManager.playAnimation(action.get().getAnimationName());
        } else if (motion.isMoving()) {
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    private void manageDirection() {
        if (motion.isMoving()) {
            this.direction = Direction.fromMotion(motion);
        }
    }

    @Override
    public boolean collidesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public CollisionBox getCollisionBox() {
        Position positionWithMotion = Position.copyOf(position);
        positionWithMotion.apply(motion);

        return new CollisionBox(new Rectangle(
                positionWithMotion.intX(),
                positionWithMotion.intY(),
                collisionBoxSize.getWidth(),
                collisionBoxSize.getHeight()
        ));
    }

    protected boolean withCollideY(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithYApplied = Position.copyOf(position);
        positionWithYApplied.applyY(motion);

        return CollisionBox.of(positionWithYApplied, collisionBoxSize).collidesWith(otherBox);
    }

    protected boolean withCollideX(GameObject other) {
        CollisionBox otherBox = other.getCollisionBox();
        Position positionWithXApplied = Position.copyOf(position);
        positionWithXApplied.applyX(motion);

        return CollisionBox.of(positionWithXApplied, collisionBoxSize).collidesWith(otherBox);
    }

    public void multiplySpeed(double speedMultiplier) {
        motion.multiply(speedMultiplier);
    }

    public void perform(Action action) {
        this.action = Optional.of(action);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public boolean isAffectedBy(Class<?> clazz) {
        return effects.stream().anyMatch(clazz::isInstance);
    }
}
