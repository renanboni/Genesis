package network;

import codec.Packet;
import codec.PacketCodecFactory;
import handlers.PacketHandler;
import main.Client;
import codec.PacketBuilder;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import util.PersistenceManager;

import java.net.InetSocketAddress;
import java.net.URL;
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
        Map<Packet.Type, PacketHandler> handlers = new HashMap<Packet.Type, PacketHandler>();
        URL path = PacketHandler.class.getResource("packethandlers.xml");
        if (path == null) {
            throw new RuntimeException("Unable to find packethandlers.xml resource");
        }

        PersistenceManager.PacketHandler[] definitions = (PersistenceManager.PacketHandler[]) PersistenceManager.load(path);
        for (PersistenceManager.PacketHandler definition : definitions) {
            try {
                PacketHandler handler = (PacketHandler) definition.handler.newInstance();
                for (Packet.Type type : definition.types)
                    handlers.put(type, handler);
            }
            catch (Exception e) {
                throw new RuntimeException("Error loading packet handlers: " + e.getMessage());
            }
        }

        return handlers;
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
            for (Packet message : packets) {
                this.processPacket(message);
            }

            packets.clear();
        }
    }

    private boolean processPacket(Packet message) {
        if (session == null) {
            return true;
        }

        PacketHandler handler = packetHandlers.get(message.getType());

        if (handler == null) {
            session.close(true);
            return false;
        }

        try {
            handler.handlePacket(message, client);
            return true;
        }
        // Something went wrong (malformed packet?), close the session (forcefully)
        catch (Exception e) {
            e.printStackTrace();
            session.close(true);
            return false;
        }
    }

    public void write(PacketBuilder packet) {
        session.write(packet);
    }

    @Override
    public void sessionCreated(IoSession session) {

    }

    @Override
    public void sessionOpened(IoSession session) {

    }

    @Override
    public void sessionClosed(IoSession session) {
        System.out.println("sessionClosed ID " +session.getId());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        System.out.println("sessionIdle, shutting it down");
        session.close(false);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("Error from server connection: " + cause.getMessage());
        cause.printStackTrace();

        // Close the session (forcefully)
        session.close(true);
    }

    @Override
    public void messageReceived(IoSession session, Object object) {
        Packet message = (Packet) object;
        System.out.println("RECEIVED << " + message);

        synchronized (session) {
            if (session.containsAttribute("pending")) {
                synchronized (packets) {
                    packets.add(message);
                }
            } else if (message.getType() == Packet.Type.LOGIN_RESPONSE || message.getType() == Packet.Type.SIGN_UP_RESPONSE) {
                // Mark this session as pending login
                session.setAttribute("pending");

                // Queue the packet
                synchronized (packets) {
                    packets.add(message);
                }
            } else {
                session.close(true);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) {
        Packet packet = (Packet) message;
        System.out.println("SENT >> " + packet);
    }
}
