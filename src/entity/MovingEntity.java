package entity;

import controller.Controller;
import core.Direction;
import core.Motion;
import game.GameObject;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    private Motion motion;
    private AnimationManager animationManager;
    private Direction direction;

    public MovingEntity(Controller controller, SpriteLibrary spriteLibrary) {
        super();
        this.controller = controller;
        this.motion = new Motion(2);
        direction = Direction.S;
        this.animationManager = new AnimationManager(spriteLibrary.getUnit("dave"));
    }

    public void update() {
        motion.update(controller);
        position.apply(motion);
        manageDirection();
        decideAnimation();
        animationManager.update(direction);
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
}
