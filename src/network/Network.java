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

    private Controller controller;

    private User currentUser;

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

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // Iterate all IP addresses assigned to each card...
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress() && !inetAddr.toString().equals("127.0.0.1")) {

                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it immediately...
                            return inetAddr;
                        }
                        else if (candidateAddress == null) {
                            // Found non-loopback address, but not necessarily site-local.
                            // Store it as a candidate to be returned if site-local address is not subsequently found...
                            candidateAddress = inetAddr;
                            // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                            // only the first. For subsequent iterations, candidate will be non-null.
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its NIC (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress;
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost() returns...
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    public Network(Controller controller) {
        this.controller = controller;
        this.currentUser = controller.getCurrentUser();

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
            ControlMessage controlMessage = new ControlMessage(currentUser.getPseudo(), getLocalHostLANAddress(), -1, "hello");
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


                    byte[] data = convertObjToData(controlMessageSocket);
                    DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);

                    socketSender.send(packet);

                    // on crée une Communication socket pour cet user
                    CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive, controller);
                    UserToSocket.put(controlMessage1.getUserName(), newComSock);

                    // on préviens aussi le controller qu'un nouvel user et arrivé
                    controller.addUser(controlMessage1.getUserName(), controlMessage1.getUserAdresse());

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
                        CommunicationSocket newComSock = new CommunicationSocket(controlMessage1.getUserAdresse(), newPortForReceive, controller);
                        UserToSocket.put(controlMessage1.getUserName(), newComSock);

                        // on update le port de dest pour cette socket
                        newComSock.setPortSocketDest(controlMessage1.getPort());

                        // on envoie les informatiosn de la nouvelle socket
                        ControlMessage controlMessageSocket = new ControlMessage(
                                currentUser.getPseudo(),
                                InetAddress.getLocalHost(),
                                newPortForReceive,
                                "socket_created");


                        byte[] data = convertObjToData(controlMessageSocket);
                        DatagramPacket packet = new DatagramPacket(data, data.length, controlMessage1.getUserAdresse(), listenNumber);

                        socketSender.send(packet);

                        // on préviens aussi le controller qu'un nouvel user et arrivé
                        controller.addUser(controlMessage1.getUserName(), controlMessage1.getUserAdresse());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
