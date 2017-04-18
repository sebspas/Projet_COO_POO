package test;

import model.Model;
import model.User;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * Created by tahel on 18/04/17.
 */
public class ModelTest {
    @Test
    public void contains() throws Exception {
        Model model = new Model();

        User usertest = new User("Jacque", InetAddress.getLocalHost());

        assert(model.contains("Jacque") == false);

        model.addUser(usertest);

        assert(model.contains("Jacque") == true);

        System.out.println("Contains Tested");
    }

    @Test
    public void connectChat() throws Exception {
        Model model = new Model();

        assert (model.getCurrentUser() == null);
        assert (model.getCurrent_state() == Model.State.Disconnected);

        model.connectChat("Jacque", InetAddress.getLocalHost());

        assert (model.getCurrentUser().getPseudo().equals("Jacque"));
        assert (model.getCurrent_state() == Model.State.Connected);

        System.out.println("connectChat tested");
    }

    @Test
    public void setUserStatus() throws Exception {
        Model model = new Model();
        User usertest = new User("Jacque", InetAddress.getLocalHost());

        assert (usertest.getStatus() == User.Status.Online);

        model.addUser(usertest);
        model.setUserStatus("Jacque", User.Status.Offline);

        assert (usertest.getStatus() == User.Status.Offline);

        System.out.println("SetUserStatusTested");
    }

}