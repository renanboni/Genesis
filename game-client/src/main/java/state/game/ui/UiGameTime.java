package state.game.ui;

import state.State;
import ui.Alignment;
import ui.HorizontalContainer;
import ui.UiText;

public class UiGameTime extends HorizontalContainer {

    private UiText gameTime;

    public UiGameTime() {
        this.alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.START);
        this.gameTime = new UiText("");
        addUiComponent(gameTime);
    }

    @Override
    public void update(State state) {
        super.update(state);
        gameTime.setText(state.getTime().getFormattedTime());
    }
}
