package ca.bcit.comp2522.bank;

/**
 * Represents a bank account belonging to a bank client.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class BankAccount {
    private final BankClient client;
    private double balanceUsd;
    private final int pin;
    private final String accountNumber;
    private final Date accountOpened;
    private Date accountClosed;

    /**
     * Constructs a new BankAccount object.
     *
     * @param client        the bank client who owns the account
     * @param balanceUsd    the initial balance in USD
     * @param pin           the PIN associated with the account
     * @param accountNumber the account number
     * @param accountOpened the date the account was opened
     * @throws IllegalArgumentException if any input is invalid
     */
    public BankAccount(BankClient client, double balanceUsd, int pin,
                       String accountNumber, Date accountOpened) {
        if (client == null || accountNumber == null ||
                !accountNumber.matches("\\w{6,7}") || accountOpened == null) {
            throw new IllegalArgumentException("Invalid account details provided");
        }
        this.client = client;
        this.balanceUsd = balanceUsd;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.accountOpened = accountOpened;
        this.accountClosed = null;
    }

    /**
     * Deposits money into the account.
     *
     * @param amountUsd the amount to deposit
     */
    public void deposit(double amountUsd) {
        if (amountUsd > 0) {
            balanceUsd += amountUsd;
        }
    }

    /**
     * Withdraws money from the account.
     *
     * @param amountUsd the amount to withdraw
     * @return true if the withdrawal is successful, false otherwise
     */
    public boolean withdraw(double amountUsd) {
        if (amountUsd > 0 && balanceUsd >= amountUsd) {
            balanceUsd -= amountUsd;
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
    public boolean withdraw(double amountUsd, int pinToMatch) {
        if (this.pin == pinToMatch) {
            return withdraw(amountUsd);
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
        if (accountClosed == null) {
            return String.format("%s had $%.2f USD in account #%s opened on %s",
                    client.getName().getFullName(), balanceUsd, accountNumber,
                    accountOpened.getDayOfTheWeek() + " " + accountOpened.getYYYYMMDD());
        } else {
            return String.format(
                    "%s had $%.2f USD in account #%s opened on %s and closed on %s",
                    client.getName().getFullName(), balanceUsd, accountNumber,
                    accountOpened.getDayOfTheWeek() + " " + accountOpened.getYYYYMMDD(),
                    accountClosed.getDayOfTheWeek() + " " + accountClosed.getYYYYMMDD());
        }
    }
}