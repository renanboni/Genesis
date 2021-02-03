package entity;

import core.CollisionBox;
import core.Position;
import core.Size;
import display.Camera;
import state.State;

import java.awt.*;

public abstract class GameObject {

    protected Position position;
    protected Position renderOffset = new Position(0, 0);
    protected Size size;
    protected GameObject parent;
    protected int renderOrder;

    public GameObject() {
        position = new Position(0, 0);
        size = new Size(64, 64);
        renderOrder = 5;
    }

    public boolean collidesWith(GameObject other) {
        return getCollisionBox().collidesWith(other.getCollisionBox());
    }

    public abstract void update(State state);

    public abstract CollisionBox getCollisionBox();

    public abstract Image getSprite();

    public Position getPosition() {
        Position finalPosition = Position.copyOf(position);

        if (parent != null) {
            finalPosition.add(parent.getPosition());
        }

        return finalPosition;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Size getSize() {
        return size;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public Position getRenderPosition(Camera camera) {
        return new Position(
                getPosition().getX() - camera.getPosition().getX() - renderOffset.getX(),
                getPosition().getY() - camera.getPosition().getY() - renderOffset.getY()
        );
    }

    public int getRenderOrder() {
        return renderOrder;
    }
}
