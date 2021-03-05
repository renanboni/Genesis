package entity;

import controller.NPCController;
import core.Direction;
import core.Vector2D;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import gfx.SpriteSet;

public class Bubble extends MovingEntity {

    private boolean halted;

    public Bubble(NPCController controller) {
        super(controller);

        this.animationManager = new AnimationManager(new SpriteSet(SpriteLibrary.getInstance().getImage("bubble")), false);
    }

    @Override
    protected void handleCollision(GameObject other) {

    }

    @Override
    protected void handleMotion() {
        if (!halted) {
            motion.add(new Vector2D(0, -0.5));
        }

        halted = false;
        direction = Direction.S;
    }

    @Override
    protected String decideAnimation() {
        return "default";
    }

    public void halt() {
        halted = true;
    }

    public void insert(GameObject gameObject) {
        this.position = gameObject.getPosition();
        this.renderOffset = gameObject.getRenderOffset();
        this.collisionBoxOffset = renderOffset;
        gameObject.parent(this);
    }
}
