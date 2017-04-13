package network;

import java.io.Serializable;

/**
 * Basic message send on the network,
 * implements serializable to be sent on the network as byte[]
 * and convert into message again when received
 */
public class Message implements Serializable {
    // All the possible data we can send
    public enum DataType {
        File,
        Text
    }
    // type of data inside the message
    private DataType type;
    // The data
    private String data;
    // the receiver pseudo
    private String destPseudo;
    // the sender pseudo
    private String srcPseudo;

    /**
     * Basic constructor, setup all the data members with the given parameters
     * @param type of the data in the message
     * @param data the data
     * @param destPseudo the dest pseudo
     * @param srcPseudo the sender pseudo
     */
    public Message(DataType type, String data, String destPseudo, String srcPseudo) {
        this.type = type;
        this.data = data;
        this.destPseudo = destPseudo;
        this.srcPseudo = srcPseudo;
    }

    /**
     * Getter for the type of the data
     * @return DataType
     */
    public DataType getType() {
        return type;
    }

    /**
     * Getter for the Data
     * @return String
     */
    public String getData() {
        return data;
    }

    /**
     * Getter for the dest of the message
     * @return String
     */
    public String getDestPseudo() {
        return destPseudo;
    }

    /**
     * Getter for the sender pseudo
     * @return String
     */
    public String getSrcPseudo() {
        return srcPseudo;
    }

    /**
     * Convert a message into a basic string
     * @return String
     */
    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", data='" + data + '\'' +
                ", destPseudo='" + destPseudo + '\'' +
                ", srcPseudo='" + srcPseudo + '\'' +
                '}';
    }
}
