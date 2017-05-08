package test;

import model.LogMessages;
import network.Message;
import org.junit.Test;

/**
 * Created by FatePc on 5/8/2017.
 */
public class LogMessagesTest {

    @Test
    public void getLastMessage() {
        LogMessages log = new LogMessages();

        Message m1 = new Message(Message.DataType.Text, "msg1", "me", "me");

        Message m2 = new Message(Message.DataType.Text, "msg2", "me", "me");

        log.addMessage(m1);

        log.addMessage(m2);

        assert (log.getlastMessage() == m1);
        System.out.println("test getLastMessage validate !");
    }
}