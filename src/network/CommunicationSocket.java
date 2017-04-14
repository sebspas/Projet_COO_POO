package network;

import com.sun.org.apache.xpath.internal.SourceTree;
import controller.Controller;

import java.io.*;
import java.lang.reflect.Executable;
import java.net.*;
import java.util.ArrayList;

/**
 * Communication socket wo is listening on the port and who send the message to the dest,
 * this is setup on a local port and a port to send at
 */
public class CommunicationSocket extends Thread {

    protected volatile boolean running = true;
    // The socket used to send with and receive on
    //private DatagramSocket socket;
    private ServerSocket socketServer;
    private Socket socketClient;

    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    // just a save of the port where the socket is
    private int portSocketLocal;
    // the port to send at
    private int portSocketDest;
    // the ip of the dest
    private InetAddress destip;

    private int type = 0;

    /**
     * Basic constructor, just create the socket
     * @param destip the ip adress of the dest
     * @param port the port where to create the socket
     * @param type the type of socket : 1 => waiting accept (serverSocket), 2=> client connect(socket)
     */
    public CommunicationSocket(InetAddress destip, int port, int type) {
        try {
            this.type =  type;
            this.destip = destip;

            if (this.type == 1) {
                this.socketServer = new ServerSocket(port);
                this.portSocketLocal = port;
            } else {
                this.socketClient = new Socket(destip, port);
                writer = new ObjectOutputStream(socketClient.getOutputStream());
                reader = new ObjectInputStream(socketClient.getInputStream());
                this.portSocketDest = port;
                System.out.println("Socket connected");
            }
            //this.socket = new DatagramSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Socket crée sur le port : " + port);
        this.start();
    }

    /**
     * The run() method, wo is listening and getting the incoming message
     */
    public void run() {
        try {
            if (type == 1 && running) {
                System.out.println("Socket en attente");
                socketClient = socketServer.accept();
                writer = new ObjectOutputStream(socketClient.getOutputStream());
                reader = new ObjectInputStream(socketClient.getInputStream());
                System.out.println("Connexion établie !!!");
            }

            while (running) {
                Message receveid = (Message)reader.readObject();
                if(receveid.getType() == Message.DataType.File) {
                    System.out.println("Starting File reception ....");
                    System.out.println("Format of the file : " + receveid.getData());
                    // we gotta get a file so we do the following process
                    // we create an empty file with the right format
                    OutputStream receivedFile = new FileOutputStream(System.getProperty("user.dir") + "myFile." + receveid.getData());
                    // temporary inputStream for the trasnfert
                    InputStream in = socketClient.getInputStream();

                    byte[] bytes = new byte[16*1024];

                    int count;
                    while ((count = in.read(bytes)) > 0) {
                        receivedFile.write(bytes, 0, count);
                    }

                    receivedFile.close();
                    in.close();

                    System.out.println("File fully received !");

                } else {
                    // we get a classic message
                    Controller.getInstance().deliverMessage(receveid);
                }

                /*byte[] incomingData = new byte[1024];

                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                // on attend un message
                //socket.receive(incomingPacket);
                byte[] dataReceive = incomingPacket.getData();

                // on reconvertit en ControlMessage
                Message message = networkUtils.convertDataToMessage(dataReceive);
                //System.out.println("message reçue :" + message);
                Controller.getInstance().deliverMessage(message);*/
            }

        } catch (SocketException e) {
            System.out.println("Socket Fermée.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the given message to the dest socket
     * @param msg the msg to send
     */
    public void sendMsg(Message msg) {
        //byte[] data = networkUtils.convertObjToData(msg);
        //DatagramPacket packet = new DatagramPacket(data, data.length, destip, portSocketDest);
        try {
            //socket.send(packet);
            writer.writeObject(msg);
            System.out.println("Message envoyé !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file, String destPseudo) {
        try {
            String extension = "";
            String fileName = file.getAbsolutePath();

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i+1);
            }

            System.out.println(extension);

            // Send the file format and warn that a file is comming
            Message msg = new Message(Message.DataType.File, extension,
                    destPseudo, Controller.getInstance().getCurrentUser().getPseudo());
            this.sendMsg(msg);

            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(file);
            OutputStream out = socketClient.getOutputStream();

            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            out.close();
            in.close();

            System.out.println("File sent !");
        }catch (Exception e) {
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

    public void closeSocket() {
        try {
            running = false;
            if (type == 1) {
                socketServer.close();
                socketClient.close();
            } else {
                socketClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}