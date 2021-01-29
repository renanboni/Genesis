package entity.action;

import entity.MovingEntity;
import game.GameLoop;
import state.State;

public class Cough extends Action {

    private int lifeSpanInUpdates;

    public Cough() {
        lifeSpanInUpdates = GameLoop.UPDATES_PER_SECONDS;
    }

    @Override
    public void update(State state, MovingEntity movingEntity) {
        lifeSpanInUpdates--;
    }

    @Override
    public boolean isDone() {
        return lifeSpanInUpdates <= 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
}
