package ca.bcit.comp1510.lab06;

/**
 * This is a Lab6 Assignment, to represent a person's name.
 * This class is not a complete program, it has not had the main method.
 *
 * @author Jiarui Xing
 * @version 2024.2.22
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
        this.firstName = checkName(firstName);
        this.middleName = checkName(middleName);
        this.lastName = checkName(lastName);
    }

    /**
     * Check if the name is valid
     *
     * @param name first name/ middle name/ last name
     * @return itself/ null
     */
    private String checkName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Jerry";
        }
        return name.substring(0, 1).toUpperCase()
                + name.substring(1).toLowerCase();
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
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = checkName(firstName);
        }
    }

    public void setMiddleName(String middleName) {
        if (middleName != null && !middleName.trim().isEmpty()) {
            this.middleName = checkName(middleName);
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = checkName(lastName);
        }
    }

    /**
     * A method that accepts no parameters and returns the length of the name.
     *
     * @return the length of the full name
     */
    public int getFullNameLength() {
        return firstName.length() + middleName.length() + lastName.length();
    }

    /**
     * A method that accepts no parameters and returns a
     * String consisting of the three initials IN UPPERCASE.
     *
     * @return the first letter of each name
     */
    public String getFirstLetters() {
        return ("" + firstName.charAt(0) + middleName.charAt(0) + lastName.charAt(0)).toUpperCase();
    }

    /**
     * A method that accepts an integer n and
     * returns the nth character in the full three-part name.
     *
     * @return the nth character in the full three-part name
     */
    public char getNthCharacter(int n) {
        String fullName = firstName + middleName + lastName;
        if (n > 0 && n <= fullName.length()) {
            return fullName.charAt(n - 1);
        } else {
            return '@';
        }
    }

    /**
     * A method that accepts no parameters and returns a
     * String consisting of the last name followed by a comma
     * followed by the first name followed by the middle name.
     *
     * @return lastname, firstname, middle name
     */
    public String getFullName() {
        return lastName + ", " + firstName + ", " + middleName;
    }

    /**
     * A method that accepts a String and returns true if the String is equal to the first name
     *
     * @return boolean
     */
    public boolean isSameName(String input) {
        return input.equals(firstName);
    }

    /**
     * A method that accepts a Name object and returns true if the three parts of the
     * name object are the same as the three parts of “this” Name object.
     *
     * @param otherName the name object
     * @return boolean
     */
    public boolean isSameNameObject(Name otherName) {
        return otherName.firstName.equals(firstName)
                && otherName.middleName.equals(middleName)
                && otherName.lastName.equals(lastName);
    }


    /**
     * Returns full name.
     *
     * @return a full name with first, middle and last names
     */
    @Override
    public String toString() {
        return firstName + " " + middleName + " " + lastName;
    }
}
