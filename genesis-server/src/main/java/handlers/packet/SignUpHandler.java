package handlers.packet;

import codec.Packet;
import codec.PacketBuilder;
import db.models.DBPlayer;
import handlers.PacketHandler;
import main.Server;
import main.WorldManager;
import model.Player;
import org.apache.mina.core.session.IoSession;

import java.util.Random;

public class SignUpHandler implements PacketHandler {
    @Override
    public void handlePacket(Packet packet, Server server, WorldManager worldManager, Player player) {
        IoSession session = packet.getSession();
        PacketBuilder response = new PacketBuilder(Packet.Type.SIGN_UP_RESPONSE);

        try {
            String username = packet.getString();
            String password = packet.getString();

            Random random = new Random();

            DBPlayer dbPlayer = new DBPlayer();
            dbPlayer.setUsername(username);
            dbPlayer.setPassword(password);
            dbPlayer.setX(random.nextInt(400));
            dbPlayer.setY(random.nextInt(400));

            server.getDatabase().savePlayer(dbPlayer);

            // PAYLOAD >> || true ||
            response.putBoolean(true);
        } catch (Exception e) {
            // PAYLOAD >> || false ||
            e.printStackTrace();
            response.putBoolean(false);
        } finally {
            session.write(response);
        }
    }
}
