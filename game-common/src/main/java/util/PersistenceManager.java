package util;

import codec.Packet;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PersistenceManager {
    public static class PacketHandler {
        public Packet.Type[] types;
        public Class<?> handler;

        public PacketHandler() {
        }
    }

    protected static final XStream xstream;

    public PersistenceManager() {
    }

    static {
        xstream = new XStream(new SunUnsafeReflectionProvider(), new XppDriver());

        xstream.alias("type", Packet.Type.class);

        xstream.alias("PacketHandler", PacketHandler.class);
    }

    public static Object load(URL path) {
        try {
            InputStream in = path.openStream();
            Object o = xstream.fromXML(in);
            in.close();

            return o;
        } catch (IOException ioe) {
            System.exit(1);
            return null;
        }
    }
}
