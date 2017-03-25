package network;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;

/**
 * Created by tahel on 28/02/17.
 */
public class CommunicationSocket extends Thread {


    private DatagramSocket socket;

    private int portSocketLocal;

    private int portSocketDest;

    public void setPortSocketDest(int portSocketDest) {
        this.portSocketDest = portSocketDest;
    }

    private InetAddress destip;

    private ArrayList<Message> messages;

    public CommunicationSocket(InetAddress destip, int port) {
        try {
            this.portSocketLocal = port;
            this.destip = destip;
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        messages = new ArrayList<>();
        this.start();
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Socket Lancé ");
                Thread.sleep(10000);
                //lines.add(reader.readLine() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getLastline() {
        try {
            //return lines.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}