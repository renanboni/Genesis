package main;

import entity.Player;
import game.Game;
import game.GameLoop;
import codec.Packet;
import codec.PacketBuilder;
import network.Connection;
import org.apache.mina.core.RuntimeIoException;
import state.GameState;

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

    public void loginOrRegister(String username, String password, boolean isLogin) {
        PacketBuilder packetBuilder = isLogin ? new PacketBuilder(Packet.Type.LOGIN_SEND) : new PacketBuilder(Packet.Type.SIGN_UP_SEND);

        try {
            this.connection.open("localhost", 36954);

            packetBuilder.putString(username);
            packetBuilder.putString(password);
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

    public void loginSuccess(Player player) {
        game.init(player);
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
    }

    public Game getGame() {
        return game;
    }

    public void loginError(String msg) {
        System.err.println(msg);
    }
}
