package bookstore;

/**
 * Represents a person's name with first and last components.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public record Name(String first, String last) implements Printable, Reversible {
    private static final int MAX_FIRST_LENGTH = 50;
    private static final String ADMIN_KEY_WORD = "admin";

    /**
     * Constructs a Name object with the given first and last names.
     *
     * @param first the first name, must not be null, blank, exceed 50 characters, or
     *              contain "admin"
     * @param last  the last name, must not be null, blank, exceed 50 characters, or
     *              contain "admin"
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Name
    {
        if(first == null || first.isBlank() || first.length() >= MAX_FIRST_LENGTH ||
                first.toLowerCase().contains(ADMIN_KEY_WORD)) {
            throw new IllegalArgumentException("Invalid first name.");
        }
        if(last == null || last.isBlank() || last.length() >= MAX_FIRST_LENGTH ||
                last.toLowerCase().contains(ADMIN_KEY_WORD)) {
            throw new IllegalArgumentException("Invalid last name.");
        }
    }

    /**
     * Returns the first name.
     *
     * @return the first name
     */
    @Override
    public String first()
    {
        return first;
    }

    /**
     * Returns the last name.
     *
     * @return the last name
     */
    @Override
    public String last()
    {
        return last;
    }

    /**
     * Returns the initials of the name in the format "F.L."
     *
     * @return the initials
     */
    public String getInitials()
    {
        return (first.charAt(0) + "." + last.charAt(0) + ".").toUpperCase();
    }

    /**
     * Returns the full name in the format "First Last".
     *
     * @return the full name
     */
    public String getFullName()
    {
        return first + " " + last;
    }

    /**
     * Returns the reverse of the full name.
     *
     * @return the reversed name
     */
    public String getReverseName()
    {
        return new StringBuilder(getFullName()).reverse().toString();
    }

    /**
     * Displays the full name.
     */
    @Override
    public void display()
    {
        System.out.println(getFullName());
    }

    /**
     * Displays the reversed full name.
     */
    @Override
    public void backward()
    {
        System.out.println(getReverseName());
    }

    /**
     * Overrides equals to compare first and last names.
     *
     * @param obj the object to compare
     * @return true if both names are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Name other)) {
            return false;
        }
        return first.equals(other.first) && last.equals(other.last);
    }

}
