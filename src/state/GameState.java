package state;

import controller.NPCController;
import controller.PlayerController;
import core.Size;
import entity.NPC;
import entity.Player;
import entity.SelectionCircle;
import entity.effect.Sick;
import game.ui.UiGameTime;
import game.ui.UiSicknessStatistics;
import input.Input;
import map.GameMap;

public class GameState extends State {
    public GameState(Size windowSize, Input input) {
        super(windowSize, input);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        initializeCharacters();
        initializeUi(windowSize);
    }

    private void initializeUi(Size windowSize) {
        uiContainers.add(new UiGameTime(windowSize));
        uiContainers.add(new UiSicknessStatistics(windowSize));
    }

    private void initializeCharacters() {
        Player player = new Player(new PlayerController(input), spriteLibrary);
        this.gameObjects.add(player);
        camera.focusOn(player);

        SelectionCircle circle = new SelectionCircle();
        circle.setParent(player);
        gameObjects.add(circle);

        initNPCs(50);
        makeNumberOfNpcsSick(10);
    }

    private void makeNumberOfNpcsSick(int number) {
        getGameObjectOfClass(NPC.class)
                .stream()
                .limit(number)
                .forEach(npc -> npc.addEffect(new Sick()));
    }

    private void initNPCs(int amount) {
        for (int i = 0; i < amount; i++) {
            NPC npc = new NPC(new NPCController(), spriteLibrary);
            npc.setPosition(gameMap.getRandomPosition());
            this.gameObjects.add(npc);
        }
    }
}
