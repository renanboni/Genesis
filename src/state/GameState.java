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
        this.gameObjects.add(player);
        camera.focusOn(player);
        initNPCs(200);
    }

    private void initNPCs(int amount) {
        for (int i = 0; i < amount; i++) {
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            this.gameObjects.add(npc);
        }
    }
}
