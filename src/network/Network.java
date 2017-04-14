package network;

import controller.Controller;
import model.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;

/**
 * The network, it control everything in link with controlMessage, and socket creation
 * each time we have a new user we create a new socket to communicate with him
 */
public class Network extends Thread {
    // 1 socket to send data
    private DatagramSocket socketSender;
    // 1 socket to receive data
    private DatagramSocket socketReceiver;
    // the main port of the app
    private int listenNumber = 15530;
    // the cpt to not create a socket on the same port each time we have a new user
    private int cptSockect = 1;
    // For an username we have one communication Sokect
    private HashMap<String, CommunicationSocket> UserToSocket;

    /**
     * Constructor of the network object, and send a "hello" control message in broadcast
     */
    public Network() {
        try {
            UserToSocket = new HashMap<>();

            socketSender = new DatagramSocket();
            /*****************************************
             /* Broadcast à l'arrivé sur le reseaux
             ******************************************/
            // broadcast a vrai
            socketSender.setBroadcast(true);
            // message à envoyer
            NetworkInterface.getNetworkInterfaces();
            // création du packet
            ControlMessage controlMessage = new ControlMessage(Controller.getInstance().getCurrentUser().getPseudo(),
                    networkUtils.getLocalHostLANAddress(), -1, "hello");
            byte[] data = networkUtils.convertObjToData(controlMessage);

            DatagramPacket packet = new DatagramPacket(data, data.length,
                    InetAddress.getByName("255.255.255.255"), listenNumber);

            socketSender.send(packet);
            System.out.println("Packet Hello envoyé");
            socketSender.setBroadcast(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.start();
    }

    /**
     * Get the communicationSocket corresponding to the username
     * @param username the username of the user
     * @return the communicationSocket
     */
    public CommunicationSocket getSocket(String username) {
        return UserToSocket.get(username);
    }

    /**
     * Send a disconnect message to all the user on the network
     */
    public void sendDisconnect() {
        try {
            // broadcast a vrai
            socketSender.setBroadcast(true);
            ControlMessage controlMessage = new ControlMessage(Controller.getInstance().getCurrentUser().getPseudo(),
                    networkUtils.getLocalHostLANAddress(), -1, "bye");
            byte[] data = networkUtils.convertObjToData(controlMessage);

            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), listenNumber);
            socketSender.send(packet);

            System.out.println("Packet disconnect send !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run a thread wo collect all the incoming message and do the associate action
     */
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
                if (controlMessage1.getUserName().equals(Controller.getInstance().getCurrentUser().getPseudo())) {
                    continue;
                }
                if (controlMessage1.getData().equals("hello")) {
                    System.out.println("Hello received !");
                    // on envoie le port sur lequel on veut recevoir
                    int newPortForReceive = listenNumber + cptSockect;
                    cptSockect++;
                    ControlMessage controlMessageSocket = new ControlMessage(
                            Controller.getInstance().getCurrentUser().getPseudo(),
                            networkUtils.getLocalHostLANAddress(),
                            newPortForReceive,
                            "socket_created");

                    // send the message
                    byte[] data = networkUtils.convertObjToData(controlMessageSocket);
                    DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);
                    socketSender.send(packet);

                    // on crée une Communication socket pour cet user, en attente d'une connection (type 1)
                    CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive, 1);
                    // on supprime l'ancienne socket si il y a lieu
                    UserToSocket.remove(controlMessage1.getUserName());
                    UserToSocket.put(controlMessage1.getUserName(), newComSock);

                    // on préviens aussi le controller qu'un nouvel user et arrivé
                    Controller.getInstance().addUser(controlMessage1.getUserName(), controlMessage1.getUserAdresse());

                } else if (controlMessage1.getData().equals("socket_created")) {
                    System.out.println("Socket Created received !");
                    if (UserToSocket.containsKey(controlMessage1.getUserName())) {
                        // si l'utilisateur à déja une socket associé
                        // on update le port de destination pour cette communication
                        //UserToSocket.get(controlMessage1.getUserName()).setPortSocketDest(controlMessage1.getPort());
                    } else {
                        // si l'utilisateur n'a pas de socket associé
                        int newPortForReceive = listenNumber + cptSockect;
                        cptSockect++;
                        // on crée une socket
                        CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), controlMessage1.getPort(), 2);
                        // on supprime l'ancienne socket si il y a lieu
                        UserToSocket.remove(controlMessage1.getUserName());
                        UserToSocket.put(controlMessage1.getUserName(), newComSock);

                        // on update le port de dest pour cette socket
                        //newComSock.setPortSocketDest(controlMessage1.getPort());

                        // on envoie les informatiosn de la nouvelle socket
                        ControlMessage controlMessageSocket = new ControlMessage(
                                Controller.getInstance().getCurrentUser().getPseudo(),
                                networkUtils.getLocalHostLANAddress(),
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
                    UserToSocket.get(controlMessage1.getUserName()).closeSocket();
                    UserToSocket.remove(controlMessage1.getUserName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
