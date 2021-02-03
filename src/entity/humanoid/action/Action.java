package entity.humanoid.action;

import entity.humanoid.Humanoid;
import state.State;

public abstract class Action {
    public abstract void update(State state, Humanoid humanoid);
    public abstract boolean isDone();
    public abstract String getAnimationName();
}
