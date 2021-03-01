package ai;

import entity.NPC;
import state.State;

public interface AiCondition {
    boolean isMet(State state, NPC currentCharacter);
}
