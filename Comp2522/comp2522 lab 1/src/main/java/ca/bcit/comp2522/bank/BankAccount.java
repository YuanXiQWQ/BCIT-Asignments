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
        if (client == null || accountNumber == null ||
                !accountNumber.matches("\\w{6,7}") || accountOpened == null) {
            throw new IllegalArgumentException("Invalid account details provided");
        }
        this.client = client;
        this.accountNumber = accountNumber;
        this.accountOpened = accountOpened;
        this.accountClosed = accountClosed;
        this.accountClosed = null;
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
        if (amountUsd > 0 && this.balanceUsd >= amountUsd) {
            this.balanceUsd -= amountUsd;
            return true;
        }
        return false;
    }

    /**
     * Withdraws money from the account with PIN verification.
     *
     * @param amountUsd  the amount to withdraw
     * @param pinToMatch the PIN to match
     * @return true if the withdrawal is successful and the PIN matches, false otherwise
     */
    public boolean withdraw(final double amountUsd,final int pinToMatch) {
        if (this.pin == pinToMatch) {
            return this.withdraw(amountUsd);
        }
        return false;
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
        return (this.accountClosed == null)
               ? String.format(
                "%s had $%.2f USD in account" + " #%s which he opened on %s",
                this.client.getName().getFullName(), this.balanceUsd, this.accountNumber,
                this.accountOpened.getDayOfTheWeek() + ", " + this.accountOpened.getYYYYMMDD())
               : String.format(
                       "%s had $%.2f USD in account #%s which " + "he opened on %s and " +
                               "closed on %s",
                       this.client.getName().getFullName(), this.balanceUsd, this.accountNumber,
                       this.accountOpened.getDayOfTheWeek() + ", " +
                               this.accountOpened.getYYYYMMDD(),
                       this.accountClosed.getDayOfTheWeek() + ", " +
                               this.accountClosed.getYYYYMMDD());
    }
}