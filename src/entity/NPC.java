package entity;

import ai.AiManager;
import controller.EntityController;
import core.Motion;
import entity.humanoid.Humanoid;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

public class NPC extends Humanoid {
    private final AiManager manager;

    public NPC(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);

        this.manager = new AiManager();
        this.motion = new Motion(Math.random() + 1);
    }

    @Override
    public void update(State state) {
        super.update(state);
        manager.update(state, this);
    }

    @Override
    protected void handleCollision(GameObject other) {
        if (other instanceof Player) {
            motion.stop(withCollideX(other), withCollideY(other));
        }
    }
}
