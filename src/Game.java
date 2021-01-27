public class Game {

    private final Display display;

    public Game(int width, int height) {
        this.display = new Display(width, height);
    }

    public void render() {
        display.render(this);
    }

    public void update() {

    }
}
