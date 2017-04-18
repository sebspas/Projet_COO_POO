package view;

import model.User;

/**
 * Created by tahel on 18/04/17.
 */
public abstract class Gui {

    public void launch() {
    }

    public void setUserStatus(String name, String status) {
    }

    public void addUserToChat(String name, Message msg) {
    }

    public void deliverMessage(network.Message msg) {
    }

    public void deliverText(String dest, String Message, String source) {
    }

    public void addNewUser(User u1) {
    }

    /**
     * We add an panelUserContact withe corresponding username
     * @param name the name of the user
     * @param panel the panel on the contact window
     */
    public void addUserToPanel(String name, PanelUserContact panel) {
    }

    public void createMainWindow() {
    }

    public void notif(String s) {
    }
}
