package entity;

import controller.Controller;
import core.Movement;
import game.GameObject;

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    private Movement movement;

    public MovingEntity(Controller controller) {
        super();
        this.controller = controller;
        this.movement = new Movement(2);
    }

    public void update() {
        movement.update(controller);
        position.apply(movement);
    }
}
