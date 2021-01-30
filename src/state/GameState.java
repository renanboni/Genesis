package state;

import controller.NPCController;
import controller.PlayerController;
import core.Size;
import entity.NPC;
import entity.Player;
import entity.effect.Sick;
import input.Input;
import map.GameMap;
import ui.Spacing;
import ui.UIContainer;

public class GameState extends State {
    public GameState(Size windowSize, Input input) {
        super(windowSize, input);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        initializeCharacters();
        initializeUi();
    }

    private void initializeUi() {
        UIContainer container = new UIContainer();
        container.setPadding(new Spacing(50));
        uiContainers.add(container);
    }

    private void initializeCharacters() {
        Player player = new Player(new PlayerController(input), spriteLibrary);
        this.gameObjects.add(player);
        camera.focusOn(player);
        initNPCs(50);
    }

    private void initNPCs(int amount) {
        for (int i = 0; i < amount; i++) {
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            npc.addEffect(new Sick());
            this.gameObjects.add(npc);
        }
    }
}
