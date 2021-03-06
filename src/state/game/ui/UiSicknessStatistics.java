package state.game.ui;

import core.Size;
import state.game.GameState;
import state.State;
import ui.*;

public class UiSicknessStatistics extends HorizontalContainer {

    private UiText numberOfSick;
    private UiText numberOfHealthy;

    public UiSicknessStatistics(Size windowSize) {
        super(windowSize);

        this.numberOfSick = new UiText("");
        this.numberOfHealthy = new UiText("");

        UIContainer sickContainer = new VerticalContainer(windowSize);
        sickContainer.setPadding(new Spacing(0));
        sickContainer.addUiComponent(new UiText("SICK"));
        sickContainer.addUiComponent(numberOfSick);

        UIContainer healthyContainer = new VerticalContainer(windowSize);
        healthyContainer.setPadding(new Spacing(0));
        healthyContainer.addUiComponent(new UiText("HEALHTY"));
        healthyContainer.addUiComponent(numberOfHealthy);

        addUiComponent(sickContainer);
        addUiComponent(healthyContainer);
    }

    @Override
    public void update(State state) {
        super.update(state);

        if (state instanceof GameState) {
            GameState gameState = (GameState) state;

            numberOfSick.setText(String.format("%d (%d)", gameState.getNumberOfSick(), gameState.getNumberOfIsolated()));
            numberOfHealthy.setText(String.valueOf(gameState.getNumberOfHealthy()));
        }
    }
}
