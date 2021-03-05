package handlers;

import codec.Packet;
import controller.PlayerController;
import core.Position;
import entity.Player;
import main.Client;

public class LoginHandler implements PacketHandler {
    @Override
    public void handlePacket(Packet packet, Client client) {
        try {
            boolean success = packet.getBoolean();

            if (success) {
                int x = packet.getShort();
                int y = packet.getShort();

                Player player = new Player(new PlayerController(client.getGame().getInput()));
                player.setPosition(new Position(x, y));

                client.loginSuccess(player);
            } else {
                String msg = packet.getString();
                client.loginError(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
