package ai.state;

import ai.AiTransition;
import entity.NPC;
import state.State;

public class Stand extends AiState {

    private int updatesAlive;

    @Override
    protected AiTransition initTransition() {
        return new AiTransition("wander", ((state, currentCharacter) -> updatesAlive >= state.getTime().getUpdatesFromSecond(3) ));
    }

    @Override
    public void update(State state, NPC currentCharacter) {
        updatesAlive++;
    }
}
