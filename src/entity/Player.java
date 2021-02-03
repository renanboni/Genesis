package entity;

import controller.EntityController;
import entity.effect.Caffeinated;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class Player extends MovingEntity {

    public Player(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        this.animationManager = new AnimationManager(spriteLibrary.getUnit("dave"));
        effects.add(new Caffeinated());
    }

    @Override
    protected void handleCollision(GameObject other) {

    }
}
