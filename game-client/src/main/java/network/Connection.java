package network;

import main.Client;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class Connection implements IoHandler {

    private final Client client;
    private final NioSocketConnector connector;
    private IoSession session;

    public Connection(Client client) {
        this.client = client;

        this.connector = new NioSocketConnector();
        this.connector.setHandler(this);
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
