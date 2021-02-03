package entity.effect;

import entity.humanoid.Humanoid;
import game.GameLoop;
import state.State;

public class Caffeinated extends Effect {

    private double speedMultiplier = 2.5;

    public Caffeinated() {
        super(GameLoop.UPDATES_PER_SECONDS * 5);
    }

    @Override
    public void update(State state, Humanoid humanoid) {
        super.update(state, humanoid);

        humanoid.multiplySpeed(speedMultiplier);
    }
}
