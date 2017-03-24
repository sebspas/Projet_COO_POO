package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by tahel on 28/02/17.
 */
public class ListenSocket extends Thread {


    private Socket socket;

    private BufferedReader reader;

    private ArrayBlockingQueue<String> lines;

    public ListenSocket(Socket socket, BufferedReader reader) {
        this.socket = socket;
        this.reader = reader;
        lines = new ArrayBlockingQueue<String>(1024);
        this.start();

    }

    public void run() {
        while (true) {
            try {
                lines.add(reader.readLine() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getLastline() {
        try {
            return lines.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}