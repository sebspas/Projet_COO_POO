package controller;

import model.Model;
import model.User;
import network.*;
import view.*;
import view.Message;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller {

    /* Model who control the collected data of the user on the network  */
    private Model model;

    private GuiElement login;
    private Contacts mainWindows;

    private HashMap<String, PanelUserContact> userToPanel;

    private HashMap<String, Message> usertToChat;

    private Network network;


    /** Instance unique pré-initialisée (singleton) */
    private static Controller INSTANCE = new Controller();

    /* Constructeur privé */
    private Controller() {

            model = new Model();

            userToPanel = new HashMap<>();
            usertToChat = new HashMap<>();

            login = GUIFactory.createGui(GUIFactory.TypeWindows.LOGIN);
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static Controller getInstance()
    {
        return INSTANCE;
    }

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

    public void addUserToPanel(String name, PanelUserContact panel) {
        this.userToPanel.put(name, panel);
    }

    public void setUserOffLine(String name) {
        userToPanel.get(name).setStatus("Offline");
    }

    public void disconnect() {
        network.sendDisconnect();
    }

    public void addUserToChat(String name, Message msg) {
        this.usertToChat.put(name, msg);
    }

    public Model getModel() {
        return model;
    }

    public void addUser(String name, InetAddress ip) {
        User u1 = new User(name, ip);
        model.addUser(u1);
        mainWindows.addNewUser(u1);
    }

    public User getCurrentUser() {
        return model.getCurrentUser();
    }

    public void sendToUser(User dest, network.Message msg) {
        network.getSocket(dest.getPseudo()).sendMsg(msg);
    }

    public void deliverMessage(network.Message msg) {
        usertToChat.get(msg.getSrcPseudo()).addMessage(msg.getData());
    }

    public static void main(String[] args) {

        Controller controller = Controller.getInstance();
    }
}
