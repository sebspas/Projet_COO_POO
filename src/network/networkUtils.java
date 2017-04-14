package network;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Libs of a lot of useful function for the network
 */
public class networkUtils {

    /**
     * Check all the networkInterface, and for each of them check all the IPAdress
     * until there is a valid one (non local, non loopback)
     * @return the good InetAdress
     * @throws UnknownHostException
     */
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;
                // Iterate all IP addresses assigned to each card...
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress() && !inetAddr.toString().equals("127.0.0.1")) {

                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it immediately...
                            return inetAddr;
                        }
                        else if (candidateAddress == null) {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }

            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

    /**
     * Convert the array of byte received into a ControlMessage
     * @param dataReceive the byte[] received
     * @return the ControlMessage
     */
    public static ControlMessage convertDataToControlMessage(byte[] dataReceive) {

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(dataReceive);
            ObjectInputStream is = null;
            is = new ObjectInputStream(in);
            ControlMessage controlMessage1 = (ControlMessage) is.readObject();
            System.out.println("Message reçu = " + controlMessage1.toString());
            return controlMessage1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the array of byte received into a Message
     * @param dataReceive the byte[] received
     * @return the Message
     */
    public static Message convertDataToMessage(byte[] dataReceive) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(dataReceive);
            ObjectInputStream is = null;
            is = new ObjectInputStream(in);
            Message msg = (Message) is.readObject();
            return msg;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the obj to send to an array of byte[]
     * @param obj the ocj to send
     * @return byte[] containing  the object
     */
    public static byte[] convertObjToData(Object obj) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = null;
            os = new ObjectOutputStream(outputStream);
            os.writeObject(obj);
            byte[] data = outputStream.toByteArray();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
