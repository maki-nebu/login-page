import java.net.*;
import java.io.*;

public class clientA {
    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.0.95", 1234); 
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server.");

            
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = input.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Error receiving message: " + e.getMessage());
                }
            }).start();

            
            String userInput;
            while (true) {
                System.out.print("Enter message: ");
                userInput = consoleInput.readLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    break; 
                }
                output.println(userInput);
                
                System.out.println("Server response: " + input.readLine());
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}