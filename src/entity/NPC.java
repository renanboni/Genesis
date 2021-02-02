package entity;

import ai.AiManager;
import controller.EntityController;
import game.GameObject;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

public class NPC extends MovingEntity {
    private final AiManager manager;

    public NPC(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);

        this.animationManager = new AnimationManager(spriteLibrary.getUnit("matt"));
        this.manager = new AiManager();
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
