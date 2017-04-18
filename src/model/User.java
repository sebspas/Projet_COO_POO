package model;

import javax.swing.*;
import java.net.InetAddress;

/**
 * The class who represent an user of the app
 */
public class User {

    // All the status possible for an user : Online, Offline...
    public enum Status {
        Online,
        Offline
    }

    // The name of the user
    private String pseudo;

    // The ip of the user
    private InetAddress ip;

    // The state of the user
    private Status status;

    // Avatar of the user (Predefined for the moment)
    private Icon icon;

    /**
     * Constructor, create an user with the given parameters
     * @param pseudo the name
     * @param ip the IP adress
     */
    public User(String pseudo, InetAddress ip) {
        this.pseudo = pseudo;
        this.ip = ip;
        this.status = Status.Online;
        this.icon = new ImageIcon(this.getClass().getResource("/user.png"));
    }

    /**
     * Getter of the status
     * @return Status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the status of the current user
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter of the Pseudo
     * @return String pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Getter of the Icon
     * @return Icon of the user
     */
    public Icon getIcon() {
        return icon;
    }
}
