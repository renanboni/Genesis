package entity;

import ai.AiManager;
import controller.Controller;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

public class NPC extends MovingEntity {
    private AiManager manager;

    public NPC(Controller controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);

        this.animationManager = new AnimationManager(spriteLibrary.getUnit("matt"));
        this.manager = new AiManager();
    }

    @Override
    public void update(State state) {
        super.update(state);
        manager.update(state, this);
    }
}
