package ca.bcit.comp2522.bank;

/**
 * Represents a bank client, which is a person with a client ID and signup date. Extends
 * the Person class.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class BankClient extends Person {
    private final Date signupDate;
    private final String clientID;

    private final static String BANK_CLIENT_INFO = "%s client #%s (%s) joined the bank" +
            " on %s";

    /**
     * Constructs a new BankClient object.
     *
     * @param clientID   the client's ID
     * @param signupDate the date the client signed up with the bank
     * @throws IllegalArgumentException if the clientID is not valid
     */
    public BankClient(Name name, Date birthDate, Date deathDate, String clientID,
                      Date signupDate) {
        super(name, birthDate, deathDate);
        if (clientID == null || !clientID.matches("\\w{6,7}")) {
            throw new IllegalArgumentException(
                    "Client ID must be a non-null 6 or 7 character alphanumeric string.");
        }
        if (signupDate == null) {
            throw new IllegalArgumentException("Signup date cannot be null.");
        }
        this.clientID = clientID;
        this.signupDate = signupDate;
    }

    /**
     * Returns the details of the bank client.
     *
     * @return the client's details
     */
    @Override
    public String getDetails() {
        return String.format(BANK_CLIENT_INFO,
                getName().getFullName(),
                this.clientID,
                isAlive() ? "alive" : "died " + formatDate(getDeathDate()),
                formatDate(this.signupDate));
    }
}