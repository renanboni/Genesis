package entity.action;

import core.CollisionBox;
import core.Position;
import core.Size;
import game.Game;
import entity.MovingEntity;
import entity.effect.Sick;
import entity.humanoid.Humanoid;
import game.GameLoop;
import state.State;

public class Cough extends Action {

    private int lifeSpanInUpdates;
    private final double risk = 1d / 10;
    private Size spreadAreaSize;

    public Cough() {
        lifeSpanInUpdates = GameLoop.UPDATES_PER_SECONDS;
        spreadAreaSize = new Size(2 * Game.SPRITE_SIZE, 2 * Game.SPRITE_SIZE);
    }

    @Override
    public void update(State state, MovingEntity entity) {
        if (--lifeSpanInUpdates <= 0) {
            Position spreadAreaPosition = new Position(
                    entity.getPosition().getX() - spreadAreaSize.getWidth() / 2,
                    entity.getPosition().getY() - spreadAreaSize.getHeight() / 2
            );

            CollisionBox collisionBox = CollisionBox.of(spreadAreaPosition, spreadAreaSize);

            state.getGameObjectOfClass(Humanoid.class)
                    .stream()
                    .filter(movingEntity -> movingEntity.getCollisionBox().collidesWith(collisionBox))
                    .filter(movingEntity -> !movingEntity.isAffectedBy(Sick.class))
                    .forEach(movingEntity -> {
                        double fallOut = Math.random();

                        if (fallOut < risk) {
                            movingEntity.addEffect(new Sick());
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
