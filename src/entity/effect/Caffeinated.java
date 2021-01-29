package entity.effect;

import entity.MovingEntity;
import game.GameLoop;
import state.State;

public class Caffeinated extends Effect {

    private double speedMultiplier = 2.5;

    public Caffeinated() {
        super(GameLoop.UPDATES_PER_SECONDS * 5);
    }

    @Override
    public void update(State state, MovingEntity movingEntity) {
        super.update(state, movingEntity);

        movingEntity.multiplySpeed(speedMultiplier);
    }
}
