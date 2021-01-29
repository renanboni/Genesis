package entity;

import controller.Controller;
import core.Direction;
import core.Motion;
import entity.effect.Effect;
import game.GameObject;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    protected Motion motion;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected List<Effect> effects;

    public MovingEntity(Controller controller, SpriteLibrary spriteLibrary) {
        super();
        this.controller = controller;
        this.motion = new Motion(2);
        direction = Direction.S;
        effects = new ArrayList<>();
    }

    public void update(State state) {
        motion.update(controller);
        animationManager.update(direction);

        effects.forEach(effect -> effect.update(state, this));

        manageDirection();
        decideAnimation();

        position.apply(motion);

        cleanUp();
    }

    private void cleanUp() {
        List.copyOf(effects)
                .stream()
                .filter(Effect::shouldDelete)
                .forEach(effects::remove);
    }

    private void decideAnimation() {
        if (motion.isMoving()) {
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
    public Image getSprite() {
        return animationManager.getSprite();
    }

    public Controller getController() {
        return controller;
    }

    public void multiplySpeed(double speedMultiplier) {
        motion.multiply(speedMultiplier);
    }
}
