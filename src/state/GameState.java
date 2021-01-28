package state;

import controller.PlayerController;
import entity.Player;
import input.Input;

public class GameState extends State {
    public GameState(Input input) {
        super(input);
        this.gameObjects.add(new Player(new PlayerController(input), spriteLibrary));
    }
}
