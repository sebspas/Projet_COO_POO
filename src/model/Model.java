package model;

import java.net.InetAddress;

/**
 * Class Model , represent all the different data needed by the app
 */
public class Model {

    // List of all the User
    private AllUsers allUsers;

    // Current user of the app
    private User currentUser;

    // All the state that the app can enter in
    public enum State {
        Disconnected,
        Connected
    }

    // Current state of the app
    private State current_state;

    /**
     * Basic constructor just set up the data member to empty
     */
    public Model() {
        this.current_state = State.Disconnected;
        allUsers = new AllUsers();
    }

    /**
     * Check if the user exist in the list of all user, using the method of AllUsers Class
     * @param name of the user to look for
     * @return true or false
     */
    public boolean contains(String name) {
        return allUsers.contains(name);
    }

    /**
     * Set up the connection to the chat system
     * @param pseudo pseudo of the user to connect
     * @param inet4Address Adress IP of the user to connect
     * @return True if the user is no connected, false if not
     */
    public boolean connectChat(String pseudo, InetAddress inet4Address) {
        if (allUsers.checkAvailable(pseudo)) {
            // si pseudo est libre
            // on crée l'user courant et on le passe dans la table
            currentUser = new User(pseudo, inet4Address);
            System.out.println("Utilisateur crée !");
            this.current_state = State.Connected;
            return true;
        } else {
            // si le pseudo n'est pas libre
            return false;
        }
    }

    /**
     * Change the status of an user
     * @param pseudo name of the user to change status
     * @param status the new status
     */
    public void setUserStatus(String pseudo, User.Status status) {
        allUsers.setUserStatus(pseudo, status);
    }

    /**
     * Return the current user of the app
     * @return User the currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Add an user to the list of allUser, using the method of the AllUser Class
     * @param user the user to add
     */
    public void addUser(User user) {
        this.allUsers.addUser(user);
    }
}
