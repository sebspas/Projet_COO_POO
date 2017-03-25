package network;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by FatePc on 3/25/2017.
 */
public class ControlMessage implements Serializable {

    private String userName;

    private InetAddress userAdresse;

    private int port;

    private String data;

    public ControlMessage(String userName, InetAddress userAdresse, int port, String data) {
        this.userName = userName;
        this.userAdresse = userAdresse;
        this.port = port;
        this.data = data;
    }

    public String getUserName() {
        return userName;
    }

    public InetAddress getUserAdresse() {
        return userAdresse;
    }

    public int getPort() {
        return port;
    }

    public String getData() {
        return data;
    }

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
