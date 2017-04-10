package network;

import controller.Controller;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

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

    private Controller controller;

    public CommunicationSocket(InetAddress destip, int port, Controller controller) {
        try {
            this.portSocketLocal = port;
            this.destip = destip;
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        messages = new ArrayList<>();
        System.out.println("Socket crée sur le port : " + port);
        this.start();
    }

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
                controller.deliverMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

}