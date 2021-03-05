package codec;

import model.Hash;
import model.Position;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import java.awt.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class Packet {

    protected final Type type;
    protected IoBuffer payload;
    protected final IoSession session;

    public static final int HEADER_SIZE = 4 + 4;

    protected static final CharsetDecoder stringDecoder;

    static {
        stringDecoder = StandardCharsets.UTF_8.newDecoder();
    }

    public Packet(Type type, IoBuffer payload, IoSession session) {
        this.type = type;
        this.payload = payload;
        this.session = session;
    }

    public enum Type {
        LOGIN_SEND, LOGIN_RESPONSE, PLAYERS_ADD_RESPONSE, SIGN_UP_SEND, SIGN_UP_RESPONSE
    }

    public int size() {
        return payload.limit();
    }
    public IoSession getSession() { return session; }
    public Type getType() {
        return type;
    }
    public void putHash(Hash hash) {
        payload.put(hash.getBytes(), 0, Hash.LENGTH);
    }
    public Hash getHash() {
        byte[] data = new byte[Hash.LENGTH];
        payload.get(data, 0, Hash.LENGTH);

        return Hash.fromBytes(data);
    }
    public void putShort(short s) {
        payload.putShort(s);
    }

    public void putPoint(Position p) {
        this.putShort((short) p.x);
        this.putShort((short) p.y);
    }

    public boolean getBoolean() {
        return payload.get() == 1;
    }

    public Point getPoint() {
        int x = this.getShort();
        int y = this.getShort();

        return new Point(x, y);
    }

    public short getShort() {
        return payload.getShort();
    }

    public String getString() {
        try {
            return payload.getString(stringDecoder);
        }
        catch (CharacterCodingException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "packet[" + type + "].length = " + this.size();
    }
}
