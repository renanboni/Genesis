package codec;

import org.apache.mina.core.buffer.IoBuffer;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class PacketBuilder extends Packet {

    public static final int DEFAULT_CAPACITY = 100;

    protected static final CharsetEncoder stringEncoder;

    static {
        stringEncoder = StandardCharsets.UTF_8.newEncoder();
    }

    public PacketBuilder(Type type) {
        super(type, IoBuffer.allocate(DEFAULT_CAPACITY).setAutoExpand(true), null);
    }

    public void putString(String s) {
        try {
            payload.putString(s + '\0', stringEncoder);
        } catch (CharacterCodingException e) {
            payload.put((byte) 0); // Put a null byte to terminate the non-existant string
        }
    }

    public IoBuffer getPayload() {
        return payload.getSlice(0, this.size()).asReadOnlyBuffer().rewind();
    }
}
