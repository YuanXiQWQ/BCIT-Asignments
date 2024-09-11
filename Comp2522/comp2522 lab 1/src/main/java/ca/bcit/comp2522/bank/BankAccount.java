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

    /**
     * Constructs a new BankAccount object.
     *
     * @param client        the client that owns the account
     * @param accountNumber the account number
     * @param accountOpened the date the account was opened
     * @param accountClosed the date the account was closed, can be null
     */
    public BankAccount(BankClient client, String accountNumber, Date accountOpened,
                       Date accountClosed, int pin) {
        if (client == null) {
            throw new IllegalArgumentException("Client must not be null");
        }
        if (accountNumber == null || !accountNumber.matches("\\w{6,7}")) {
            throw new IllegalArgumentException(
                    "Account number must be a 6 or 7 character string");
        }
        if (accountOpened == null) {
            throw new IllegalArgumentException("Account opened date must not be null");
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
    public void deposit(double amountUsd) {
        if (amountUsd > 0) {
            this.balanceUsd += amountUsd;
        }
    }

    /**
     * Withdraws money from the account.
     *
     * @param amountUsd the amount to withdraw
     * @return true if the withdrawal is successful, false otherwise
     */
    public boolean withdraw(final double amountUsd) {
        if (amountUsd <= 0 || this.balanceUsd < amountUsd) {
            return false;
        }
        this.balanceUsd -= amountUsd;
        return true;
    }

    /**
     * Withdraws money from the account with PIN verification.
     *
     * @param amountUsd  the amount to withdraw
     * @param pinToMatch the PIN to match
     * @return true if the withdrawal is successful and the PIN matches, false otherwise
     */
    public boolean withdraw(final double amountUsd, final int pinToMatch) {
        return this.pin == pinToMatch && this.withdraw(amountUsd);
    }

    /**
     * Closes the account by setting the account closed date.
     *
     * @param accountClosed the date the account is closed
     */
    public void closeAccount(Date accountClosed) {
        if (this.accountClosed == null && accountClosed != null) {
            this.accountClosed = accountClosed;
        }
    }

    /**
     * Returns the details of the bank account.
     *
     * @return the account details
     */
    public String getDetails() {
        final String template =
                "%s had $%.2f USD in account #%s which he opened on %s";
        return this.accountClosed == null
               ? String.format(template,
                this.client.getName().getFullName(),
                this.balanceUsd,
                this.accountNumber,
                client.formatDate(this.accountOpened))
               : String.format(template + " and closed on %s",
                       this.client.getName().getFullName(),
                       this.balanceUsd,
                       this.accountNumber,
                       client.formatDate(this.accountOpened),
                       client.formatDate(this.accountClosed));
    }
}
