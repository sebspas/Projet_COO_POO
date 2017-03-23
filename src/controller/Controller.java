package controller;

import model.Model;
import view.GUIFactory;
import view.GuiElement;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by tahel on 08/03/17.
 */
public class Controller {

    private Model model;

    private GuiElement login;
    private GuiElement mainWindows;

    private GUIFactory guiFactory;

    public Controller() {
        model = new Model();
        guiFactory = new GUIFactory(this);

        login = guiFactory.createGui("Login");
    }

    public void buttonLoginClicked(String pseudo) {
        try {
            if (model.connectChat(pseudo, Inet4Address.getLocalHost())) {
                mainWindows = guiFactory.createGui("Contacts");
                login.close();
            } else {
                login.notif("Connection impossible");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    public Model getModel() {
        return model;
    }
}
