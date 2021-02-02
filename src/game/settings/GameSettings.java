package game.settings;

public class GameSettings {

    private boolean debugMode;

    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void toggleDebugMode() {
        debugMode = !debugMode;
    }
}
