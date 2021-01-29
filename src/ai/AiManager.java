package ai;

import ai.state.AiState;
import ai.state.Stand;
import ai.state.Wander;
import entity.NPC;
import state.State;

public class AiManager {

    private AiState currentAiState;

    public AiManager() {
        transitionTo("stand");
    }

    public void update(State state, NPC currentCharacter) {
        currentAiState.update(state, currentCharacter);

        if (currentAiState.shouldTransition(state, currentCharacter)) {
            transitionTo(currentAiState.getNextState());
        }
    }

    private void transitionTo(String nextState) {
        switch (nextState) {
            case "wander":
                currentAiState = new Wander();
                return;
            case "stand":
            default:
                currentAiState = new Stand();
        }
    }
}
