package ca.bcit.comp1510.lab10;

import java.util.stream.Stream;

/**
 * Implement comparable and use it to sort a list of Names.
 *
 * @author Xing Jiarui
 * @version 2024.4.4
 */
public class Name implements Comparable<Name> {

    /**
     * The first name.
     */
    private final String first;

    /**
     * The middle name.
     */
    private final String middle;

    /**
     * The last name.
     */
    private final String last;

    /**
     * Formats the name.
     *
     * @param name first/middle/last name
     *
     * @return the formatted name
     */

    private String formatName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        return Stream.of(name.trim().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse(null);
    }

    /**
     * Constructor for Name with all names and probably doesn't have middle name (is blank).
     *
     * @param first  the first name
     * @param middle the middle name
     * @param last   the last name
     */
    public Name(String first, String middle, String last) {
        this.first = formatName(first);
        this.middle = formatName(middle);
        this.last = formatName(last);

        if (this.first == null || this.last == null) {
            throw new IllegalArgumentException(
                    // Check which one is null or both are null.
                    this.first != null ? "Last name cannot be null" : this.last != null ? "First "
                            + "name cannot be null" : "Name cannot be null");
        }
    }

    /**
     * Constructor for Name with only first and last.
     *
     * @param first the first name
     * @param last  the last name
     *
     * @see Name#Name(String, String, String)
     */
    public Name(String first, String last) {
        this(first, null, last);
    }

    /**
     * Retrieves the value of the first variable.
     *
     * @return the value of the first variable
     */
    public String getFirst() {
        return first;
    }

    /**
     * Retrieves the value of the middle variable.
     *
     * @return the value of the middle variable
     */
    public String getMiddle() {
        return middle;
    }

    /**
     * Retrieves the value of the last variable.
     *
     * @return the value of the last variable
     */
    public String getLast() {
        return last;
    }

    /**
     * Returns a string representation of the name.
     *
     * @return a string representation of the name
     */
    public String getFullName() {
        return middle == null ? first + " " + last : first + " " + middle + " " + last;
    }

    /**
     * Returns a string representation of the name.
     *
     * @return a string representation of the name
     */
    @Override
    public String toString() {
        return getFullName();
    }

    /**
     * @param other the other name
     *
     * @return the comparison result
     *
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(Name other) {
        int lastCmp = last.compareTo(other.last);
        if (lastCmp != 0) {
            return lastCmp;
        }
        int firstCmp = first.compareTo(other.first);
        if (firstCmp != 0) {
            return firstCmp;
        }
        return middle != null ? middle.compareTo(other.middle == null ? "" : other.middle) :
                other.middle == null ? 0 : -1;
    }
}
