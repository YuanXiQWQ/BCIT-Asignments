package bookstore;

import java.util.Objects;

/**
 * Represents a biography book about a specific subject.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Biography extends Book implements Printable {
    private final Person subject;

    /**
     * Constructs a Biography object with the specified title, publication year, author,
     * and subject.
     *
     * @param title         the title of the biography, must not be null, blank, and less
     *                      than 100 characters
     * @param yearPublished the year the biography was published, must be between 1 and
     *                      the current year
     * @param author        the author of the biography, must not be null
     * @param subject       the subject of the biography, must not be null
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Biography(final String title,
                     final int yearPublished,
                     final Author author,
                     final Person subject)
    {
        super(title, yearPublished, author);
        if(subject == null) {
            throw new IllegalArgumentException("Subject must not be null.");
        }
        this.subject = subject;
    }

    /**
     * Returns the subject of the biography.
     *
     * @return the Person object representing the subject
     */
    public Person getSubject()
    {
        return subject;
    }

    /**
     * Displays all attributes of the biography, including the subject.
     */
    @Override
    public void display()
    {
        System.out.println(
                "Title: " +
                        getTitle() +
                        ", Year Published: " +
                        getYearPublished() +
                        ", Author: " +
                        getAuthor().getName().getFullName() +

                        ", Subject: " +
                        subject.getName().getFullName());
    }

    /**
     * Overrides equals to compare biographies based on their subjects.
     *
     * @param obj the object to compare
     * @return true if both biographies are about the same subject, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Biography other)) {
            return false;
        }
        return subject.equals(other.subject);
    }

    /**
     * Overrides hashCode based on the subject.
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(subject);
    }
}
