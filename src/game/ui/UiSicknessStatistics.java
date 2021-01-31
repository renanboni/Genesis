package game.ui;

import core.Size;
import entity.MovingEntity;
import entity.effect.Sick;
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

        long sickCount = state
                .getGameObjects()
                .stream()
                .filter(gameObject -> gameObject instanceof MovingEntity)
                .map(gameObject -> (MovingEntity) gameObject)
                .filter(movingEntity -> movingEntity.isAffectedBy(Sick.class))
                .count();

        long healthyCount = state
                .getGameObjects()
                .stream()
                .filter(gameObject -> gameObject instanceof MovingEntity)
                .map(gameObject -> (MovingEntity) gameObject)
                .filter(movingEntity -> !movingEntity.isAffectedBy(Sick.class))
                .count();

        numberOfSick.setText(String.valueOf(sickCount));
        numberOfHealthy.setText(String.valueOf(healthyCount));
    }
}
