package game.settings;

public class GameSettings {

    private boolean debugMode;

    private final RenderSettings renderSettings;

    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        this.renderSettings = new RenderSettings();
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void toggleDebugMode() {
        debugMode = !debugMode;
    }

    public RenderSettings getRenderSettings() {
        return renderSettings;
    }
}
