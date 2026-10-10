
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/online_reservation_db";
        String user = "root";
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            throw new Exception("DB_PASSWORD environment variable is missing.");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
