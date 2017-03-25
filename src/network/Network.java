package network;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class Network extends Thread {

    private DatagramSocket socketSender;
    private DatagramSocket socketReceiver;

    private int listenNumber = 15530;
    private int cptSockect = 1;

    private HashMap<String, CommunicationSocket> UserToSocket;

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

    public ControlMessage convertDataToControlMessage(byte[] dataReceive) {

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(dataReceive);
            ObjectInputStream is = null;
            is = new ObjectInputStream(in);
            ControlMessage controlMessage1 = (ControlMessage) is.readObject();
            System.out.println("Message reçu = " + controlMessage1.toString());
            return controlMessage1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommunicationSocket getSocket(String username) {
        return UserToSocket.get(username);
    }

    public Network() {
        UserToSocket = new HashMap<>();
        try {
            socketSender = new DatagramSocket();
            /*****************************************
             /* Broadcast à l'arrivé sur le reseaux
             ******************************************/
            // broadcast a vrai
            socketSender.setBroadcast(true);
            // message à envoyer
            // création du packet
            ControlMessage controlMessage = new ControlMessage("xkr0", InetAddress.getLocalHost(), -1, "hello");
            byte[] data = convertObjToData(controlMessage);

            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), listenNumber);

            socketSender.send(packet);
            System.out.println("Packet Hello envoyé");
            socketSender.setBroadcast(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.start();
    }

    public void run() {
        try {

            /******************************************
             * Reception de la réponse
             ******************************************/
            socketReceiver = new DatagramSocket(listenNumber);
            byte[] incomingData = new byte[1024];


            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                // on attend un message
                socketReceiver.receive(incomingPacket);
                byte[] dataReceive = incomingPacket.getData();

                // on reconvertit en ControlMessage
                ControlMessage controlMessage1 = convertDataToControlMessage(dataReceive);

                if (controlMessage1.getData().equals("hello")) {
                    System.out.println("Hello received !");
                    // on envoie le port sur lequel on veut recevoir
                    int newPortForReceive = listenNumber + cptSockect;
                    cptSockect++;
                    ControlMessage controlMessageSocket = new ControlMessage(
                            "xkr0",
                            InetAddress.getLocalHost(),
                            newPortForReceive,
                            "socket_created");


                    byte[] data = convertObjToData(controlMessageSocket);
                    DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);

                    socketSender.send(packet);

                    // on crée une Communication socket pour cet user
                    CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive);
                    UserToSocket.put(controlMessage1.getUserName(), newComSock);

                } else if (controlMessage1.getData().equals("socket_created")) {
                    System.out.println("Socket Created received !");
                    // on récupére le port sur lequel on devra envoyer
                    int portForSend = controlMessage1.getPort();

                    if (UserToSocket.containsKey(controlMessage1.getUserName())) {
                        // si l'utilisateur à déja une socket associé
                        // cas 3
                        // on update le port de destination pour cette communication
                        CommunicationSocket communicationSocket = UserToSocket.get(controlMessage1.getUserName());
                        communicationSocket.setPortSocketDest(controlMessage1.getPort());
                    } else {
                        // si l'utilisateur n'a pas de socket associé
                        int newPortForReceive = listenNumber + cptSockect;
                        cptSockect++;
                        // on crée une socket
                        CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive);
                        UserToSocket.put(controlMessage1.getUserName(), newComSock);

                        // on update le port de dest pour cette socket
                        newComSock.setPortSocketDest(controlMessage1.getPort());

                        // on envoie les informatiosn de la nouvelle socket
                        ControlMessage controlMessageSocket = new ControlMessage(
                                "xkr0",
                                InetAddress.getLocalHost(),
                                newPortForReceive,
                                "socket_created");


                        byte[] data = convertObjToData(controlMessageSocket);
                        DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);

                        socketSender.send(packet);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
