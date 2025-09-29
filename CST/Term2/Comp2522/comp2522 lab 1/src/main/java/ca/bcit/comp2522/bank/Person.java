package ca.bcit.comp2522.bank;

/**
 * Represents a person with a name, birthdate, and optionally a death date.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class Person {
    private final Name name;
    private final Date birthDate;
    private final Date deathDate;

    private final static String INFO_NAME_OR_BIRTHDATE_NULL =
            "Name and birth date must not be null";
    private final static String INFO_PERSON_BORN = "was born on %s!";

    /**
     * Constructs a new Person object.
     *
     * @param name      the person's name
     * @param birthDate the person's birthdate
     * @param deathDate the person's death date
     */
    public Person(final Name name, final Date birthDate, final Date deathDate)
    {
        if (name == null || birthDate == null) {
            throw new IllegalArgumentException(INFO_NAME_OR_BIRTHDATE_NULL);
        }
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    /**
     * Getter for the person's name.
     *
     * @return the name
     */
    public Name getName()
    {
        return this.name;
    }

    /**
     * Getter for the person's birthdate.
     *
     * @return the death date, null if the person is still alive
     */
    public Date getDeathDate()
    {
        return this.deathDate;
    }

    /**
     * Checks if the person is alive.
     *
     * @return true if the person is alive, false if deceased
     */
    public boolean isAlive()
    {
        return this.deathDate == null;
    }

    /**
     * Formats a Date into a string like "dayOfWeek, YYYY-MM-DD".
     *
     * @param date the date to format
     * @return formatted string
     */
    protected String formatDate(final Date date)
    {
        return date.getDayOfTheWeek() + ", " + date.getYYYYMMDD();
    }

    /**
     * Returns the details of the person.
     *
     * @return the person's details
     */
    public String getDetails()
    {
        return String.format("%s %s",
                name.getFullName(),
                isAlive()
                ? String.format("(alive) " + INFO_PERSON_BORN, formatDate(birthDate))
                : String.format("(died %s) " + INFO_PERSON_BORN, formatDate(deathDate),
                        formatDate(birthDate)));
    }
}