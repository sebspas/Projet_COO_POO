package controller;

import model.Model;
import model.User;
import network.Network;
import view.*;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Controller of the App, control every interaction between the network
 * and the other components of the app (view and model)
 */
public class Controller {

    // Model who control the collected data of the user on the network
    private Model model;
    // Network component, control the network interactions
    private Network network;
    // First GUI we need to keep it as data member to try to connect
    private GuiElement login;
    // Main Frame, we need to keep it to have a track of the interaction
    private Contacts mainWindows;
    // For a pseudo we keep the panelUserContact to be able to access it (to change status for example)
    private HashMap<String, PanelUserContact> userToPanel;
    // For a pseudo we keep the window of message
    private HashMap<String, Message> usertToChat;

    /** Instance unique pré-initialisée (singleton) */
    private static Controller INSTANCE = new Controller();

    /**
     * Basic contructeur, initalize the differents data
     * and create the first window (login)
     */
    private Controller() {
            model = new Model();
            userToPanel = new HashMap<>();
            usertToChat = new HashMap<>();
            login = GUIFactory.createGui(GUIFactory.TypeWindows.LOGIN);
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
        userToPanel.get(name).setStatus("Offline");
    }

    /**
     * When the user click the login button, we check with model if it's okay,
     * if it's we connect to the network and we close the login window and open
     * a new window
     * @param pseudo
     */
    public void buttonLoginClicked(String pseudo) {
        try {
            if (model.connectChat(pseudo, Inet4Address.getLocalHost())) {
                mainWindows = (Contacts) GUIFactory.createGui(GUIFactory.TypeWindows.CONTACTS);
                network = new Network();
                login.close();
            } else {
                login.notif("Connection impossible");
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
        this.usertToChat.put(name, msg);
    }

    /**
     * We add an panelUserContact withe corresponding username
     * @param name the name of the user
     * @param panel the panel on the contact window
     */
    public void addUserToPanel(String name, PanelUserContact panel) {
        this.userToPanel.put(name, panel);
    }



    /**
     * Disconnect the app before to close it
     */
    public void disconnect() {
        network.sendDisconnect();
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
            // user already exist, so we update his status
            userToPanel.get(name).setStatus("Online");
        } else {
            // we create a new user
            User u1 = new User(name, ip);
            model.addUser(u1);
            mainWindows.addNewUser(u1);
        }
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
        network.getSocket(dest.getPseudo()).sendMsg(msg);
    }

    /**
     * Print the received message on the good message window
     * @param msg the message to print
     */
    public void deliverMessage(network.Message msg) {
        usertToChat.get(msg.getSrcPseudo()).addMessage(msg.getData(), msg.getSrcPseudo());
    }

    public void deliverText(String dest, String Message, String source) {
        usertToChat.get(dest).addMessage(Message, source);
    }

    /**
     * Main to launch of the program
     * @param args no args for the moment
     */
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
    }

    /**
     * Send a file to the selected user
     * @param selectedFile file to send
     * @param pseudo the name of the des
     */
    public void sendFileToUser(File selectedFile, String pseudo) {
        network.getSocket(pseudo).sendFile(selectedFile, pseudo);
    }
}
