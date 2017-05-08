package model;

import network.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by tahel on 20/04/17.
 */
public class LogMessages {

    private ArrayBlockingQueue<Message> listMessage;

    public LogMessages() {
        listMessage = new ArrayBlockingQueue<>(1024);
    }

    public void addMessage(Message msg) {
        //System.out.println(" Mess" + msg.toString());
        try {
            listMessage.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message getlastMessage() {
        try {
            //System.out.println(listMessage);
            Message msg = listMessage.poll(3, TimeUnit.SECONDS);
            if (msg == null) {
                System.out.println("Timeout");
                return null;
            } else {
                return msg;
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        return null;
    }
}
