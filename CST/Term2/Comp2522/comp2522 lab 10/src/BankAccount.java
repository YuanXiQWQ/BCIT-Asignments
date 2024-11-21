/**
 * A class representing a bank account.
 *
 * @author Daniel Wang
 * @author Uppnoor Panesar
 * @author Jack Le
 * @author Jiarui Xing
 * @version 1.0
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
            throw new IllegalArgumentException("Destination account not found");
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
