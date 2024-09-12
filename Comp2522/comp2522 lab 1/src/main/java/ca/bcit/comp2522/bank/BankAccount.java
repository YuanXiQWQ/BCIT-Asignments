package ca.bcit.comp2522.bank;

/**
 * Represents a bank account belonging to a bank client.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class BankAccount {
    private final BankClient client;
    private final String accountNumber;
    private final Date accountOpened;
    private Date accountClosed;
    private double balanceUsd;
    private final int pin;

    private final static String INFO_CLIENT_NULL = "Client must not be null";
    private final static String INFO_ACCOUNT_NUMBER_NULL_OR_INVALID =
            "Account number must be a 6 or 7 character string";
    private final static String INFO_ACCOUNT_OPENED_NULL =
            "Account opened date must not be null";
    private final static String INFO_WITHDRAWAL_INVALID = "Invalid amount to withdraw";
    private final static String INFO_BANK_ACCOUNT =
            "%s had $%.2f USD in account #%s which he opened on %s";

    /**
     * Constructs a new BankAccount object.
     *
     * @param client        the client that owns the account
     * @param accountNumber the account number
     * @param accountOpened the date the account was opened
     * @param accountClosed the date the account was closed, can be null
     */
    public BankAccount(final BankClient client, final String accountNumber,
                       final Date accountOpened, final Date accountClosed, final int pin)
    {
        if (client == null) {
            throw new IllegalArgumentException(INFO_CLIENT_NULL);
        }
        if (accountNumber == null || !accountNumber.matches("\\w{6,7}")) {
            throw new IllegalArgumentException(INFO_ACCOUNT_NUMBER_NULL_OR_INVALID);
        }
        if (accountOpened == null) {
            throw new IllegalArgumentException(INFO_ACCOUNT_OPENED_NULL);
        }
        this.client = client;
        this.accountNumber = accountNumber;
        this.accountOpened = accountOpened;
        this.accountClosed = accountClosed;
        this.pin = pin;
    }

    /**
     * Deposits money into the account.
     *
     * @param amountUsd the amount to deposit
     */
    public void deposit(final double amountUsd)
    {
        if (amountUsd > 0) {
            this.balanceUsd += amountUsd;
        }
    }

    /**
     * Withdraws money from the account.
     *
     * @param amountUsd the amount to withdraw
     */
    public void withdraw(final double amountUsd)
    {
        if (amountUsd <= 0 || this.balanceUsd < amountUsd) {
            throw new IllegalArgumentException(INFO_WITHDRAWAL_INVALID);
        }
        this.balanceUsd -= amountUsd;
    }

    /**
     * Withdraws money from the account with PIN verification.
     *
     * @param amountUsd  the amount to withdraw
     * @param pinToMatch the PIN to match
     */
    public void withdraw(final double amountUsd, final int pinToMatch)
    {
        if (this.pin == pinToMatch) {
            this.withdraw(amountUsd);
        }
    }

    /**
     * Closes the account by setting the account closed date.
     *
     * @param accountClosed the date the account is closed
     */
    public void closeAccount(final Date accountClosed)
    {
        if (this.accountClosed == null && accountClosed != null) {
            this.accountClosed = accountClosed;
        }
    }

    /**
     * Returns the details of the bank account.
     *
     * @return the account details
     */
    public String getDetails()
    {
        return this.accountClosed == null ? String.format(INFO_BANK_ACCOUNT,
                this.client.getName().getFullName(), this.balanceUsd, this.accountNumber,
                client.formatDate(this.accountOpened)) : String.format(
                INFO_BANK_ACCOUNT + " and closed on %s",
                this.client.getName().getFullName(), this.balanceUsd, this.accountNumber,
                client.formatDate(this.accountOpened),
                client.formatDate(this.accountClosed));
    }
}
