package entity;

import controller.EntityController;
import entity.effect.Isolated;
import entity.humanoid.action.BlowBubble;
import entity.humanoid.Humanoid;
import game.Game;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

import java.util.Comparator;
import java.util.Optional;

public class Player extends Humanoid {

    private NPC target;
    private double targetRange;
    private SelectionCircle selectionCircle;

    public Player(EntityController controller, SpriteLibrary spriteLibrary, SelectionCircle selectionCircle) {
        super(controller, spriteLibrary);
        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSet("dave"));
        this.targetRange = Game.SPRITE_SIZE;
        this.selectionCircle = selectionCircle;
    }

    @Override
    public void update(State state) {
        super.update(state);
        handleTarget(state);
        handleInput(state);
    }

    private void handleInput(State state) {
        if (entityController.isRequestingAction()) {
            if (target != null) {
                perform(new BlowBubble(target));
            }
        }
    }

    private void handleTarget(State state) {
        Optional<NPC> closestNPC = findClosestNPC(state);

        if (closestNPC.isPresent()) {
            NPC npc = closestNPC.get();

            if (!npc.equals(target)) {
                selectionCircle.parent(npc);
                target = npc;
            }
        } else {
            selectionCircle.clearParent();
            target = null;
        }
    }

    private Optional<NPC> findClosestNPC(State state) {
        return state
                .getGameObjectOfClass(NPC.class)
                .stream()
                .filter(npc -> getPosition().distanceTo(npc.getPosition()) < targetRange)
                .filter(npc -> isFacing(npc.getPosition()))
                .filter(npc -> !npc.isAffectedBy(Isolated.class))
                .min(Comparator.comparingDouble(npc -> position.distanceTo(npc.getPosition())));
    }

    @Override
    protected void handleCollision(GameObject other) {

    }
}
