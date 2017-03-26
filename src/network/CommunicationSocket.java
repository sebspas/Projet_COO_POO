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
                Message message = convertDataToMessage(dataReceive);
                //System.out.println("message reçue :" + message);
                controller.deliverMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Message convertDataToMessage(byte[] dataReceive) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(dataReceive);
            ObjectInputStream is = null;
            is = new ObjectInputStream(in);
            Message msg = (Message) is.readObject();
            return msg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] convertObjToData(Object obj) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = null;
            os = new ObjectOutputStream(outputStream);
            os.writeObject(obj);
            byte[] data = outputStream.toByteArray();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMsg(Message msg) {
        byte[] data = convertObjToData(msg);
        DatagramPacket packet = new DatagramPacket(data, data.length, destip, portSocketDest);
        try {
            socket.send(packet);
            System.out.println("Message envoyé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}