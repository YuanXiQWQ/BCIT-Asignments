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

    /**
     * Constructs a new Person object.
     *
     * @param name      the person's name
     * @param birthDate the person's birthdate
     * @param deathDate the person's death date
     */
    public Person(Name name, Date birthDate, Date deathDate) {
        if (name == null || birthDate == null) {
            throw new IllegalArgumentException("Name and birth date must not be null");
        }
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    /**
     * Returns the person's name.
     *
     * @return the name
     */
    public Name getName() {
        return name;
    }

    /**
     * Returns the person's birthdate.
     *
     * @return the birthdate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the person's death date.
     *
     * @return the death date, null if the person is still alive
     */
    public Date getDeathDate() {
        return deathDate;
    }

    /**
     * Checks if the person is alive.
     *
     * @return true if the person is alive, false if deceased
     */
    public boolean isAlive() {
        return deathDate == null;
    }

    /**
     * Returns the details of the person.
     *
     * @return the person's details
     */
    public String getDetails() {
        if (isAlive()) {
            return String.format("%s (alive) was born on %s!",
                    name.getFullName(),
                    birthDate.getDayOfTheWeek() + " " + birthDate.getYYYYMMDD());
        } else {
            return String.format("%s (died %s) was born on %s!",
                    name.getFullName(),
                    deathDate.getDayOfTheWeek() + " " + deathDate.getYYYYMMDD(),
                    birthDate.getDayOfTheWeek() + " " + birthDate.getYYYYMMDD());
        }
    }
}