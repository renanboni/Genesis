package entity;

import controller.Controller;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class Player extends MovingEntity {

    public Player(Controller controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        this.animationManager = new AnimationManager(spriteLibrary.getUnit("dave"));
    }

    @Override
    public void update() {
        super.update();
    }
}
