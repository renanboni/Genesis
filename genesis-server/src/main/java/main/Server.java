package main;

import codec.PacketCodecFactory;
import db.Database;
import handlers.PacketHandler;
import codec.Packet;
import model.Player;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import util.PersistenceManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Server implements IoHandler, Runnable {

    public static final int DEFAULT_PORT = 36954;
    public static final int LOOP_DELAY = 100;

    private static final String DATABASE_CONFIG_FILE = "database.conf.xml";

    private boolean isRunning = false;

    protected NioSocketAcceptor acceptor;
    protected final Queue<Packet> packets;
    protected final Map<Packet.Type, PacketHandler> packetHandlers;

    private WorldManager worldManager;

    private Database db;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private Server() {
        connectToDatabase();

        packets = new LinkedList<>();
        packetHandlers = loadPacketHandlers();
        worldManager = new WorldManager(this);
        setupServerListener();
    }

    private void connectToDatabase() {
        File dbConfig = new File(DATABASE_CONFIG_FILE);
        if (!dbConfig.exists()) {
            throw new RuntimeException("Unable to load database config file: " + dbConfig.getAbsolutePath());
        }

        db = new Database(dbConfig, this);
    }

    private Map<Packet.Type, PacketHandler> loadPacketHandlers() {
        Map<Packet.Type, PacketHandler> handlers = new HashMap<>();

        URL path = PacketHandler.class.getResource("packethandlers.xml");
        if (path == null) {
            throw new RuntimeException("Unable to find packethandlers.xml resource");
        }

        PersistenceManager.PacketHandler[] definitions = (PersistenceManager.PacketHandler[]) PersistenceManager.load(path);

        for (PersistenceManager.PacketHandler definition : definitions) {
            try {
                PacketHandler handler = (PacketHandler) definition.handler.newInstance();
                for (Packet.Type type : definition.types) {
                    handlers.put(type, handler);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error loading packet handlers: " + e.getMessage());
            }
        }

        return handlers;
    }

    private void setupServerListener() {
        acceptor = new NioSocketAcceptor();
        acceptor.setReuseAddress(true);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PacketCodecFactory()));
        acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 100);
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
            System.out.println("main.Server listening on: " + ipAddress.getHostName() + ":" + ipAddress.getPort());
        } catch (IOException e) {
            throw new RuntimeException("Unable to bind to: " + ipAddress.getHostName() + ":" + ipAddress.getPort());
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            long start = System.currentTimeMillis();
            this.update(start);

            int duration = (int) (System.currentTimeMillis() - start);
            if (duration < LOOP_DELAY) {
                try { Thread.sleep(LOOP_DELAY - duration); } catch (InterruptedException e) { }
            }
        }
    }

    private void update(long now) {
        synchronized (packets) {
            for (Packet message : packets) {
                processPacket(message);
            }

            packets.clear();
        }

        worldManager.update(now);
    }

    private boolean processPacket(Packet message) {
        IoSession session = message.getSession();

        // Confirm the client is still connected and valid
        if (!session.isConnected() || (!session.containsAttribute("client") && !session.containsAttribute("pending"))) {
            return false;
        }

        Player client = (Player) session.getAttribute("client");

        PacketHandler handler = packetHandlers.get(message.getType());

        // If there's no handler then close the session (forcefully)
        if (handler == null) {
            session.close(true);
            return false;
        }

        try {
            handler.handlePacket(message, this, worldManager, client);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.close(true);
            return false;
        }
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
        System.out.println("Session idle, shutting it down.");
        session.close(false);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        System.out.println("Exception caught: " + cause.getLocalizedMessage());
        session.close(true);
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
            // If there isn't a client attached then this must be the login or sign up request
            else if (message.getType() == Packet.Type.LOGIN_SEND || message.getType() == Packet.Type.SIGN_UP_SEND) {
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

    public Database getDatabase() {
        return db;
    }
}
