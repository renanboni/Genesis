package ai;

import ai.state.AiState;
import ai.state.Stand;
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
        System.out.println("Transitioning to " + nextState);
        switch (nextState) {
            case "stand":
            default:
                currentAiState = new Stand();
        }
    }
}
