package game.settings;

public class GameSettings {

    private boolean debugMode;

    private final RenderSettings renderSettings;
    private final EditorSettings editorSettings;

    public GameSettings(boolean debugMode) {
        this.debugMode = debugMode;
        this.renderSettings = new RenderSettings();
        this.editorSettings = new EditorSettings();
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

    public EditorSettings getEditorSettings() {
        return editorSettings;
    }
}
