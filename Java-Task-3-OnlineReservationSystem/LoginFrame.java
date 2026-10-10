
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private ReservationManager manager;

    public LoginFrame(ReservationManager manager) {
        this.manager = manager;

        setTitle("Online Reservation System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel userLabel = new JLabel("User ID:");
        JTextField userField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(clearButton);

        add(panel);

        loginButton.addActionListener(e -> {
            String user = userField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (user.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(
                        this, "Login successful!");

                dispose();
                new ReservationFrame(manager).setVisible(true);

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid User ID or Password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        clearButton.addActionListener(e -> {
            userField.setText("");
            passwordField.setText("");
        });
    }
}
