package ai.state;

import ai.AiTransition;
import controller.NPCController;
import core.Position;
import entity.NPC;
import state.State;

import java.util.ArrayList;
import java.util.List;

public class Wander extends AiState {

    private List<Position> targets;

    public Wander() {
        super();
        targets = new ArrayList<>();
    }

    @Override
    protected AiTransition initTransition() {
        return new AiTransition("stand", ((state, currentCharacter) -> arrived(currentCharacter)));
    }

    @Override
    public void update(State state, NPC currentCharacter) {
        if (targets.isEmpty()) {
            targets.add(state.getRandomPosition());
        }

        NPCController controller = (NPCController) currentCharacter.getController();
        controller.moveToTarget(targets.get(0), currentCharacter.getPosition());

        if (arrived(currentCharacter)) {
            controller.stop();
        }
    }

    private boolean arrived(NPC currentCharacter) {
        return currentCharacter.getPosition().isInRangeOf(targets.get(0));
    }
}
