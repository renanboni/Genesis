package handlers;

import codec.Packet;
import main.Client;

public interface PacketHandler {
    void handlePacket(Packet packet, Client client);
}
