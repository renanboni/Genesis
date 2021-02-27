package entity.humanoid;

import controller.EntityController;
import core.Position;
import core.Size;
import entity.GameObject;
import entity.MovingEntity;
import entity.effect.Effect;
import entity.humanoid.action.Action;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import state.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Humanoid extends MovingEntity {

    protected List<Effect> effects;
    protected Optional<Action> action;
    private static final List<String> availableCharacters = new ArrayList<>(List.of("dave", "matt", "melissa", "roger"));

    public Humanoid(EntityController controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);

        effects = new ArrayList<>();
        action = Optional.empty();

        this.animationManager = new AnimationManager(spriteLibrary.getSpriteSet(getRandomCharacter()));

        collisionBoxSize = new Size(16, 28);
        renderOffset = new Position(size.getWidth() / 2, size.getHeight() - 12);
        collisionBoxOffset = new Position(collisionBoxSize.getWidth() / 2, collisionBoxSize.getHeight());
    }

    private String getRandomCharacter() {
        Collections.shuffle(availableCharacters);
        return availableCharacters.get(0);
    }

    @Override
    protected void handleCollision(GameObject other) {

    }

    @Override
    protected void handleMotion() {
        if (action.isPresent()) {
            motion.stop(true, true);
        }
    }

    public void update(State state) {
        super.update(state);
        handleAction(state);
        effects.forEach(effect -> effect.update(state, this));
        cleanUp();
    }

    @Override
    protected String decideAnimation() {
        if (action.isPresent()) {
            return action.get().getAnimationName();
        } else if (motion.isMoving()) {
            return "walk";
        }

        return "stand";
    }

    private void cleanUp() {
        List.copyOf(effects)
                .stream()
                .filter(Effect::shouldDelete)
                .forEach(effects::remove);

        if (action.isPresent() && action.get().isDone()) {
            action = Optional.empty();
        }
    }

    private void handleAction(State state) {
        action.ifPresent(value -> value.update(state, this));
    }

    public void perform(Action action) {
        if (this.action.isPresent() && !this.action.get().isInterruptable()) {
            return;
        }

        this.action = Optional.of(action);
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    protected void clearEffects() {
        effects.clear();
    }

    public boolean isAffectedBy(Class<?> clazz) {
        return effects.stream().anyMatch(clazz::isInstance);
    }

    public List<Effect> getEffects() {
        return effects;
    }
}
