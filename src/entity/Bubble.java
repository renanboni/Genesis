package entity;

import controller.NPCController;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import gfx.SpriteSet;

public class Bubble extends MovingEntity {

    private final NPCController controller;

    public Bubble(NPCController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        this.controller = controller;

        this.animationManager = new AnimationManager(new SpriteSet(spriteLibrary.getImage("bubble")), false);
    }

    @Override
    protected void handleCollision(GameObject other) {

    }

    @Override
    protected void handleMotion() {
        motion.update(controller);
    }

    @Override
    protected String decideAnimation() {
        return "default";
    }

    public void insert(GameObject gameObject) {
        this.position = gameObject.getPosition();
        this.renderOffset = gameObject.getRenderOffset();
        gameObject.parent(this);
    }
}
