package game.settings;

public class EditorSettings {
    private Value<Boolean> autoTile;

    public EditorSettings() {
        autoTile = new Value<>(false);
    }

    public Value<Boolean> getAutoTile() {
        return autoTile;
    }

    public void setAutoTile(Value<Boolean> autoTile) {
        this.autoTile = autoTile;
    }
}
