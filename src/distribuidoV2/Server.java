package distribuidoV2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;
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
            socket.setSoTimeout(2000);
            InetAddress address = InetAddress.getByName("192.168.1.255");
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4446);
                socket.send(packet);
                System.out.println("broadcast sent");
                try {
                    socket.receive(packet);
                } catch (SocketTimeoutException ex) {
                    System.out.println("Socket Timed out **************");
                    continue;
                }
                handlePacket(packet);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handlePacket(DatagramPacket packet) {
        new Thread(() -> {
            String req = new String(packet.getData());
            Date date = new Date();
            System.out.println("Received: " + req + "\tfrom: " + packet.getAddress() + "\t " + date.toString());
        }).start();
    }

}
