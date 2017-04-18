package view;

import controller.Controller;
import model.User;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by tahel on 18/04/17.
 */
public class Gui {

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

    }

    public void launch() {
        if (typeGui == Gui.type_gui.TEXT) {
            System.out.println("Entrer le pseudo : ");
            Scanner scanner = new Scanner(System.in);
            String pseudo = scanner.next();
            Controller.getInstance().buttonLoginClicked(pseudo);
        } else {
            login = GUIFactory.createGui(GUIFactory.TypeWindows.LOGIN);
            active = login;
        }
    }

    public void setUserStatus(String name, String status) {
        if (typeGui == Gui.type_gui.TEXT) {
            System.out.println(name + " est maintenant deconnecté " + status);
        } else {
            userToPanel.get(name).setStatus(status);
        }
    }

    public void addUserToChat(String name, Message msg) {
        if (typeGui == Gui.type_gui.TEXT) {
            System.out.println(name + " viens de se connecter." );
        } else {
            this.usertToChat.put(name, msg);
        }
    }

    public void deliverMessage(network.Message msg) {
        if (typeGui == Gui.type_gui.TEXT) {
            System.out.println("Message de " + msg.getSrcPseudo() + " : " + msg.getData());
        } else {
            usertToChat.get(msg.getSrcPseudo()).addMessage(msg.getData(), msg.getSrcPseudo());
        }
    }

    public void deliverText(String dest, String Message, String source) {
        if (typeGui == Gui.type_gui.TEXT) {
            System.out.println("Message de " + dest + " : " + Message);
        } else {
            usertToChat.get(dest).addMessage(Message, source);
        }
    }

    public void addNewUser(User u1) {
        if (typeGui == Gui.type_gui.TEXT) {
            System.out.println(u1.getPseudo() + " est maintenant connecté." );
        } else {
            mainWindows.addNewUser(u1);
        }
    }

    /**
     * We add an panelUserContact withe corresponding username
     * @param name the name of the user
     * @param panel the panel on the contact window
     */
    public void addUserToPanel(String name, PanelUserContact panel) {
        if (typeGui == Gui.type_gui.TEXT) {
            // rien
        } else {
            this.userToPanel.put(name, panel);
        }
    }

    public void createMainWindow() {
        if (typeGui == type_gui.TEXT) {
            System.out.println("Vous êtes connecté.");
        } else {
            mainWindows = (Contacts) GUIFactory.createGui(GUIFactory.TypeWindows.CONTACTS);
            active = mainWindows;
            login.close();
        }
    }

    public void notif(String s) {
        active.notif(s);
    }
}
