
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class ReservationManager {

    private final Random random = new Random();

    private String generatePNR() {
        String pnr;
        String sql = "SELECT pnr FROM reservations WHERE pnr = ?";

        do {
            pnr = String.format("%06d", random.nextInt(1_000_000));

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, pnr);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return pnr;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Could not generate PNR.", e);
            }
        } while (true);
    }

    public Reservation bookTicket(
            String passengerName,
            String trainNumber,
            String trainName,
            String travelClass,
            String journeyDate,
            String source,
            String destination) {

        Date sqlDate;

        try {
            LocalDate date = LocalDate.parse(
                    journeyDate,
                    DateTimeFormatter.ofPattern("dd-MM-uuuu")
            );
            sqlDate = Date.valueOf(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Enter journey date in DD-MM-YYYY format."
            );
        }

        if (!sqlDate.toLocalDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Journey date must be in the future."
            );
        }

        String pnr = generatePNR();

        String sql = """
                INSERT INTO reservations
                (pnr, passenger_name, train_number, train_name,
                 travel_class, journey_date, source_station,
                 destination_station, status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'BOOKED')
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);
            ps.setString(2, passengerName);
            ps.setString(3, trainNumber);
            ps.setString(4, trainName);
            ps.setString(5, travelClass);
            ps.setDate(6, sqlDate);
            ps.setString(7, source);
            ps.setString(8, destination);

            ps.executeUpdate();

            return new Reservation(
                    pnr, passengerName, trainNumber, trainName,
                    travelClass, journeyDate, source, destination
            );

        } catch (SQLException e) {
            throw new RuntimeException("Could not save reservation.", e);
        }
    }

    public Reservation getReservation(String pnr) {
        String sql = "SELECT * FROM reservations WHERE pnr = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getString("pnr"),
                            rs.getString("passenger_name"),
                            rs.getString("train_number"),
                            rs.getString("train_name"),
                            rs.getString("travel_class"),
                            rs.getDate("journey_date")
                                    .toLocalDate()
                                    .format(DateTimeFormatter.ofPattern("dd-MM-uuuu")),
                            rs.getString("source_station"),
                            rs.getString("destination_station")
                    );

                    if ("CANCELLED".equals(rs.getString("status"))) {
                        reservation.cancelReservation();
                    }

                    return reservation;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not retrieve reservation.", e);
        }

        return null;
    }

    public boolean cancelTicket(String pnr) {
        String sql = """
                UPDATE reservations
                SET status = 'CANCELLED'
                WHERE pnr = ? AND status = 'BOOKED'
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pnr);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Could not cancel reservation.", e);
        }
    }

    public void showReservation(String pnr) {
        Reservation reservation = getReservation(pnr);

        if (reservation == null) {
            System.out.println("Reservation not found!");
        } else {
            reservation.displayDetails();
        }
    }
}
