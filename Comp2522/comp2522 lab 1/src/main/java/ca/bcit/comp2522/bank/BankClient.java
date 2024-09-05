package ca.bcit.comp2522.bank;

/**
 * Represents a bank client, which is a person with a client ID and signup date. Extends
 * the Person class.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class BankClient extends Person {
    private final String clientID;
    private final Date signupDate;

    /**
     * Constructs a new BankClient object.
     *
     * @param name       the name
     * @param birthDate  the birthdate
     * @param deathDate  the death date
     * @param clientID   the client's ID
     * @param signupDate the date the client signed up with the bank
     * @throws IllegalArgumentException if the clientID is not valid
     */
    public BankClient(Name name, Date birthDate, Date deathDate, String clientID,
                      Date signupDate) {
        super(name, birthDate, deathDate);
        if (clientID == null || !clientID.matches("\\w{6,7}")) {
            throw new IllegalArgumentException(
                    "Client ID must be a 6 or 7 character string");
        }
        if (signupDate == null) {
            throw new IllegalArgumentException("Signup date must not be null");
        }
        this.clientID = clientID;
        this.signupDate = signupDate;
    }

    /**
     * Returns the client's ID.
     *
     * @return the client ID
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Returns the signup date.
     *
     * @return the signup date
     */
    public Date getSignupDate() {
        return signupDate;
    }

    /**
     * Returns the details of the bank client.
     *
     * @return the client's details
     */
    @Override
    public String getDetails() {
        String baseDetails = super.getDetails();
        return String.format("%s client #%s joined the bank on %s",
                getName().getFullName(), clientID,
                signupDate.getDayOfTheWeek() + " " + signupDate.getYYYYMMDD());
    }
}