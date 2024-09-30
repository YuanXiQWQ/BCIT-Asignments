package bookstore;

import java.util.Objects;

/**
 * Represents a person with a name, date of birth, and optional date of death.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Person implements Comparable<Person>, Printable, Reversible {
    private final Date dateOfBirth;
    private final Date dateOfDeath;
    private final Name name;

    /**
     * Constructs a Person object with the given name, date of birth, and date of death.
     *
     * @param name        the name of the person, must not be null
     * @param dateOfBirth the birthdate, must not be null
     * @param dateOfDeath the death date, can be null
     * @throws IllegalArgumentException if name or dateOfBirth is null
     */
    public Person(Name name, Date dateOfBirth, Date dateOfDeath)
    {
        if(name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if(dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

    /**
     * Returns the name of the person.
     *
     * @return the Name object
     */
    public Name getName()
    {
        return name;
    }

    /**
     * Returns the date of birth.
     *
     * @return the birth Date
     */
    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    /**
     * Returns the date of death.
     *
     * @return the death Date, or null if still alive
     */
    public Date getDateOfDeath()
    {
        return dateOfDeath;
    }

    /**
     * Returns whether the person is alive.
     *
     * @return true if alive, false otherwise
     */
    public boolean isAlive()
    {
        return dateOfDeath == null;
    }

    /**
     * Returns the details of the person in a formatted string.
     *
     * @return the formatted details
     */
    public String getDetails()
    {
        StringBuilder details = new StringBuilder();
        details.append(name.getFullName()).append(" ");
        if(isAlive()) {
            details.append("(alive)");
        } else {
            details.append("(died ").append(dateOfDeath.getDayOfTheWeek().toLowerCase())
                    .append(", ").append(dateOfDeath.toString()).append(")");
        }
        details.append(" was born on ").append(dateOfBirth.toString()).append("!");
        return details.toString();
    }

    /**
     * Displays all attributes of the person.
     */
    @Override
    public void display()
    {
        System.out.println(getDetails());
    }

    /**
     * Displays the full name in reverse order.
     */
    @Override
    public void backward()
    {
        name.backward();
    }

    /**
     * Compares this person with another based on their date of birth. Older people are
     * considered "larger."
     *
     * @param other the other person to compare to
     * @return a negative integer, zero, or a positive integer as this person is older
     * than, equal to, or younger than the specified person
     */
    @Override
    public int compareTo(Person other)
    {
        if(this.dateOfBirth.year() != other.dateOfBirth.year()) {
            return Integer.compare(other.dateOfBirth.year(), this.dateOfBirth.year());
        }
        if(this.dateOfBirth.month() != other.dateOfBirth.month()) {
            return Integer.compare(other.dateOfBirth.month(), this.dateOfBirth.month());
        }
        return Integer.compare(other.dateOfBirth.day(), this.dateOfBirth.day());
    }

    /**
     * Overrides equals to compare name and date of birth.
     *
     * @param obj the object to compare
     * @return true if both persons are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Person other)) {
            return false;
        }
        return name.equals(other.name) && dateOfBirth.equals(other.dateOfBirth) &&
                Objects.equals(dateOfDeath, other.dateOfDeath);
    }

    /**
     * Overrides hashCode based on name, date of birth, and date of death.
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(name, dateOfBirth, dateOfDeath);
    }
}
