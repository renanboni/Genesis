package main;

import game.Game;
import game.GameLoop;
import network.Connection;

public class Client {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    private final Game game;
    private final GameLoop gameLoop;
    private Connection connection;

    public Client() {
        this.game = new Game(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.gameLoop = new GameLoop(game);
        this.connection = new Connection(this);
        this.connection.open("localhost", 36954);
    }

    public void start() {
        gameLoop.run();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
