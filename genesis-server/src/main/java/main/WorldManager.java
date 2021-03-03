package main;

import model.EntityList;
import model.Player;

public class WorldManager {

    private final Server server;
    private final EntityList<Player> players;

    public WorldManager(Server server) {
        this.server = server;
        this.players = new EntityList<>();
    }

    public void update() {
        synchronized (players) {

        }
    }

    public void addPlayer(Player player) {
        synchronized (players) {
            // player.onSessionStarted();
            players.add(player);
        }
    }
}
