package controller;

import model.Model;
import view.GUIFactory;
import view.GuiElement;
import view.Message;
import view.PanelUserContact;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller {

    private Model model;

    private GuiElement login;
    private GuiElement mainWindows;

    private GUIFactory guiFactory;

    private HashMap<String, PanelUserContact> userToPanel;

    private HashMap<String, Message> usertToChat;

    public Controller() {
        model = new Model();
        guiFactory = new GUIFactory(this);

        userToPanel = new HashMap<>();
        usertToChat = new HashMap<>();

        login = guiFactory.createGui("Login", null);
    }

    public void buttonLoginClicked(String pseudo) {
        try {
            if (model.connectChat(pseudo, Inet4Address.getLocalHost())) {
                mainWindows = guiFactory.createGui("Contacts", null);
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

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    public Model getModel() {
        return model;
    }
}
