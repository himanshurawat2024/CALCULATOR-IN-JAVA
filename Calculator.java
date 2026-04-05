import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private double result = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculator() {
        setTitle("Modern Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        // Buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));
        String[] buttons = {
            "Clear", "⌫", "%", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "±", "0", ".", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("[0-9.]")) {
            if (startNewNumber) {
                display.setText(cmd);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + cmd);
            }
        } else if (cmd.equals("Clear")) {
            display.setText("0");
            result = 0;
            operator = "";
            startNewNumber = true;
        } else if (cmd.equals("⌫")) {
            String text = display.getText();
            if (text.length() > 1) display.setText(text.substring(0, text.length() - 1));
            else display.setText("0");
        } else if (cmd.equals("±")) {
            double value = Double.parseDouble(display.getText());
            display.setText("" + (-value));
        } else if (cmd.equals("%")) {
            double value = Double.parseDouble(display.getText());
            display.setText("" + (value / 100));
        } else if (cmd.matches("[+\\-*/]")) {
            calculate(Double.parseDouble(display.getText()));
            operator = cmd;
            startNewNumber = true;
        } else if (cmd.equals("=")) {
            calculate(Double.parseDouble(display.getText()));
            operator = "";
            startNewNumber = true;
        }
    }

    private void calculate(double x) {
        switch (operator) {
            case "+": result += x; break;
            case "-": result -= x; break;
            case "*": result *= x; break;
            case "/": result = (x != 0) ? result / x : 0; break;
            case "": result = x; break;
        }
        display.setText("" + result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}