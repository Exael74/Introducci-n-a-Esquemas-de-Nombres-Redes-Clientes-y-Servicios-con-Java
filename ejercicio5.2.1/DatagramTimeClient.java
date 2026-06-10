import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeClient {
    public static void main(String[] args) {
        String lastKnownTime = "Desconocida (Iniciando...)";

        try {
            DatagramSocket socket = new DatagramSocket();
            // Timeout de 2 segundos para no bloquearse eternamente si el servidor esta caido
            socket.setSoTimeout(2000); 
            InetAddress address = InetAddress.getByName("127.0.0.1");

            while (true) {
                try {
                    byte[] sendBuf = new byte[256];
                    DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, 4445);
                    socket.send(sendPacket);

                    byte[] recvBuf = new byte[256];
                    DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
                    
                    socket.receive(recvPacket); // Esto lanzara excepcion si pasa del timeout
                    lastKnownTime = new String(recvPacket.getData(), 0, recvPacket.getLength());
                    
                    System.out.println("Date recibida: " + lastKnownTime);
                } catch (SocketTimeoutException timeoutEx) {
                    // Servidor caido o no respondio
                    System.out.println("Servidor inalcanzable. Manteniendo la ultima hora conocida: " + lastKnownTime);
                } catch (IOException ex) {
                    System.err.println("Error de I/O en envio/recepcion del datagrama.");
                }

                // Esperar 5 segundos
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
