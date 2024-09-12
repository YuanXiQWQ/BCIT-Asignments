package ca.bcit.comp2522.bank;

/**
 * Represents a Name with a first and last name. Author: Jiarui Xing
 *
 * @author Jiarui Xing
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
    public Name(final String first, final String last) {
        if (first == null || last == null || first.isBlank() || last.isBlank() ||
                first.length() > 45 || last.length() > 45 ||
                first.toLowerCase().contains("admin") ||
                last.toLowerCase().contains("admin")) {
            throw new IllegalArgumentException("Invalid first or last name provided.");
        }
        this.first = first.trim();
        this.last = last.trim();
    }

    /**
     * Returns the initials of the name.
     *
     * @return the initials
     */
    public String getInitials() {
        return String.format("%c.%c.",
                Character.toUpperCase(this.first.charAt(0)),
                Character.toUpperCase(this.last.charAt(0)));
    }

    /**
     * Returns the full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return formatName(this.first) + " " + formatName(this.last);
    }

    /**
     * Returns the reverse name.
     *
     * @return the reverse name
     */
    public String getReverseName() {
        return new StringBuilder(this.last).reverse() + " " +
                new StringBuilder(this.first).reverse();
    }

    /**
     * Capitalises the first letter of the name, and lowercases the rest.
     *
     * @param name the name
     * @return the formatted name
     */
    private String formatName(final String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
