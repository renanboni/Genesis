package network;

import codec.Packet;
import codec.PacketCodecFactory;
import handler.LoginHandler;
import handler.PacketHandler;
import main.Client;
import codec.PacketBuilder;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Connection implements IoHandler {

    private final Client client;
    private final NioSocketConnector connector;
    private IoSession session;
    protected final Map<Packet.Type, PacketHandler> packetHandlers;
    protected final Queue<Packet> packets = new LinkedList<>();

    public Connection(Client client) {
        this.client = client;

        packetHandlers = loadPacketHandlers();

        connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PacketCodecFactory()));
        connector.setHandler(this);
    }

    private Map<Packet.Type, PacketHandler> loadPacketHandlers() {
        Map<Packet.Type, PacketHandler> packets = new HashMap<>();
        packets.put(Packet.Type.LOGIN_RESPONSE, new LoginHandler());
        return packets;
    }

    public void open(String hostname, int port) throws RuntimeIoException {
        if (session != null) {
            return;
        }

        InetSocketAddress address = new InetSocketAddress(hostname, port);

        ConnectFuture future = connector.connect(address);
        future.awaitUninterruptibly();
        session = future.getSession();

        System.out.println("Connected to server: " + address.getHostName() + ":" + address.getPort());
    }

    public void update() {
        synchronized (packets) {
            for (Packet message : packets)
                this.processPacket(message);

            packets.clear();
        }
    }

    private boolean processPacket(Packet message) {
        if (session == null)
            return true;

        PacketHandler handler = packetHandlers.get(message.getType());

        if (message.getType() == Packet.Type.LOGIN_RESPONSE) {
            client.loginSuccess();
        }
        // If there's no handler then close the session (forcefully)
        return true;
    }

    public void write(PacketBuilder packet) {
        session.write(packet);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {

    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("sessionClosed");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("sessionIdle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("Exception");
    }

    @Override
    public void messageReceived(IoSession session, Object object) throws Exception {
        System.out.println("messageReceived");
        Packet message = (Packet) object;

        synchronized (session) {
            if (message.getType() == Packet.Type.LOGIN_RESPONSE) {
                packets.add(message);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("Message sent!");
        Packet packet = (Packet) message;
        System.out.println(packet);
    }
}
