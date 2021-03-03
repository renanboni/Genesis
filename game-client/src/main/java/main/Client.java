package main;

import game.Game;
import game.GameLoop;
import codec.Packet;
import codec.PacketBuilder;
import model.Hash;
import network.Connection;
import org.apache.mina.core.RuntimeIoException;

public class Client {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    private final Game game;
    private final GameLoop gameLoop;
    private final Connection connection;

    public Client() {
        this.game = new Game(DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
        this.gameLoop = new GameLoop(game);
        this.connection = new Connection(this);
    }

    public void start() {
        gameLoop.run();
    }

    public void login(String username, String password) {
        Hash usernameHash = new Hash(username.toLowerCase());
        Hash passwordHash = new Hash(usernameHash + password);

        PacketBuilder packetBuilder = new PacketBuilder(Packet.Type.LOGIN_SEND);

        try {
            this.connection.open("localhost", 36954);

            packetBuilder.putHash(usernameHash);
            packetBuilder.putHash(passwordHash);
        } catch (RuntimeIoException ex) {
            ex.printStackTrace();
        } finally {
            connection.write(packetBuilder);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void update() {
        connection.update();
    }

    public void loginSuccess() {
        game.enterState(game.getGameState());
    }
}









