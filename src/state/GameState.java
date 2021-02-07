package state;

import controller.NPCController;
import controller.PlayerController;
import core.Size;
import entity.NPC;
import entity.Player;
import entity.SelectionCircle;
import entity.effect.Isolated;
import entity.effect.Sick;
import entity.humanoid.Humanoid;
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
        SelectionCircle circle = new SelectionCircle();
        Player player = new Player(new PlayerController(input), spriteLibrary, circle);
        gameObjects.add(player);
        camera.focusOn(player);
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

    public long getNumberOfSick() {
        return getGameObjectOfClass(Humanoid.class)
                .stream()
                .filter(humanoid -> humanoid.isAffectedBy(Sick.class) && !humanoid.isAffectedBy(Isolated.class))
                .count();
    }

    public long getNumberOfIsolated() {
        return getGameObjectOfClass(Humanoid.class)
                .stream()
                .filter(humanoid -> humanoid.isAffectedBy(Sick.class) && humanoid.isAffectedBy(Isolated.class))
                .count();
    }

    public long getNumberOfHealthy() {
        return getGameObjectOfClass(Humanoid.class)
                .stream()
                .filter(humanoid -> !humanoid.isAffectedBy(Sick.class))
                .count();
    }
}
