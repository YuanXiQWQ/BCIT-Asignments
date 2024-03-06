package ca.bcit.comp1510.lab04;

/**
 * This is a Lab4 Assignment, to represent a person's name.
 * This class is not a complete program, it has not had the main method.
 *
 * @author Jiarui Xing
 * @version 2024.2.9
 */
public class Name {
    /**
     * To save the first name
     */
    private String firstName;
    /**
     * To save the middle name
     */
    private String middleName;
    /**
     * To save the last name
     */
    private String lastName;

    /**
     * Accepts three parameters for the first, middle, and last names in order.
     *
     * @param firstName  the first name
     * @param middleName the middle name
     * @param lastName   the last name
     */
    public Name(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    //Accessors
    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    //Mutators
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns full name.
     *
     * @return a full name with first, middle and last names
     */
    //Actually idk what is "@Override", but checkstyle says I should add it
    @Override
    public String toString() {
        return firstName + " " + middleName + " " + lastName;
    }
}
