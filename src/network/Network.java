package network;

import controller.Controller;
import model.User;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;

public class Network extends Thread {

    private DatagramSocket socketSender;
    private DatagramSocket socketReceiver;

    private int listenNumber = 15530;
    private int cptSockect = 1;

    private HashMap<String, CommunicationSocket> UserToSocket;

    private User currentUser;

    public CommunicationSocket getSocket(String username) {
        return UserToSocket.get(username);
    }

    public Network() {
        this.currentUser = Controller.getInstance().getCurrentUser();

        UserToSocket = new HashMap<>();
        try {
            socketSender = new DatagramSocket();
            /*****************************************
             /* Broadcast à l'arrivé sur le reseaux
             ******************************************/
            // broadcast a vrai
            socketSender.setBroadcast(true);
            // message à envoyer
            NetworkInterface.getNetworkInterfaces();
            // création du packet
            ControlMessage controlMessage = new ControlMessage(currentUser.getPseudo(), networkUtils.getLocalHostLANAddress(), -1, "hello");
            byte[] data = networkUtils.convertObjToData(controlMessage);

            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), listenNumber);

            socketSender.send(packet);
            System.out.println("Packet Hello envoyé");
            socketSender.setBroadcast(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.start();
    }

    public void sendDisconnect() {
        try {
            // broadcast a vrai
            socketSender.setBroadcast(true);
            ControlMessage controlMessage = new ControlMessage(currentUser.getPseudo(), networkUtils.getLocalHostLANAddress(), -1, "bye");
            byte[] data = networkUtils.convertObjToData(controlMessage);

            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), listenNumber);
            socketSender.send(packet);

            System.out.println("Packet disconnect send !");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                ControlMessage controlMessage1 = networkUtils.convertDataToControlMessage(dataReceive);
                if (controlMessage1.getUserName().equals(currentUser.getPseudo())) {
                    continue;
                }
                if (controlMessage1.getData().equals("hello")) {
                    System.out.println("Hello received !");
                    // on envoie le port sur lequel on veut recevoir
                    int newPortForReceive = listenNumber + cptSockect;
                    cptSockect++;
                    ControlMessage controlMessageSocket = new ControlMessage(
                            currentUser.getPseudo(),
                            InetAddress.getLocalHost(),
                            newPortForReceive,
                            "socket_created");


                    byte[] data = networkUtils.convertObjToData(controlMessageSocket);
                    DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);

                    socketSender.send(packet);

                    // on crée une Communication socket pour cet user
                    CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive, Controller.getInstance());
                    UserToSocket.put(controlMessage1.getUserName(), newComSock);

                    // on préviens aussi le controller qu'un nouvel user et arrivé
                    Controller.getInstance().addUser(controlMessage1.getUserName(), controlMessage1.getUserAdresse());

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
                        CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive, Controller.getInstance());
                        UserToSocket.put(controlMessage1.getUserName(), newComSock);

                        // on update le port de dest pour cette socket
                        newComSock.setPortSocketDest(controlMessage1.getPort());

                        // on envoie les informatiosn de la nouvelle socket
                        ControlMessage controlMessageSocket = new ControlMessage(
                                currentUser.getPseudo(),
                                InetAddress.getLocalHost(),
                                newPortForReceive,
                                "socket_created");


                        byte[] data = networkUtils.convertObjToData(controlMessageSocket);
                        DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);

                        socketSender.send(packet);

                        // on préviens aussi le controller qu'un nouvel user et arrivé
                        Controller.getInstance().addUser(controlMessage1.getUserName(), controlMessage1.getUserAdresse());
                    }
                } else if(controlMessage1.getData().equals("bye")) {
                    System.out.println("Ok byeeeeeee.....");
                    Controller.getInstance().setUserOffLine(controlMessage1.getUserName());
                    //Controller.getInstance().sendToUser(controlMessage1.getUserName(), "Disconected");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
