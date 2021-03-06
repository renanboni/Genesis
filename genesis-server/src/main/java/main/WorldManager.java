package main;

import model.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final Server server;
    private final List<Player> players;
    protected long lastPlayerUpdate;

    public static final int PLAYER_UPDATE_DELAY = 200;

    public WorldManager(Server server) {
        this.server = server;
        this.players = new ArrayList<>();
        this.lastPlayerUpdate = 0;
    }

    public void update(long now) {
        if (now - lastPlayerUpdate > PLAYER_UPDATE_DELAY) {
            lastPlayerUpdate = now;

            synchronized (players) {
                for (Player player: players) {
                    player.update(players);
                }
            }
        }
    }

    public void addPlayer(Player player) {
        synchronized (players) {
            players.add(player);
        }
    }
}
