package distribuidoV2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alek_G12
 */
public class Client implements Runnable {

    DatagramSocket socket;
    private int PORT = 4446;

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(PORT);
            DatagramPacket packet;
            byte[] buf;
            while (true) {
                buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                System.out.println("Client waiting");
                socket.receive(packet);
                System.out.println("Client received packet");
                InetAddress svAddress = packet.getAddress();
                System.out.println("Server Address: " + svAddress);

                String reply = System.getProperty("user.name");
                buf = reply.getBytes();
                packet = new DatagramPacket(buf, buf.length, svAddress, 4445);
                socket.send(packet);
                System.out.println("Packet Sent");
            }
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
