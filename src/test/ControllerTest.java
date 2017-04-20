package test;

import controller.Controller;
import model.User;
import network.Message;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tahel on 18/04/17.
 */
public class ControllerTest {
    /**
     * Check if the message is Correctly receive and send back by the parrot
     * @throws Exception
     */
    @Test
    public void sendToUser() throws Exception {
        Controller controller = Controller.getInstance();
        controller.chooseGraphique("Text");
        controller.buttonLoginClicked("Test");

        // we have to add the Parrot first
        Thread.sleep(2000);

        // we know the parrot
        controller.sendToUser("Parrot", new Message(Message.DataType.Text, "Coucou", "Parrot", "Test"));

        // we check the received message
        Message last = controller.getLastMessage();

        // we do an assert to see if it's was well received
        assert (last.getData().equals("Coucou"));
    }

    /**
     * Test if the parrot disconnect correctly from the app
     * @throws Exception
     */
    @Test(timeout = 3000)
    public void disconnect() throws Exception {
        Controller controller = Controller.getInstance();
        controller.chooseGraphique("Text");
        controller.buttonLoginClicked("Test");

        // we have to add the Parrot first
        Thread.sleep(2000);

        // we know the parrot
        controller.sendToUser("Parrot", new Message(Message.DataType.Text, "disconnect", "Parrot", "Test"));

        // wait for the parrot to disconnect
        Thread.sleep(1000);

        assert (controller.getModel().getUserStatus("Parrot") == User.Status.Offline);
    }
}