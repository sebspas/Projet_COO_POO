package model;

import java.util.ArrayList;

/**
 * Created by tahel on 14/03/17.
 */
public class AllUsers {

    private ArrayList<User> listUsers;

    public AllUsers() {
        this.listUsers = new ArrayList<>();
    }

    public void addUser(User u) {
        listUsers.add(u);
    }

    public void setUserStatus(String name, User.Status status) {

        for (User u: listUsers) {
            if (u.getPseudo().equals(name)) {
                u.setStatus(status);
            }
        }
    }

    public boolean checkAvailable(String pseudo) {
        // send a text to allUsers to see if the chat is available
        // wait for an answer
        // add dans la table
        // if there is no bad answer say ok
        return true;
    }

}
