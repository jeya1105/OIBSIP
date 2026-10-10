
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        int attempts = 0;
        Account account = null;

        System.out.println("================================");
        System.out.println("       WELCOME TO ATM");
        System.out.println("================================");

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = sc.next();

            System.out.print("Enter PIN: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid PIN! Enter numbers only.");
                sc.next();
                attempts++;
                continue;
            }

            int pin = sc.nextInt();

            if (bank.validateUser(userId, pin)) {
                account = bank.getAccount(userId);
                System.out.println("\nLogin successful!");
                break;
            } else {
                attempts++;
                System.out.println("Invalid User ID or PIN!");

                if (attempts < 3) {
                    System.out.println(
                            "Attempts remaining: " + (3 - attempts));
                }
            }
        }

        if (account == null) {
            System.out.println("\nToo many incorrect attempts.");
            System.out.println("Access denied!");
        } else {
            ATM atm = new ATM(bank, account, sc);
            atm.start();
        }

        sc.close();
    }
}
