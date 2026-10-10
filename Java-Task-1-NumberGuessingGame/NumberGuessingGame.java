
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int roundsPlayed = 0;
        int roundsWon = 0;
        int totalAttempts = 0;
        String playAgain;

        System.out.println("================================");
        System.out.println("     NUMBER GUESSING GAME");
        System.out.println("================================");

        do {
            int number = random.nextInt(100) + 1;
            int maxAttempts = 7;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nI have selected a number between 1 and 100.");
            System.out.println("You have 7 attempts to guess it!");

            while (attempts < maxAttempts) {

                System.out.print("\nEnter your guess: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next();
                    continue;
                }

                int guess = sc.nextInt();

                if (guess < 1 || guess > 100) {
                    System.out.println("Enter a number between 1 and 100.");
                    continue;
                }

                attempts++;
                totalAttempts++;

                if (guess == number) {
                    System.out.println("Correct! You guessed the number!");
                    System.out.println("Attempts used: " + attempts);
                    guessedCorrectly = true;
                    roundsWon++;
                    break;
                } else if (guess < number) {
                    System.out.println("Too Low! Try a higher number.");
                } else {
                    System.out.println("Too High! Try a lower number.");
                }

                System.out.println("Attempts remaining: "
                        + (maxAttempts - attempts));
            }

            roundsPlayed++;

            if (!guessedCorrectly) {
                System.out.println("\nYou have used all 7 attempts.");
                System.out.println("The correct number was: " + number);
            }

            System.out.print("\nDo you want to play again? (yes/no): ");
            playAgain = sc.next();

        } while (playAgain.equalsIgnoreCase("yes")
                || playAgain.equalsIgnoreCase("y"));

        System.out.println("\n================================");
        System.out.println("         FINAL SCORE");
        System.out.println("================================");
        System.out.println("Rounds played: " + roundsPlayed);
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Rounds lost: " + (roundsPlayed - roundsWon));
        System.out.println("Total valid guesses: " + totalAttempts);
        System.out.println("================================");
        System.out.println("Thank you for playing!");

        sc.close();
    }
}
