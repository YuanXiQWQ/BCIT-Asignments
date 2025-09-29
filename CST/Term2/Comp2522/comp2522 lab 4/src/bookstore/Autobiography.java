package bookstore;

/**
 * Represents an autobiography, which is a biography where the subject is the author.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Autobiography extends Biography implements Printable {

    /**
     * Constructs an Autobiography object with the specified title, publication year, and
     * author. The subject is automatically set to the author.
     *
     * @param title         the title of the autobiography, must not be null, blank, and
     *                      less than 100 characters
     * @param yearPublished the year the autobiography was published, must be between 1
     *                      and the current year
     * @param author        the author of the autobiography, must not be null
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Autobiography(final String title,
                         final int yearPublished,
                         final Author author)
    {
        super(title, yearPublished, author, author);
    }

    /**
     * Displays the autobiography's information, including the author.
     */
    @Override
    public void display()
    {
        System.out.println("Autobiography Title: " +
                getTitle() +
                ", Year Published: " +
                getYearPublished() +
                ", Author: " +
                getAuthor().getName().getFullName());
    }
}
