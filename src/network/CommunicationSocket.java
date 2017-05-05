package network;

import controller.Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

    // FOR the file transfer
    private String destPseudoFile;
    private File fileToSend;
    private int lengthToReceived;
    private String fileNameToReceive;
    private String pseudoOfSrc;

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
                    if (receveid.getData().equals("accept")) {
                        // we received the accept for the file to send so we send it
                        if (fileToSend != null) {
                            // we send the file
                            sendFileData();
                        } else {
                            System.out.println("Erreur pas de fichier à envoyer");
                        }
                    } else {
                        // we print that we can receive a file
                        System.out.println("Starting File reception ....");
                        System.out.println("Name of the file : " + receveid.getData());
                        Controller.getInstance().deliverText(receveid.getSrcPseudo(), "Starting file reception : " + receveid.getData(), "System");
                        // we wait for an other message with the size of the file
                        Message received2 = (Message)reader.readObject();
                        pseudoOfSrc = receveid.getSrcPseudo();
                        fileNameToReceive = receveid.getData();
                        lengthToReceived = Integer.parseInt(received2.getData());
                        System.out.println("Length to received : " + lengthToReceived);
                    }
                } else {
                    // we get a classic message
                    Controller.getInstance().deliverMessage(receveid);
                }
            }

        } catch (EOFException e) {
          // it's nothing
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

    public void receiveFileData(String path) {
        try {
            // we send the accept first
            Message msg = new Message(Message.DataType.File,
                    "accept",
                    destPseudoFile,
                    Controller.getInstance().getCurrentUserPseudo());
            this.sendMsg(msg);

            // we gotta get a file so we do the following process
            // we create an empty file with the right format
            OutputStream receivedFile = new FileOutputStream(path + "/" + fileNameToReceive);

            String imagePath = path + "/" + fileNameToReceive;
            // temporary inputStream for the trasnfert
            InputStream in = socketClient.getInputStream();

            byte[] bytes = new byte[16*1024];
            int cptSize = 0;
            int count = 0;
            while (cptSize < lengthToReceived && (count = in.read(bytes)) > 0) {
                receivedFile.write(bytes, 0, count);
                cptSize += count;
                //System.out.println(cptSize);
            }
            //in.reset();
            receivedFile.close();

            System.out.println("File fully received !");
            Controller.getInstance().deliverText(pseudoOfSrc, "File received : " + fileNameToReceive, "System");

            // if it's an image format we print it in the window
            String extension = "";

            int i = fileNameToReceive.lastIndexOf('.');
            if (i > 0) {
                extension = fileNameToReceive.substring(i+1);
            }
            System.out.println("Extension :" + extension);
            if (extension.equals("png") || extension.equals("jpg") || extension.equals("gif")) {
                System.out.println(imagePath);
                Controller.getInstance().deliverImage(pseudoOfSrc, imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFileData() {
        try {
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(fileToSend);
            OutputStream out = socketClient.getOutputStream();

            int count;
            int cptSize = 0;
            while ((count = in.read(bytes)) > 0) {
                //Controller.getInstance().deliverText(destPseudo, "Percentage of transfert : " + (float)cptSize/file.length() + "%", "System");
                out.write(bytes, 0, count);
                cptSize += count;
            }

            out.flush();
            in.close();
            Controller.getInstance().deliverText(destPseudoFile, "File sent !", "System");

            System.out.println("File sent !");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file, String destPseudo) {

        Controller.getInstance().deliverText(destPseudo, "Starting the file transfert of : " + file.getName(), "System");
        // Send the file format and warn that a file is comming
        Message msg = new Message(Message.DataType.File, file.getName(),
                destPseudo, Controller.getInstance().getCurrentUser().getPseudo());
        this.sendMsg(msg);
        // send the file size
        Message msg2 = new Message(Message.DataType.File, file.length() + "",
                destPseudo, Controller.getInstance().getCurrentUser().getPseudo());
        this.sendMsg(msg2);

        this.destPseudoFile = destPseudo;
        this.fileToSend = file;
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