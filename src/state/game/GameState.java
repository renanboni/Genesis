package state.game;

import controller.NPCController;
import controller.PlayerController;
import core.Condition;
import core.Size;
import entity.NPC;
import entity.Player;
import entity.SelectionCircle;
import entity.effect.Isolated;
import entity.effect.Sick;
import entity.humanoid.Humanoid;
import game.Game;
import state.game.ui.UiGameTime;
import state.game.ui.UiSicknessStatistics;
import input.Input;
import map.GameMap;
import state.State;
import state.menu.MenuState;
import ui.Alignment;
import ui.UiText;
import ui.VerticalContainer;
import ui.clickable.UiButton;

import java.awt.*;
import java.util.List;

public class GameState extends State {

    private List<Condition> victoryConditions;
    private List<Condition> defeatConditions;

    private boolean isPlaying;

    public GameState(Size windowSize, Input input) {
        super(windowSize, input);
        this.gameMap = new GameMap(new Size(20, 20), spriteLibrary);

        this.isPlaying = true;

        initializeCharacters();
        initializeUi(windowSize);
        initializeConditions();
    }

    @Override
    public void update(Game game) {
        super.update(game);

        if (isPlaying) {
            if (victoryConditions.stream().allMatch(Condition::isMet)) {
                win();
            }

            if (defeatConditions.stream().allMatch(Condition::isMet)) {
                lose();
            }
        }
    }

    private void win() {
        isPlaying = false;

        VerticalContainer container = new VerticalContainer(camera.getSize());
        container.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        container.setBackgroundColor(Color.DARK_GRAY);
        container.addUiComponent(new UiButton("Menu", (state) -> state.setNextState(new MenuState(windowsSize, input))));
        container.addUiComponent(new UiButton("Options", (state) -> System.out.println("Button 1 pressed")));
        container.addUiComponent(new UiButton("Exit", (state) -> System.out.println("Button 1 pressed")));

        uiContainers.add(container);
    }

    private void lose() {
        isPlaying = false;

        VerticalContainer container = new VerticalContainer(camera.getSize());
        container.setAlignment(new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER));
        container.addUiComponent(new UiText("DEFEAT!"));

        uiContainers.add(container);
    }

    private void initializeConditions() {
        victoryConditions = List.of(() -> getNumberOfSick() > 0);
        defeatConditions = List.of(() -> (float) getNumberOfSick() / getNumberOfNpcs() > 0.25);
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
                .filter(npc -> npc.isAffectedBy(Sick.class) && !npc.isAffectedBy(Isolated.class))
                .count();
    }

    public long getNumberOfIsolated() {
        return getGameObjectOfClass(NPC.class)
                .stream()
                .filter(npc -> npc.isAffectedBy(Sick.class) && npc.isAffectedBy(Isolated.class))
                .count();
    }

    public long getNumberOfHealthy() {
        return getGameObjectOfClass(NPC.class)
                .stream()
                .filter(npc -> !npc.isAffectedBy(Sick.class))
                .count();
    }

    private long getNumberOfNpcs() {
        return getGameObjectOfClass(NPC.class).size();
    }
}
