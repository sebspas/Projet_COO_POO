package network;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Control message send on the network,
 * implements serializable to be sent on the network as byte[]
 * and convert into message again when received
 * they are used to send different messages as "hello","socket_created","bye"
 */
public class ControlMessage implements Serializable {
    // the name of the sender of this controlMessage
    private String userName;
    // the Ip adress of the dest
    private InetAddress userAdresse;
    // the port to setup the connection
    private int port;
    // the data : "hello","socket_created","bye"
    private String data;

    /**
     *  Constructor of the control message, with the given parameters
     * @param userName the name fo the send
     * @param userAdresse the ip adress
     * @param port the port decided for the socket
     * @param data the data : "hello","socket_created","bye"
     */
    public ControlMessage(String userName, InetAddress userAdresse, int port, String data) {
        this.userName = userName;
        this.userAdresse = userAdresse;
        this.port = port;
        this.data = data;
    }

    /**
     * Getter of the username of the sender
     * @return String username
     */
    public String getUserName() {
        return userName;
    }

    /**
     *  Getter of the userAdress
     * @return InetAdress
     */
    public InetAddress getUserAdresse() {
        return userAdresse;
    }

    /**
     *  Getter of the port
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Getter for the Data
     * @return String
     */
    public String getData() {
        return data;
    }

    /**
     * Convert a ControlMessage into a basic string
     * @return String
     */
    @Override
    public String toString() {
        return "ControlMessage{" +
                "userName='" + userName + '\'' +
                ", userAdresse=" + userAdresse +
                ", port=" + port +
                ", data='" + data + '\'' +
                '}';
    }
}
