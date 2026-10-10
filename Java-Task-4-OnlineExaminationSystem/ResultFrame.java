
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResultFrame extends JFrame {

    public ResultFrame(String username, int score, int totalQuestions) {
        setTitle("Examination Result");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel title = new JLabel("Exam Result", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel student = new JLabel(
            "Student: " + username, SwingConstants.CENTER
        );

        JLabel result = new JLabel(
            "Score: " + score + " / " + totalQuestions,
            SwingConstants.CENTER
        );

        JLabel message = new JLabel(
            score >= totalQuestions / 2.0
                ? "Good job!" : "Keep practicing!",
            SwingConstants.CENTER
        );

        JLabel status = new JLabel("Saving result...", SwingConstants.CENTER);

        add(title);
        add(student);
        add(result);
        add(message);
        add(status);

        try {
            String sql = "INSERT INTO exam_results "
                       + "(student_name, score, total_questions) "
                       + "VALUES (?, ?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setInt(2, score);
                ps.setInt(3, totalQuestions);
                ps.executeUpdate();
            }

            status.setText("Result saved to MySQL!");
        } catch (Exception e) {
            status.setText("Database save failed!");
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
