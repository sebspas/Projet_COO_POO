package network;

import java.util.Scanner;

/**
 * Created by FatePc on 3/25/2017.
 */
public class testNetwork {
    public static void main(String[] args) {
        Network n = new Network();

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Selectionner dest : ");
            String dest = scan.nextLine();
            System.out.println("message à envoyer : ");
            String msg = scan.nextLine();

            n.getSocket(dest).sendMsg(new Message(Message.DataType.Text, msg, dest, "tahel"));
        }
    }
}
