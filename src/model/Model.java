package model;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by tahel on 14/03/17.
 */
public class Model {

    private AllUsers allUsers;

    private User currentUser;

    public boolean contains(String name) {
        return allUsers.contains(name);
    }

    public enum State {
        Disconnected,
        Connected
    }

    private State current_state;

    public Model() {
        this.current_state = State.Disconnected;
        allUsers = new AllUsers();
    }

    public boolean connectChat(String pseudo, InetAddress inet4Address) {
        if (allUsers.checkAvailable(pseudo)) {
            // si pseudo est libre
            // on crée l'user courant et on le passe dans la table
            currentUser = new User(pseudo, inet4Address);
            System.out.println("Utilisateur crée !");
            return true;
        } else {
            // si le pseudo n'est pas libre
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addUser(User user) {
        this.allUsers.addUser(user);
    }
}
