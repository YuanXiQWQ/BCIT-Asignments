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

    private final static String INFO_CLIENT_ID_INVALID =
            "Client ID must be a non-null 6 or 7 character alphanumeric string.";
    private final static String INFO_SIGNUP_DATE_NULL = "Signup date cannot be null.";
    private final static String INFO_BANK_CLIENT = "%s client #%s (%s) joined the bank" +
            " on %s";

    /**
     * Constructs a new BankClient object.
     *
     * @param clientId   the client's ID
     * @param signupDate the date the client signed up with the bank
     * @throws IllegalArgumentException if the clientID is not valid
     */
    public BankClient(final Name name,
                      final Date birthDate,
                      final Date deathDate,
                      final String clientId,
                      final Date signupDate)
    {
        super(name, birthDate, deathDate);
        if (clientId == null || !clientId.matches("\\w{6,7}")) {
            throw new IllegalArgumentException(INFO_CLIENT_ID_INVALID);
        }
        if (signupDate == null) {
            throw new IllegalArgumentException(INFO_SIGNUP_DATE_NULL);
        }
        this.clientID = clientId;
        this.signupDate = signupDate;
    }

    /**
     * Returns the details of the bank client.
     *
     * @return the client's details
     */
    @Override
    public String getDetails()
    {
        return String.format(INFO_BANK_CLIENT,
                getName().getFullName(),
                this.clientID,
                isAlive() ? "alive" : "died " + formatDate(getDeathDate()),
                formatDate(this.signupDate));
    }
}