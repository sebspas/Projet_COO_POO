package model;

import java.net.InetAddress;

public class User {

    public enum Status {
        Online,
        Offline
    }

    private String pseudo;

    private InetAddress ip;

    private Status status;


    public User(String pseudo, InetAddress ip) {
        this.pseudo = pseudo;
        this.ip = ip;
        status = Status.Offline;
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
}
