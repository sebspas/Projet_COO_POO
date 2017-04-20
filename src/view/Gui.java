package view;

import model.User;

/**
 * Created by tahel on 18/04/17.
 */
public interface Gui {

    void launch();

    void setUserStatus(String name, String status);

    void addUserToChat(String name, Message msg);

    void deliverMessage(network.Message msg);

    void deliverText(String dest, String Message, String source);

    void addNewUser(User u1);

    void addUserToPanel(String name, PanelUserContact panel);

    void createMainWindow();

    void notif(String s);

    void deliverImage(String dest, String path);
}
