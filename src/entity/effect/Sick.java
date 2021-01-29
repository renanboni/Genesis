package entity.effect;

import entity.MovingEntity;
import entity.action.Cough;
import game.GameLoop;
import state.State;

public class Sick extends Effect {

    private static final double COUGH_RATE = 1d / GameLoop.UPDATES_PER_SECONDS / 10;

    public Sick() {
        super(Integer.MAX_VALUE);
    }

    @Override
    public void update(State state, MovingEntity movingEntity) {
        super.update(state, movingEntity);

        if (Math.random() < COUGH_RATE) {
            movingEntity.perform(new Cough());
        }
    }
}
