package handlers.packet;

import handlers.PacketHandler;
import codec.Packet;
import codec.PacketBuilder;
import org.apache.mina.core.session.IoSession;

public class LoginHandler implements PacketHandler  {
    @Override
    public void handlePacket(Packet packet) {
        IoSession session = packet.getSession();
        PacketBuilder response = new PacketBuilder(Packet.Type.LOGIN_RESPONSE);

        session.write(response);
    }
}
