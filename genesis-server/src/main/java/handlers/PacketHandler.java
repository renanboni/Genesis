package handlers;

import codec.Packet;
import main.Server;
import main.WorldManager;
import model.Player;

public interface PacketHandler {
    void handlePacket(Packet packet, Server server, WorldManager worldManager, Player player);
}
