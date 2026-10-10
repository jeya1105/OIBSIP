
import java.util.ArrayList;
import java.util.List;

public class ExamManager {
    private final List<Question> questions = new ArrayList<>();

    public ExamManager() {
        questions.add(new Question(
            "Which language is used for Android development?",
            new String[]{"Java", "HTML", "SQL", "CSS"}, 0));

        questions.add(new Question(
            "Which keyword creates an object in Java?",
            new String[]{"class", "new", "void", "static"}, 1));

        questions.add(new Question(
            "Which data type stores true or false?",
            new String[]{"int", "char", "boolean", "String"}, 2));

        questions.add(new Question(
            "Which method is the entry point of a Java program?",
            new String[]{"start()", "run()", "main()", "init()"}, 2));

        questions.add(new Question(
            "Which collection does not allow duplicate elements?",
            new String[]{"ArrayList", "HashSet", "LinkedList", "Vector"}, 1));
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int calculateScore(int[] answers) {
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (answers[i] == questions.get(i).getCorrectAnswer()) {
                score++;
            }
        }

        return score;
    }
}
