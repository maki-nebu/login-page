import java.net.*;
import java.io.*;

public class CLIENT {
    public static void main(String[] args) {
        try {
   
            Socket client = new Socket("192.168.101.6", 1020);
            System.out.println("Connected to server");

            DataInputStream input = new DataInputStream(client.getInputStream());
            String receivedMessage = input.readUTF(); 
            System.out.println("Received from server: " + receivedMessage);
            
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            output.writeUTF("Hello server");

            
            output.close();
            input.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
              
                Socket server2 = new Socket("server2", 1022);
                DataInputStream input = new DataInputStream(server2.getInputStream());
                String receivedMessage = input.readUTF(); 
                System.out.println("Received from Server2: " + receivedMessage);
                
                
                input.close();
                server2.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}