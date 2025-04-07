import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientTask {
    private static Socket socket; 
    private static DataOutputStream output; 
    private static DataInputStream input; 

    public static void main(String[] args) {
        Frame f = new Frame("Client Task");
        f.setSize(400, 300);
        f.setLayout(new FlowLayout());

        TextField num1 = new TextField(10);
        TextField num2 = new TextField(10); 
        Choice operation = new Choice();
        operation.add("+");
        operation.add("-");
        operation.add("*");
        operation.add("/");
        Button ok = new Button("OK"); 
        TextArea res = new TextArea(10, 20); 
        res.setEditable(false);

        f.add(new Label("Num1:"));
        f.add(num1);
        f.add(new Label("Num2:"));
        f.add(num2);
        f.add(new Label("="));
        f.add(operation);
        f.add(ok);
        f.add(res);

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendData(num1, num2, operation, res);
            }
        });

        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                    if (socket != null) socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        // Establish connection to the server
        try {
            socket = new Socket("192.168.101.6", 1020);
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            System.out.println("Connected to the server");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error connecting to server");
        }
    }

    private static void sendData(TextField num1, TextField num2, Choice operation, TextArea res) {
        try {
            double numb1 = Double.parseDouble(num1.getText());
            double numb2 = Double.parseDouble(num2.getText());
            String operationSelected = operation.getSelectedItem(); // Get selected operation

            output.writeUTF(operationSelected); 
            output.writeDouble(numb1); 
            output.writeDouble(numb2); 

            double result = input.readDouble(); // Read result from server
            res.append(numb1 + " " + operationSelected + " " + numb2 + " = " + result + "\n");

        } catch (IOException e) {
            e.printStackTrace();
            showError("Error during communication with server");
        } catch (NumberFormatException e) {
            showError("Please enter valid numbers");
        }
    }

    private static void showError(String message) {
        new MessageDialog(message);
    }

    private static class MessageDialog {
        MessageDialog(String message) {
            Dialog dialog = new Dialog(new Frame(), "Error", true);
            dialog.setSize(200, 100);
            dialog.setLayout(new FlowLayout());
            dialog.add(new Label(message));
            Button ok = new Button("OK");
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            dialog.add(ok);
            dialog.setVisible(true);
        }
    }
}