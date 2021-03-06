package handlers;

import codec.Packet;
import controller.PlayerController;
import core.Position;
import entity.Player;
import main.Client;

public class PlayerHandler implements PacketHandler {
    @Override
    public void handlePacket(Packet packet, Client client) {
        if (packet.getType() == Packet.Type.PLAYERS_ADD_RESPONSE) {
            int playerCount = packet.getShort();

            for (int i = 0; i < playerCount; i++) {
                Player player = new Player(new PlayerController(client.getGame().getInput()));

                int x = packet.getShort();
                int y = packet.getShort();

                player.setPosition(new Position(x, y));

                client.addPlayer(player);
            }
        }
    }
}
