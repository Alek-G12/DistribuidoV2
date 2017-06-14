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
public class Server implements Runnable {

    private DatagramSocket socket;
    private final int PORT = 4445;

    public int getPORT() {
        return PORT;
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(PORT);
            socket.setBroadcast(true);
            InetAddress address = InetAddress.getByName("192.168.1.255");
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4446);
                socket.send(packet);
                System.out.println("Broadcast message sent");
                System.out.println("Server Listening...");
                socket.receive(packet);
                String req = new String(packet.getData());
                System.out.println("Received: " + req);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
