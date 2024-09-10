package ca.bcit.comp2522.bank;

/**
 * Represents a Name with a first and last name. Author: Jiarui Xing
 *
 * @version 1.0
 */
public class Name {
    private final String first;
    private final String last;

    /**
     * Constructs a new Name object.
     *
     * @param first the first name
     * @param last  the last name
     */
    public Name(String first, String last) {
        if (first == null || last == null || first.isBlank() || last.isBlank() ||
                first.length() > 45 || last.length() > 45 ||
                first.toLowerCase().contains("admin") ||
                last.toLowerCase().contains("admin")) {
            throw new IllegalArgumentException("Invalid name provided.");
        }
        this.first = first;
        this.last = last;
    }

    /**
     * Returns the initials of the name.
     *
     * @return the initials
     */
    public String getInitials() {
        return (first.charAt(0) + "." + last.charAt(0) + ".").toUpperCase();
    }

    /**
     * Returns the full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return formatName(first) + " " + formatName(last);
    }

    /**
     * Returns the reverse name.
     *
     * @return the reverse name
     */
    public String getReverseName() {
        return new StringBuilder(last).reverse().toString() + " " +
                new StringBuilder(first).reverse().toString();
    }

    /**
     * Capitalizes the first letter of the name, and lowercases the rest.
     *
     * @param name the name
     * @return the formatted name
     */
    private String formatName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
