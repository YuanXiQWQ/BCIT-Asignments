/**
 * <p>This class provides functionalities to manage a bank account, including depositing,
 * withdrawing, transferring funds, and querying the account balance.
 * </p>
 *
 * <p>All methods that can throw runtime exceptions have been documented using
 * {@code @throws} tags to inform users about potential errors.</p>
 *
 * <p>Example usage:
 * <pre>{@code
 * BankAccount account = new BankAccount("12345", 1000.0);
 * account.deposit(500.0);
 * account.withdraw(200.0);
 * }</pre>
 * </p>
 *
 * <p>Note: This class does not handle thread safety. If used in a multithreaded
 * environment,
 * external synchronization is required.</p>
 *
 * @author Daniel Wang
 * @author Uppnoor Panesar
 * @author Jack Le
 * @author Jiarui Xing
 * @version 1.2
 */
public class BankAccount {
    private static final double MIN_BALANCE = 0;

    private final String accountId;
    private double balanceUsd;

    /**
     * Constructs a BankAccount with the given ID and initial balance.
     *
     * @param accountId      the unique identifier for the account
     * @param initialBalance the starting balance of the account
     * @throws IllegalArgumentException if {@code accountId} is null or empty, or if
     *                                  {@code initialBalance} is negative
     */
    public BankAccount(final String accountId, final double initialBalance)
    {
        if(accountId == null || accountId.isEmpty())
        {
            throw new IllegalArgumentException("Account ID cannot be null or empty");
        }
        if(initialBalance < MIN_BALANCE)
        {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountId = accountId;
        this.balanceUsd = initialBalance;
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if {@code amount} is not positive
     */
    public void deposit(final double amount)
    {
        if(amount <= MIN_BALANCE)
        {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balanceUsd += amount;
    }

    /**
     * Withdraws the specified amount from the account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if {@code amount} is not positive or if the
     *                                  withdrawal amount exceeds the current balance
     */
    public void withdraw(final double amount)
    {
        if(amount <= MIN_BALANCE)
        {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if(balanceUsd < amount)
        {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balanceUsd -= amount;
    }

    /**
     * Transfers the specified amount to another BankAccount.
     *
     * @param destination the account to transfer funds to
     * @param sourceId    the source account ID
     * @param amount      the amount to transfer
     * @throws IllegalArgumentException if the {@code sourceId} does not match this
     *                                  account's ID, if {@code destination} is null, if
     *                                  {@code amount} is not positive, or if the
     *                                  withdrawal amount exceeds the current balance
     */
    public void transferToBank(final BankAccount destination,
                               final String sourceId,
                               final double amount)
    {
        if(!this.accountId.equals(sourceId))
        {
            throw new IllegalArgumentException("Source account ID does not match");
        }
        if(destination == null)
        {
            throw new IllegalArgumentException("Destination account cannot be null");
        }
        this.withdraw(amount);
        destination.deposit(amount);
    }

    /**
     * Returns the current balance of the account.
     *
     * @return the balance in USD
     */
    public double getBalanceUsd()
    {
        return balanceUsd;
    }

    /**
     * Returns the account ID.
     *
     * @return the account ID
     */
    public String getAccountId()
    {
        return accountId;
    }
}
