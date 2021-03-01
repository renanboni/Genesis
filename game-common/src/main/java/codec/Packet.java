package codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

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
        LOGIN_SEND, LOGIN_RESPONSE
    }

    public int size() {
        return payload.limit();
    }
    public IoSession getSession() { return session; }
    public Type getType() {
        return type;
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
