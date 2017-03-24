package network;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Created by tahel on 24/03/17.
 */
public class Message implements Serializable {

    public enum DataType {
        File,
        Text
    }

    private DataType type;
    private String data;

    private String destPseudo;
    private String srcPseudo;

    private InetAddress destAdr;
    private InetAddress srcAdr;

    public Message(DataType type, String data, String destPseudo, String srcPseudo, InetAddress destAdr, InetAddress srcAdr) {
        this.type = type;
        this.data = data;
        this.destPseudo = destPseudo;
        this.srcPseudo = srcPseudo;
        this.destAdr = destAdr;
        this.srcAdr = srcAdr;
    }
}
