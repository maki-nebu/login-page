

import java.net.*;

public class UDPConsumer {
    public static void main(String[] args) {
        try {
          
            DatagramSocket socket = new DatagramSocket();

         
            int consumerPort = socket.getLocalPort();
            System.out.println("UDP Consumer started. Listening on port " + consumerPort);

            
            InetAddress brokerAddress = InetAddress.getByName("localhost");
            int brokerPort = 4000;
            String initMessage = "Consumer joined on port " + consumerPort;
            DatagramPacket initPacket = new DatagramPacket(initMessage.getBytes(), initMessage.length(), brokerAddress, brokerPort);
            socket.send(initPacket);

          
            byte[] receiveBuffer = new byte[1024];

            while (true) {
              
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);

             
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + message);
            }
        } catch (Exception e) {
            System.err.println("An error occurred in UDPConsumer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}