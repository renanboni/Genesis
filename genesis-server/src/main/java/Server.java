import codec.PacketCodecFactory;
import handlers.PacketHandler;
import handlers.packet.LoginHandler;
import codec.Packet;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Server implements IoHandler, Runnable {

    public static final int DEFAULT_PORT = 36954;

    private boolean isRunning = false;

    protected NioSocketAcceptor acceptor;
    protected final Queue<Packet> packets;
    protected final Map<Packet.Type, PacketHandler> packetHandlers;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private Server() {
        packets = new LinkedList<>();
        packetHandlers = loadPacketHandlers();
        setupServerListener();
    }

    private Map<Packet.Type, PacketHandler> loadPacketHandlers() {
        Map<Packet.Type, PacketHandler> handlers = new HashMap<>();
        handlers.put(Packet.Type.LOGIN_SEND, new LoginHandler());

        return handlers;
    }

    private void setupServerListener() {
        acceptor = new NioSocketAcceptor();
        acceptor.setReuseAddress(true);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PacketCodecFactory()));
        acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 5);
        acceptor.setHandler(this);
    }

    private void start() {
        if (isRunning) {
            return;
        }

        InetSocketAddress ipAddress = new InetSocketAddress(DEFAULT_PORT);

        isRunning = true;
        new Thread(this).start();

        try {
            acceptor.bind(ipAddress);
            System.out.println("Server listening on: " + ipAddress.getHostName() + ":" + ipAddress.getPort());
        } catch (IOException e) {
            throw new RuntimeException("Unable to bind to: " + ipAddress.getHostName() + ":" + ipAddress.getPort());
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            update();
        }
    }

    private void update() {
        synchronized (packets) {
            for (Packet message : packets) {
                processPacket(message);
            }

            packets.clear();
        }
    }

    private boolean processPacket(Packet message) {
        IoSession session = message.getSession();

        if (!session.isConnected() || (!session.containsAttribute("client") && !session.containsAttribute("pending"))) {
            return false;
        }

        PacketHandler handler = packetHandlers.get(message.getType());
        handler.handlePacket(message);
        return true;
    }

    @Override
    public void sessionCreated(IoSession session) {
        System.out.println("New connection created || ID " + session.getId());
    }

    @Override
    public void sessionOpened(IoSession session) {
        System.out.println("New connection opened || ID " + session.getId());
    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("Session closed");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("Session idle");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("Exception caught: " + cause.getLocalizedMessage());
    }

    @Override
    public void messageReceived(IoSession session, Object o) {
        Packet message = (Packet) o;

        System.out.println("Message received: " + message.toString());

        synchronized (session) {
            if (session.containsAttribute("client") || session.containsAttribute("pending")) {
                synchronized (packets) {
                    packets.add(message);
                }
            }
            // If there isn't a client attached then this must be the login request
            else if (message.getType() == Packet.Type.LOGIN_SEND) {
                // Decrypt the login request

                // Mark this session as pending login
                session.setAttribute("pending");

                // Queue the packet
                synchronized (packets) {
                    packets.add(message);
                }
            }
            // Otherwise this packet shouldn't be here!
            else {
                session.close(true);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) {

    }
}
