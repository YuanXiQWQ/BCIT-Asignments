package bookstore;

import java.util.Objects;

/**
 * Represents an author with a specific genre.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Author extends Person implements Printable {
    private final String genre;

    /**
     * Constructs an Author object with the given name, date of birth, date of death, and
     * genre.
     *
     * @param name        the name of the author, must not be null
     * @param dateOfBirth the birthdate, must not be null
     * @param dateOfDeath the death date, can be null
     * @param genre       the genre of the author, must not be null, blank, and less than
     *                    30 characters
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Author(final Name name,
                  Date dateOfBirth,
                  final Date dateOfDeath,
                  final String genre)
    {
        super(name, dateOfBirth, dateOfDeath);
        if(genre == null ||
                genre.isBlank() ||
                genre.length() >= 30) {
            throw new IllegalArgumentException(
                    "Genre must not be null, blank, or exceed 30 characters.");
        }
        this.genre = genre;
    }

    /**
     * Returns the genre of the author.
     *
     * @return the genre
     */
    public String getGenre()
    {
        return genre;
    }

    /**
     * Displays the author's information.
     */
    @Override
    public void display()
    {
        System.out.println("Author: " +
                getName().getFullName() +
                ", Genre: " +
                genre);
    }

    /**
     * Overrides equals to compare authors based on name and genre.
     *
     * @param obj the object to compare
     * @return true if both authors have the same name and genre, false otherwise
     */

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Author other)) {
            return false;
        }
        return this.getName().equals(other.getName()) &&
                this.genre.equals(other.genre);
    }

    /**
     * Overrides hashCode based on superclass and genre.
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(getName(), genre);
    }
}
