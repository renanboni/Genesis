package handlers;

import codec.Packet;

public interface PacketHandler {
    void handlePacket(Packet packet);
}
