
import javax.swing.*;
import java.awt.*;

public class ReservationFrame extends JFrame {

    private ReservationManager manager;

    private JTextField nameField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JTextField dateField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextField pnrField;

    private JComboBox<String> classBox;

    public ReservationFrame(ReservationManager manager) {
        this.manager = manager;

        setTitle("Online Reservation System");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(10, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        nameField = new JTextField();
        trainNumberField = new JTextField();
        trainNameField = new JTextField();
        dateField = new JTextField();
        sourceField = new JTextField();
        destinationField = new JTextField();
        pnrField = new JTextField();

        classBox = new JComboBox<>(
                new String[]{"Sleeper", "AC 3 Tier", "AC 2 Tier", "First Class"}
        );

        panel.add(new JLabel("Passenger Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Train Number:"));
        panel.add(trainNumberField);

        panel.add(new JLabel("Train Name:"));
        panel.add(trainNameField);

        panel.add(new JLabel("Class:"));
        panel.add(classBox);

        panel.add(new JLabel("Journey Date (DD-MM-YYYY):"));
        panel.add(dateField);

        panel.add(new JLabel("Source:"));
        panel.add(sourceField);

        panel.add(new JLabel("Destination:"));
        panel.add(destinationField);

        JButton bookButton = new JButton("Book Ticket");
        JButton cancelButton = new JButton("Cancel Ticket");
        JButton viewButton = new JButton("View Reservation");

        panel.add(bookButton);
        panel.add(cancelButton);

        panel.add(new JLabel("Enter PNR:"));
        panel.add(pnrField);

        panel.add(viewButton);
        panel.add(new JLabel(""));

        add(panel);

        bookButton.addActionListener(e -> bookTicket());
        cancelButton.addActionListener(e -> cancelTicket());
        viewButton.addActionListener(e -> viewReservation());
    }

    private void bookTicket() {
        String name = nameField.getText().trim();
        String trainNumber = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String date = dateField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String classType = (String) classBox.getSelectedItem();

        if (name.isEmpty() || trainNumber.isEmpty()
                || trainName.isEmpty() || date.isEmpty()
                || source.isEmpty() || destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this, "Please fill in all reservation details!");
            return;
        }

        if (source.equalsIgnoreCase(destination)) {
            JOptionPane.showMessageDialog(
                    this, "Source and destination must be different!");
            return;
        }

        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            JOptionPane.showMessageDialog(
                    this, "Enter date in DD-MM-YYYY format!");
            return;
        }

        Reservation reservation = manager.bookTicket(
                name, trainNumber, trainName, classType,
                date, source, destination
        );

        JOptionPane.showMessageDialog(
                this,
                "Reservation successful!\nYour PNR: "
                        + reservation.getPnr(),
                "Booking Confirmation",
                JOptionPane.INFORMATION_MESSAGE
        );

        reservation.displayDetails();
    }

    private void cancelTicket() {
        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter the PNR number!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel this ticket?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (manager.cancelTicket(pnr)) {
                JOptionPane.showMessageDialog(
                        this, "Ticket cancelled successfully!");
            } else {
                JOptionPane.showMessageDialog(
                        this, "Invalid PNR or ticket already cancelled!");
            }
        }
    }

    private void viewReservation() {
        String pnr = pnrField.getText().trim();
        Reservation reservation = manager.getReservation(pnr);

        if (reservation == null) {
            JOptionPane.showMessageDialog(
                    this, "Reservation not found!");
            return;
        }

        String details =
                "PNR: " + reservation.getPnr()
                + "\nPassenger: " + reservation.getPassengerName()
                + "\nTrain: " + reservation.getTrainName()
                + "\nTrain Number: " + reservation.getTrainNumber()
                + "\nClass: " + reservation.getClassType()
                + "\nJourney Date: " + reservation.getJourneyDate()
                + "\nSource: " + reservation.getSource()
                + "\nDestination: " + reservation.getDestination()
                + "\nStatus: " + (reservation.isCancelled()
                        ? "Cancelled" : "Confirmed");

        JOptionPane.showMessageDialog(
                this, details, "Reservation Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
