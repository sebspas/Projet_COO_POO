package test;

import model.AllUsers;
import model.User;

import java.net.InetAddress;

/**
 * Created by tahel on 18/04/17.
 */
public class AllUsersTest {
    @org.junit.Test
    public void addUser() throws Exception {
        AllUsers allUsers = new AllUsers();

        User usertest = new User("Jacque", InetAddress.getLocalHost());

        assert (allUsers.contains(usertest.getPseudo()) == false);

        allUsers.addUser(usertest);

        assert (allUsers.contains(usertest.getPseudo()) == true);

        System.out.println("addUser Tested");
    }

    @org.junit.Test
    public void setUserStatus() throws Exception {
        AllUsers allUsers = new AllUsers();
        User usertest = new User("Jacque", InetAddress.getLocalHost());
        allUsers.addUser(usertest);
        allUsers.setUserStatus("Jacque", User.Status.Online);

        assert (usertest.getStatus() == User.Status.Online);
        System.out.println("SetUserStatus Tested");
    }

    @org.junit.Test
    public void checkAvailable() throws Exception {
        System.out.println("checkAvailable NOT IMPLEMENTED YET");
    }

    @org.junit.Test
    public void contains() throws Exception {
        AllUsers allUsers = new AllUsers();

        User usertest = new User("Jacque", InetAddress.getLocalHost());

        assert (allUsers.contains(usertest.getPseudo()) == false);

        allUsers.addUser(usertest);

        assert (allUsers.contains(usertest.getPseudo()) == true);

        System.out.println("contains Tested");


    }
}