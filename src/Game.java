import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Display display;
    private final List<GameObject> gameObjects;

    public Game(int width, int height) {
        this.display = new Display(width, height);
        this.gameObjects = new ArrayList<>();
    }

    public void render() {
        display.render(this);
    }

    public void update() {
        gameObjects.forEach(GameObject::update);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
