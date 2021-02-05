import entity.Game;
import game.GameLoop;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        new Thread(new GameLoop(new Game(800, 600))).start();
    }
}
