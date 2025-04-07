import java.net.*;
import java.util.Scanner;

public class UDProducer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            
            DatagramSocket socket = new DatagramSocket();

            
            InetAddress brokerAddress = InetAddress.getByName("localhost");
            int brokerPort = 4000;

            System.out.println("UDP Producer started. Type messages to send to the broker:");

            while (true) {
                System.out.print("Message: ");
                String message = scanner.nextLine();

             
                byte[] data = message.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, brokerAddress, brokerPort);
                socket.send(packet);

                System.out.println("Message sent to broker.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred in UDPProducer: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}