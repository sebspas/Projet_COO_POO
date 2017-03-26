package controller;

import model.Model;
import model.User;
import network.Network;
import view.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller {

    private Model model;

    private GuiElement login;
    private Contacts mainWindows;

    private GUIFactory guiFactory;

    private HashMap<String, PanelUserContact> userToPanel;

    private HashMap<String, Message> usertToChat;

    private Network network;

    public Controller() {
        model = new Model();
        network = new Network(this);

        guiFactory = new GUIFactory(this);

        userToPanel = new HashMap<>();
        usertToChat = new HashMap<>();

        login = guiFactory.createGui("Login", null);
    }

    public void buttonLoginClicked(String pseudo) {
        try {
            if (model.connectChat(pseudo, Inet4Address.getLocalHost())) {
                mainWindows = (Contacts) guiFactory.createGui("Contacts", null);
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

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

}
