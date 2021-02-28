package game.settings;

public class RenderSettings {

    private final Value<Boolean> shouldRenderGrid;

    public RenderSettings() {
        shouldRenderGrid = new Value<>(false);
    }

    public Value<Boolean> getShouldRenderGrid() {
        return shouldRenderGrid;
    }
}
