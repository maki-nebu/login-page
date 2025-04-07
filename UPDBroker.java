import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class UPDBroker {
    public static void main(String[] args) {
        try {
          
            DatagramSocket socket = new DatagramSocket(4000);
            System.out.println("Message broker started on port 4000");

            byte[] receiveBuffer = new byte[1024];

           
            CopyOnWriteArrayList<InetSocketAddress> clients = new CopyOnWriteArrayList<>();

            while (true) {
               
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);

            
                String message = new String(packet.getData(), 0, packet.getLength());
                InetSocketAddress senderAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());

                System.out.println("Received message from " + senderAddress + ": " + message);

             
                if (!clients.contains(senderAddress)) {
                    clients.add(senderAddress);
                }
                for (InetSocketAddress client : clients) {
                    if (!client.equals(senderAddress)) {
                        try {
                            byte[] data = message.getBytes();
                            DatagramPacket forwardPacket = new DatagramPacket(
                                data,
                                data.length,
                                client.getAddress(),
                                client.getPort()
                            );
                            socket.send(forwardPacket);
                        } catch (Exception e) {
                            System.err.println("Failed to send message to " + client + ": " + e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}