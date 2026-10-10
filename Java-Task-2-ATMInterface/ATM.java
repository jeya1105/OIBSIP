
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Account account;
    private Scanner sc;
    private List<Transaction> transactions;

    public ATM(Bank bank, Account account, Scanner sc) {
        this.bank = bank;
        this.account = account;
        this.sc = sc;
        this.transactions = new ArrayList<>();
    }

    public void start() {
        int choice;

        do {
            System.out.println("\n========== ATM MENU ==========");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    transferMoney();
                    break;

                case 5:
                    showTransactions();
                    break;

                case 6:
                    System.out.println("Thank you for using our ATM!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 6);
    }

    private void checkBalance() {
        System.out.printf("Available Balance: Rs. %.2f%n",
                account.getBalance());
    }

    private void depositMoney() {
        System.out.print("Enter deposit amount: Rs. ");
        double amount = readAmount();

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
            return;
        }

        account.deposit(amount);
        transactions.add(new Transaction("Deposit", amount));

        System.out.printf("Current Balance: Rs. %.2f%n",
                account.getBalance());
    }

    private void withdrawMoney() {
        System.out.print("Enter withdrawal amount: Rs. ");
        double amount = readAmount();

        if (account.withdraw(amount)) {
            transactions.add(new Transaction("Withdrawal", amount));
            System.out.printf("Current Balance: Rs. %.2f%n",
                    account.getBalance());
        }
    }

    private void transferMoney() {
        System.out.print("Enter receiver User ID: ");
        String receiverId = sc.next();

        if (receiverId.equals(account.getUserId())) {
            System.out.println("You cannot transfer to your own account.");
            return;
        }

        if (!bank.accountExists(receiverId)) {
            System.out.println("Receiver account not found!");
            return;
        }

        System.out.print("Enter transfer amount: Rs. ");
        double amount = readAmount();

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
            return;
        }

        if (account.transfer(amount)) {
            Account receiver = bank.getAccount(receiverId);
            receiver.deposit(amount);

            transactions.add(new Transaction(
                    "Transfer to " + receiverId, amount));

            System.out.println("Money transferred successfully!");
            System.out.printf("Current Balance: Rs. %.2f%n",
                    account.getBalance());
        }
    }

    private void showTransactions() {
        System.out.println("\n====== TRANSACTION HISTORY ======");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }
    }

    private int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }

        return sc.nextInt();
    }

    private double readAmount() {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid amount: Rs. ");
            sc.next();
        }

        return sc.nextDouble();
    }
}
