package handlers.packet;

import handlers.PacketHandler;
import codec.Packet;
import codec.PacketBuilder;
import main.Server;
import main.WorldManager;
import model.Player;
import model.Position;
import org.apache.mina.core.session.IoSession;

import java.util.Random;

public class LoginHandler implements PacketHandler {

    /**
     * @param packet       Login packet sent from the client
     * @param server       Current server
     * @param worldManager World manager
     * @param player       if login successfully, it should contains the player
     *                     <p>
     *                     Client will send a LOGIN_REQUEST packet, we should try to fetch it from
     *                     our database, if we can get it, let's add to the map and send a
     *                     PLAYERS_ADD_RESPONSE so the client can render it.
     */
    @Override
    public void handlePacket(Packet packet, Server server, WorldManager worldManager, Player player) {
        IoSession session = packet.getSession();

        player = handleLoginRequest(server, worldManager, packet);

        if (player == null) {
            session.close(false);
        } else {
            worldManager.addPlayer(player);
        }
    }

    private Player handleLoginRequest(Server server, WorldManager worldManager, Packet packet) {
        IoSession session = packet.getSession();
        PacketBuilder response = new PacketBuilder(Packet.Type.LOGIN_RESPONSE);

        session.write(response);

        Player player = new Player(session, worldManager, server);
        player.setPosition(new Position(new Random().nextInt(400), new Random().nextInt(400)));

        return player;
    }
}
