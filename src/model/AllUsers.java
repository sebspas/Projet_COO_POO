package model;

import java.util.ArrayList;

/**
 *  Class AllUsers, it's just a Array of all the user known by the app
 */
public class AllUsers {
    // List of all the user known by the app
    private ArrayList<User> listUsers;

    /**
     *  Basic constructor just set-up the data members to empty
     */
    public AllUsers() {
        this.listUsers = new ArrayList<>();
    }

    /**
     *  Add an user to the list of user
     * @param user the user to add
     */
    public void addUser(User user) {
        listUsers.add(user);
    }

    /**
     *  Change the status of the given user
     * @param name
     * @param status
     */
    public void setUserStatus(String name, User.Status status) {

        for (User u: listUsers) {
            if (u.getPseudo().equals(name)) {
                u.setStatus(status);
            }
        }
    }

    /**
     * NOT IMPLEMENTED YET
     * @param pseudo
     * @return
     */
    public boolean checkAvailable(String pseudo) {
        // send a text to allUsers to see if the chat is available
        // wait for an answer
        // add dans la table
        // if there is no bad answer say ok
        //TODO
        return true;
    }

    /**
     * Check if the user given as parameter exist in the lis of all users
     * @param pseudo the name of the user to look for
     * @return  true or false
     */
    public boolean contains(String pseudo) {
        for (User u: listUsers
             ) {
            if(u.getPseudo().equals(pseudo)) {
                return true;
            }
        }

        return false;
    }
}
