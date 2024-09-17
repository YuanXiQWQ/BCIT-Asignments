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

    private static final int MAX_NAME_LENGTH = 45;
    private static final String ILLEGAL_NAME = "admin";
    private static final String INFO_INVALID_NAME =
            "Invalid first or last name provided.";
    private static final int FIRST_LETTER_BEGIN_INDEX = 0;
    private static final int FIRST_LETTER_END_INDEX = 1;

    /**
     * Constructs a new Name object.
     *
     * @param first the first name
     * @param last  the last name
     */
    public Name(final String first, final String last)
    {
        if (first == null || last == null || first.isBlank() || last.isBlank() ||
                first.length() > MAX_NAME_LENGTH || last.length() > MAX_NAME_LENGTH ||
                first.toLowerCase().contains(ILLEGAL_NAME) ||
                last.toLowerCase().contains(ILLEGAL_NAME)) {
            throw new IllegalArgumentException(INFO_INVALID_NAME);
        }
        this.first = first.trim();
        this.last = last.trim();
    }

    /**
     * Returns the initials of the name.
     *
     * @return the initials
     */
    public String getInitials()
    {
        return String.format("%c.%c.", Character.toUpperCase(this.first.charAt(0)),
                Character.toUpperCase(this.last.charAt(0)));
    }

    /**
     * Returns the full name.
     *
     * @return the full name
     */
    public String getFullName()
    {
        return formatName(this.first) + " " + formatName(this.last);
    }

    /**
     * Returns the reverse name.
     *
     * @return the reverse name
     */
    public String getReverseName()
    {
        return new StringBuilder(this.last).reverse() + " " +
                new StringBuilder(this.first).reverse();
    }

    /**
     * Capitalises the first letter of the name, and lowercases the rest.
     *
     * @param name the name
     * @return the formatted name
     */
    private String formatName(final String name)
    {
        return name.substring(FIRST_LETTER_BEGIN_INDEX, FIRST_LETTER_END_INDEX)
                .toUpperCase() + name.substring(FIRST_LETTER_END_INDEX).toLowerCase();
    }
}
