package entity.effect;

import entity.humanoid.Humanoid;
import entity.humanoid.action.Cough;
import game.GameLoop;
import state.State;

public class Sick extends Effect {

    private static final double COUGH_RATE = 1d / GameLoop.UPDATES_PER_SECONDS / 10;

    public Sick() {
        super(Integer.MAX_VALUE);
    }

    @Override
    public void update(State state, Humanoid humanoid) {
        super.update(state, humanoid);

        if (Math.random() < COUGH_RATE) {
            humanoid.perform(new Cough());
        }
    }
}
