package model;

import javax.swing.*;
import java.net.InetAddress;

public class User {

    public enum Status {
        Online,
        Offline
    }

    private String pseudo;

    private InetAddress ip;

    private Status status;

    private Icon icon;


    public User(String pseudo, InetAddress ip) {
        this.pseudo = pseudo;
        this.ip = ip;
        this.status = Status.Online;

        this.icon = new ImageIcon("images/user.png");
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Icon getIcon() {
        return icon;
    }
}
