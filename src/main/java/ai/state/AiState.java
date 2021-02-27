package ai.state;

import ai.AiTransition;
import entity.NPC;
import state.State;

public abstract class AiState {

    private AiTransition transition;

    public AiState() {
        this.transition = initTransition();
    }

    protected abstract AiTransition initTransition();
    public abstract void update(State state, NPC currentCharacter);

    public boolean shouldTransition(State state, NPC currentCharacter) {
        return transition.shouldTransition(state, currentCharacter);
    }

    public String getNextState() {
        return transition.getNextState();
    }
}
