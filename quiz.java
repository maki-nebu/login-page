import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator {
    public static void main(String[] args) {
        Frame frame = new Frame("Simple Calculator");
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        TextField num1Field = new TextField(10);
        TextField num2Field = new TextField(10);
        Choice operationChoice = new Choice();
        operationChoice.add("+");
        operationChoice.add("-");
        operationChoice.add("*");
        operationChoice.add("/");
        Button calculateButton = new Button("Calculate");
        Label resultLabel = new Label("Result:");

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = Double.parseDouble(num1Field.getText());
                    double num2 = Double.parseDouble(num2Field.getText());
                    String operation = operationChoice.getSelectedItem();
                    double result = 0;

                    switch (operation) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            result = (num2 != 0) ? num1 / num2 : Double.NaN;
                            break;
                    }
                    resultLabel.setText("Result: " + (Double.isNaN(result) ? "Cannot divide by zero" : result));
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Result: Invalid input");
                }
            }
        });

        frame.add(new Label("Number 1:"));
        frame.add(num1Field);
        frame.add(new Label("Number 2:"));
        frame.add(num2Field);
        frame.add(operationChoice);
        frame.add(calculateButton);
        frame.add(resultLabel);

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
            }
        });
    }
}