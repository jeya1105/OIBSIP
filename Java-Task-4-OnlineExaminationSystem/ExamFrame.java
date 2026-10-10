
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExamFrame extends JFrame {

    private final String username;
    private final ExamManager manager;
    private final List<Question> questions;
    private final int[] answers;

    private int currentQuestion = 0;
    private int timeLeft = 120;
    private final JLabel questionLabel;
    private final JLabel timerLabel;
    private final JRadioButton[] optionButtons;
    private final ButtonGroup optionGroup;
    private final Timer timer;

    public ExamFrame(String username) {
        this.username = username;
        manager = new ExamManager();
        questions = manager.getQuestions();
        answers = new int[questions.size()];
        Arrays.fill(answers, -1);

        setTitle("Online Examination System");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        timerLabel = new JLabel("Time left: 02:00");
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 15));
        add(timerLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(questionLabel);

        optionButtons = new JRadioButton[4];
        optionGroup = new ButtonGroup();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            centerPanel.add(optionButtons[i]);

            final int selectedOption = i;
            optionButtons[i].addActionListener(e -> {
                answers[currentQuestion] = selectedOption;
            });
        }

        add(centerPanel, BorderLayout.CENTER);

        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton submitButton = new JButton("Submit Exam");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        previousButton.addActionListener(e -> {
            if (currentQuestion > 0) {
                currentQuestion--;
                showQuestion();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                showQuestion();
            }
        });

        submitButton.addActionListener(e -> submitExam());

        showQuestion();

        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLeft--;
                    timerLabel.setText(String.format(
                        "Time left: %02d:%02d",
                        timeLeft / 60, timeLeft % 60
                    ));

                    if (timeLeft <= 0) {
                        timer.cancel();
                        submitExam();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void showQuestion() {
        Question q = questions.get(currentQuestion);
        questionLabel.setText(
            "Question " + (currentQuestion + 1) + ": " + q.getQuestion()
        );

        optionGroup.clearSelection();

        String[] options = q.getOptions();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setSelected(answers[currentQuestion] == i);
        }
    }

    private void submitExam() {
        if (timer != null) {
            timer.cancel();
        }

        int score = manager.calculateScore(answers);

        dispose();
        new ResultFrame(username, score, questions.size()).setVisible(true);
    }
}
