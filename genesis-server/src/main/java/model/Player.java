package model;

import codec.Packet;
import codec.PacketBuilder;
import main.Server;
import main.WorldManager;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Player extends Entity {

    private Hash id;
    private Position position;

    private final IoSession session;
    private final WorldManager worldManager;
    private final Server server;

    private List<Player> knownPlayers;

    public Player(IoSession session, WorldManager worldManager, Server server) {
        this.session = session;
        this.worldManager = worldManager;
        this.server = server;
        this.knownPlayers = new ArrayList<>();
    }

    public void update(List<Player> players) {
        if (knownPlayers.containsAll(players)) {
            return;
        }

        // Pick up the unknown players
        List<Player> unknownPlayers = new ArrayList<>();

        for (Player player: players) {
            if (!knownPlayers.contains(player)) {
                unknownPlayers.add(player);
            }
        }

        // Add to the knownPlayers list
        knownPlayers.addAll(unknownPlayers);

        sendAddPlayers(unknownPlayers);
    }

    /**
     * Send a packet to the players saying there is a new logged in player
     *
     * @param players List of connected players
     */
    public void sendAddPlayers(Collection<Player> players) {

        // Remove ourselves
        int playersCount = players.size();
        if (players.contains(this)) {
            playersCount--;
        }

        // If there's no other player, do nothing
        if (playersCount == 0) {
            return;
        }

        // Send a packet with all the player's info
        PacketBuilder packet = new PacketBuilder(Packet.Type.PLAYERS_ADD_RESPONSE);

        packet.putShort((short) playersCount);

        for (Player player : players) {
            // Ignore ourself
            if (player.equals(this)) {
                continue;
            }

            packet.putPoint(player.position);
        }

        this.write(packet);
    }

    @Override
    public Hash getID() {
        return id;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setId(Hash id) {
        this.id = id;
    }

    public void write(PacketBuilder packet) {
        session.write(packet);
    }
}














