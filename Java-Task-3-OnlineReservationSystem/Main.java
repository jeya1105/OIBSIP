
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservationManager manager = new ReservationManager();
            LoginFrame login = new LoginFrame(manager);
            login.setVisible(true);
        });
    }
}
