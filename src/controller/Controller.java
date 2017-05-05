package controller;

import model.Model;
import model.User;
import network.*;
import view.*;
import view.Message;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Controller of the App, control every interaction between the network
 * and the other components of the app (view and model)
 */
public class Controller implements Facade{

    // Model who control the collected data of the user on the network
    private Model model;
    // Network component, control the network interactions
    private Network network;
    // FLAG for the parrot behavior
    private boolean isParrot = false;

    /**
     * Make this controller a parrot, it will repeat every
     * message and resend it
     */
    public void setParrot() {
        isParrot = true;
        gui = new GuiText();
    }

    /**
     * Choose the type of interface TEXT or GRAPHIQUE
     * @param type "text" or "graphique"
     */
    public void chooseGraphique(String type) {
        if (type.equals("Text")) {
            gui = new GuiText();
        } else {
            gui = new GuiGraphique();
        }
    }


    private Gui gui;

    /** Instance unique pré-initialisée (singleton) */
    private static Controller INSTANCE = new Controller();

    /**
     * Basic contructeur, initalize the differents data
     * and create the first window (login)
     */
    private Controller() {
        model = new Model();
    }

    /**
     * Getter of the singelton instance
     * @return the instance
     */
    public static Controller getInstance()
    {
        return INSTANCE;
    }

    /**
     *  Set the user given as parameter Offline, in the model
     *  and as well in the view
     * @param name
     */
    public void setUserOffLine(String name) {
        model.setUserStatus(name, User.Status.Offline);
        gui.setUserStatus(name, User.Status.Offline.toString());
    }

    /**
     * When the user click the login button, we check with model if it's okay,
     * if it's we connect to the network and we close the login window and open
     * a new window
     * @param pseudo the name of the user to connect
     */
    public void connect(String pseudo) {
        try {
            if (model.connectChat(pseudo, Inet4Address.getLocalHost())) {
                gui.createMainWindow();
                network = new Network();
            } else {
               gui.notif("Connexion Impossible");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add an user to the chat, and add the corresponding message window
     * @param name the name of the user
     * @param msg the message window
     */
    public void addUserToChat(String name, Message msg) {
        gui.addUserToChat(name, msg);
    }

    /**
     * We add an panelUserContact withe corresponding username
     * @param name the name of the user
     * @param panel the panel on the contact window
     */
    public void addUserToPanel(String name, PanelUserContact panel) {
        gui.addUserToPanel(name, panel);
    }



    /**
     * Disconnect the app before to close it
     */
    public void disconnect() {
        network.sendDisconnect();
        System.exit(0);
    }


    /**
     * Getter for the model
     * @return the model
     */
    public Model getModel() {
        return model;
    }

    /**
     * Add on user in the app
     * @param name the pseudo of the user
     * @param ip the ip
     */
    public void addUser(String name, InetAddress ip) {
        // two cases if user already exist or not
        if (model.contains(name)) {
            model.setUserStatus(name, User.Status.Online);
            // user already exist, so we update his status
            gui.setUserStatus(name, User.Status.Online.toString());
        } else {
            // we create a new user
            User u1 = new User(name, ip);
            model.addUser(u1);
            gui.addNewUser(u1);
        }
    }

    @Override
    public String getCurrentUserPseudo() {
        return getCurrentUser().getPseudo();
    }

    @Override
    public String getCurrentUserStatus() {
        return getCurrentUser().getStatus().toString();
    }

    /**
     * Getter of the current User
     * @return the current user
     */
    public User getCurrentUser() {
        return model.getCurrentUser();
    }

    /**
     * Send the message to the corresponding dest
     * @param dest the dest to send at
     * @param msg the message to send
     */
    public void sendToUser(User dest, network.Message msg) {
        if (network.getSocket(dest.getPseudo()) != null) {
            network.getSocket(dest.getPseudo()).sendMsg(msg);
        } else {
            deliverText(dest.getPseudo(), "L'utilisateur n'est pas connecté !", "System");
        }
    }

    public void sendToUser(String pseudo, network.Message msg) {
        if (network.getSocket(pseudo) != null) {
            network.getSocket(pseudo).sendMsg(msg);
        } else {
            deliverText(pseudo, "L'utilisateur n'est pas connecté !", "System");
        }
    }

    /**
     * Print the received message on the good message window
     * @param msg the message to print
     */
    public void deliverMessage(network.Message msg) {
        if (isParrot) {
            gui.deliverMessage(msg);

            network.Message msgBis = new network.Message(msg.getType(), msg.getData(), msg.getSrcPseudo(), msg.getDestPseudo());

            if (msg.getData().equals("disconnect")) {
                this.disconnect();
            } else {
                sendToUser(msg.getSrcPseudo(), msgBis);
            }
        } else {
            // add to the log
            model.getLogMessages().addMessage(msg);

            gui.deliverMessage(msg);
        }
    }

    public void deliverText(String dest, String Message, String source) {
        gui.deliverText(dest, Message, source);
    }

    public network.Message getLastMessage() {
        return model.getLogMessages().getlastMessage();
    }

    /**
     * Send a file to the selected user
     * @param selectedFile file to send
     * @param pseudo the name of the des
     */
    public void sendFileToUser(File selectedFile, String pseudo) {
        network.getSocket(pseudo).sendFile(selectedFile, pseudo);
    }

    public void launch(){
        if (isParrot) {
            this.connect("Parrot");
        } else {
            gui.launch();
        }
    }

    public void deliverImage(String dest, String path) {
        gui.deliverImage(dest, path);
    }

    public void setUserStatus(String userName, String data) {
        model.setUserStatus(userName, User.Status.valueOf(data));
        gui.setUserStatus(userName, data);
    }

    public void sendStatusChange(String status) {
        network.sendStatusUpdate(status);
    }

    public String chooseDirectory(String dest) {
        return gui.chooseDirectory(dest);
    }
    /**
     * Main to launch of the program
     * @param args no args for the moment
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Controller controller = Controller.getInstance();
            controller.chooseGraphique("graphique");
            //controller.setParrot();
            controller.launch();
        } else if (args[0].equals("parrot")){
            Controller controller = Controller.getInstance();
            controller.chooseGraphique("graphique");
            controller.setParrot();
            controller.launch();
        } else {
            System.out.println("Erreur argument invalide, ne rien mettre en argument pour lancer l'app ou mettre :" +
                    " parrot pour un perroquet!");
        }

    }

}
