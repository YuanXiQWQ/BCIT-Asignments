import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a bank.
 *
 * @author Daniel Wang
 * @author Uppnoor Panesar
 * @author Jack Le
 * @author Jiarui Xing
 * @version 1.0
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
     * Adds a new BankAccount to the bank.
     *
     * @param account the BankAccount to add
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
     * Retrieves a BankAccount by its ID.
     *
     * @param accountId the ID of the account to retrieve
     * @return the BankAccount with the specified ID
     */
    public BankAccount retrieveAccount(final String accountId)
    {
        final BankAccount account;
        account = accounts.get(accountId);
        if(account == null)
        {
            throw new IllegalArgumentException("Account not found");
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
