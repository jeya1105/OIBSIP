
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();

        // Sample accounts for testing
        accounts.put("JEYA101",
                new Account("JEYA101", 1234, 10000));

        accounts.put("USER102",
                new Account("USER102", 5678, 5000));
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean validateUser(String userId, int pin) {
        Account account = accounts.get(userId);

        return account != null && account.checkPin(pin);
    }

    public boolean accountExists(String userId) {
        return accounts.containsKey(userId);
    }
}
