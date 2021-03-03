package model;

public abstract class Entity {
    public abstract Hash getID();

    public abstract Position getPosition();

    public abstract void setPosition(Position position);
}
