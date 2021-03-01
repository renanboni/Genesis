package handler;

import codec.Packet;

public interface PacketHandler {
    void handlePacket(Packet packet);
}
