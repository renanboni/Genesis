package entity.humanoid.action;

import core.CollisionBox;
import core.Position;
import core.Size;
import entity.Game;
import entity.effect.Sick;
import entity.humanoid.Humanoid;
import game.GameLoop;
import state.State;

public class Cough extends Action {

    private int lifeSpanInUpdates;
    private final double risk = 1d / 10;
    private final Size spreadAreaSize;

    public Cough() {
        lifeSpanInUpdates = GameLoop.UPDATES_PER_SECONDS;
        spreadAreaSize = new Size(2 * Game.SPRITE_SIZE, 2 * Game.SPRITE_SIZE);
    }

    @Override
    public void update(State state, Humanoid performer) {
        if (--lifeSpanInUpdates <= 0) {
            Position spreadAreaPosition = new Position(
                    performer.getPosition().getX() - spreadAreaSize.getWidth() / 2,
                    performer.getPosition().getY() - spreadAreaSize.getHeight() / 2
            );

            CollisionBox collisionBox = CollisionBox.of(spreadAreaPosition, spreadAreaSize);

            state.getGameObjectOfClass(Humanoid.class)
                    .stream()
                    .filter(humanoid -> humanoid.getCollisionBox().collidesWith(collisionBox))
                    .filter(humanoid -> !humanoid.isAffectedBy(Sick.class))
                    .forEach(humanoid -> {
                        double fallOut = Math.random();

                        if (fallOut < risk) {
                            humanoid.addEffect(new Sick());
                        }
                    });
        }
    }

    @Override
    public boolean isDone() {
        return lifeSpanInUpdates <= 0;
    }

    @Override
    public String getAnimationName() {
        return "cough";
    }
}
