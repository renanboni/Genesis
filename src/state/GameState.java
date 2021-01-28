package state;

import controller.NPCController;
import controller.PlayerController;
import core.Position;
import core.Size;
import entity.Game;
import entity.NPC;
import entity.Player;
import input.Input;
import map.GameMap;

import java.util.List;

public class GameState extends State {
    public GameState(Size windowSize, Input input) {
        super(windowSize, input);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        initializeCharacters();
    }

    private void initializeCharacters() {
        Player player = new Player(new PlayerController(input), spriteLibrary);
        NPC npc = new NPC(new NPCController(), spriteLibrary);
        npc.setPosition(new Position(3 * Game.SPRITE_SIZE, 2 * Game.SPRITE_SIZE));

        this.gameObjects.addAll(List.of(npc, player));
        camera.focusOn(player);
    }
}
