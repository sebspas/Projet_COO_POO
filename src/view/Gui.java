package view;

import model.User;

import java.util.HashMap;

/**
 * Created by tahel on 18/04/17.
 */
public class Gui {

    public void createMainWindow() {
        mainWindows = (Contacts) GUIFactory.createGui(GUIFactory.TypeWindows.CONTACTS);
        active = mainWindows;
        login.close();
    }

    public void notif(String s) {
        active.notif(s);
    }

    public enum type_gui {
        TEXT,
        GRAPHIQUE
    }

    private type_gui typeGui;

    private GuiElement active;

    // First GUI we need to keep it as data member to try to connect
    private GuiElement login;
    // Main Frame, we need to keep it to have a track of the interaction
    private Contacts mainWindows;
    // For a pseudo we keep the panelUserContact to be able to access it (to change status for example)
    private HashMap<String, PanelUserContact> userToPanel;
    // For a pseudo we keep the window of message
    private HashMap<String, Message> usertToChat;

    public Gui(type_gui type_gui) {
        this.typeGui = type_gui;
        userToPanel = new HashMap<>();
        usertToChat = new HashMap<>();

        if (type_gui == Gui.type_gui.TEXT) {
            System.out.println("Entrer le pseudo : ");
            //TODO ARRIVER DU PSEUDO
        } else {
            login = GUIFactory.createGui(GUIFactory.TypeWindows.LOGIN);
            active = login;
        }

    }

    public void setUserStatus(String name, String status) {
        userToPanel.get(name).setStatus(status);
    }

    public void addUserToChat(String name, Message msg) {
        this.usertToChat.put(name, msg);
    }

    public void deliverMessage(network.Message msg) {

        usertToChat.get(msg.getSrcPseudo()).addMessage(msg.getData(), msg.getSrcPseudo());
    }

    public void deliverText(String dest, String Message, String source) {
        usertToChat.get(dest).addMessage(Message, source);
    }


    public void addNewUser(User u1) {
        mainWindows.addNewUser(u1);
    }

    /**
     * We add an panelUserContact withe corresponding username
     * @param name the name of the user
     * @param panel the panel on the contact window
     */
    public void addUserToPanel(String name, PanelUserContact panel) {
        this.userToPanel.put(name, panel);
    }


}
