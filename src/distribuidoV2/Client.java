package distribuidoV2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alek_G12
 */
public class Client implements Runnable {

    DatagramSocket socket;
    private int PORT = 4446;
    private String clientAddress;

    public Client() {
        try {
            this.clientAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPORT() {
        return PORT;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(PORT);
            DatagramPacket packet;
            byte[] buf;
            while (true) {
                buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);;
                socket.receive(packet);
                InetAddress svAddress = packet.getAddress();

                String reply = System.getProperty("user.name");
                reply = reply.concat("\t@\t" + InetAddress.getLocalHost().getHostName());
                buf = reply.getBytes();
                packet = new DatagramPacket(buf, buf.length, svAddress, 4445);
                socket.send(packet);
                Thread.sleep(1000);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
