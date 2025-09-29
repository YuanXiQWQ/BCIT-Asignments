import java.util.HashMap;
import java.util.Map;

/**
 * <p>This class manages a collection of {@link BankAccount} objects, providing
 * functionalities
 * to add new accounts, retrieve existing accounts by their unique IDs, and calculate the
 * total balance across all accounts.</p>
 *
 * <p>All methods that can throw runtime exceptions have been documented using
 * {@code @throws} tags to inform users about potential errors.</p>
 *
 * <p>Example usage:
 * <pre>{@code
 * Bank bank = new Bank();
 * BankAccount account1 = new BankAccount("12345", 1000.0);
 * BankAccount account2 = new BankAccount("67890", 500.0);
 * bank.addAccount(account1);
 * bank.addAccount(account2);
 * double total = bank.totalBalanceUsd(); // total = 1500.0
 * BankAccount retrieved = bank.retrieveAccount("12345");
 * }</pre>
 * </p>
 *
 * <p>Note: This class does not handle thread safety. If used in a multithreaded
 * environment,
 * external synchronisation is required.</p>
 *
 * @author Daniel Wang
 * @author Uppnoor Panesar
 * @author Jack Le
 * @author Jiarui Xing
 * @version 1.2
 */
public class Bank {
    private final Map<String, BankAccount> accounts;

    /**
     * Constructs a new Bank with no accounts.
     */
    public Bank()
    {
        accounts = new HashMap<>();
    }

    /**
     * Adds a new {@link BankAccount} to the bank.
     *
     * @param account the {@code BankAccount} to add
     * @throws IllegalArgumentException if {@code account} is {@code null} or if an
     *                                  account with the same ID already exists in the
     *                                  bank
     */
    public void addAccount(final BankAccount account)
    {
        if(account == null)
        {
            throw new IllegalArgumentException("Account cannot be null");
        }
        final String accountId = account.getAccountId();
        if(accounts.containsKey(accountId))
        {
            throw new IllegalArgumentException(
                    "Account with ID " + accountId + " already exists");
        }
        accounts.put(accountId, account);
    }

    /**
     * Retrieves a {@link BankAccount} by its ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the {@code BankAccount} with the specified ID
     *
     * @throws IllegalArgumentException if no account with the specified ID is found
     */
    public BankAccount retrieveAccount(final String accountId)
    {
        final BankAccount account = accounts.get(accountId);
        if(account == null)
        {
            throw new IllegalArgumentException(
                    "Account not found");
        }
        return account;
    }

    /**
     * Calculates the total balance of all accounts in the bank.
     *
     * @return the total balance in USD
     */
    public double totalBalanceUsd()
    {
        return accounts.values()
                .stream()
                .mapToDouble(BankAccount::getBalanceUsd)
                .sum();
    }
}
