package network;

import controller.Controller;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Communication socket wo is listening on the port and who send the message to the dest,
 * this is setup on a local port and a port to send at
 */
public class CommunicationSocket extends Thread {
    // The socket used to send with and receive on
    private DatagramSocket socket;
    // just a save of the port where the socket is
    private int portSocketLocal;
    // the port to send at
    private int portSocketDest;
    // the ip of the dest
    private InetAddress destip;

    /**
     * Basic constructor, just create the socket
     * @param destip the ip adress of the dest
     * @param port the port where to create the socket
     */
    public CommunicationSocket(InetAddress destip, int port) {
        try {
            this.portSocketLocal = port;
            this.destip = destip;
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("Socket crée sur le port : " + port);
        this.start();
    }

    /**
     * The run() method, wo is listening and getting the incoming message
     */
    public void run() {
        while (true) {
            try {
                byte[] incomingData = new byte[1024];

                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                // on attend un message
                socket.receive(incomingPacket);
                byte[] dataReceive = incomingPacket.getData();

                // on reconvertit en ControlMessage
                Message message = networkUtils.convertDataToMessage(dataReceive);
                //System.out.println("message reçue :" + message);
                Controller.getInstance().deliverMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send the given message to the dest socket
     * @param msg the msg to send
     */
    public void sendMsg(Message msg) {
        byte[] data = networkUtils.convertObjToData(msg);
        DatagramPacket packet = new DatagramPacket(data, data.length, destip, portSocketDest);
        try {
            socket.send(packet);
            System.out.println("Message envoyé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the dest port, because it's not know when we create the socket
     * @param portSocketDest the dest port
     */
    public void setPortSocketDest(int portSocketDest) {
        this.portSocketDest = portSocketDest;
    }

}