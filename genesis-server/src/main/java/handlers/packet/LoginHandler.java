package handlers.packet;

import db.models.DBPlayer;
import handlers.PacketHandler;
import codec.Packet;
import codec.PacketBuilder;
import main.Server;
import main.WorldManager;
import model.Player;
import model.Position;
import org.apache.mina.core.session.IoSession;

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
            session.setAttribute("client", player);
            session.removeAttribute("pending");

            worldManager.addPlayer(player);
        }
    }

    private Player handleLoginRequest(Server server, WorldManager worldManager, Packet packet) {
        IoSession session = packet.getSession();
        PacketBuilder response = new PacketBuilder(Packet.Type.LOGIN_RESPONSE);

        try {
            String username = packet.getString();
            String pass = packet.getString();

            DBPlayer dbPlayer = server.getDatabase().getPlayer(username, pass);

            /*
              if we don't find any player, it means the username and/or password is wrong
              the payload will look like this

              PAYLOAD >> || false || Invalid username and/or password. ||
             */
            if (dbPlayer == null) {
                response.putBoolean(false);
                response.putString("Invalid username and/or password.");
                return null;
            }

            Player player = new Player(session, worldManager, server);
            player.setId(dbPlayer.getId());
            player.setPosition(new Position(dbPlayer.getX(), dbPlayer.getY()));

            response.putBoolean(true);
            response.putPoint(new Position(dbPlayer.getX(), dbPlayer.getY()));

            /*
                if we do find a player, the payload will look like this:
                PAYLOAD >> || true || x || y ||
             */
            return player;
        } catch (Exception e) {
            // PAYLOAD >> || false || Invalid username and/or password. ||
            response.putBoolean(false);
            response.putString(e.getMessage());

            e.printStackTrace();

            return null;
        } finally {
            session.write(response);
        }
    }
}
