import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server implements IoHandler, Runnable {

    public static final int DEFAULT_PORT = 36954;

    private boolean isRunning = false;

    protected NioSocketAcceptor acceptor;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private Server() {
        setupServerListener();
    }

    private void setupServerListener() {
        acceptor = new NioSocketAcceptor();
        acceptor.setReuseAddress(true);
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
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to bind to: " + ipAddress.getHostName() + ":" + ipAddress.getPort());
        }
    }

    @Override
    public void run() {
        while (isRunning) {

        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {

    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {

    }
}
