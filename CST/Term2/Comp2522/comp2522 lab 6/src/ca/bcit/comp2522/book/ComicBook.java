package ca.bcit.comp2522.book;

/**
 * The ComicBook class represents a comic book with a title and illustrator. It extends
 * the Literature abstract class.
 *
 * @author Jack Le
 * @author Daniel Wang
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class ComicBook extends Literature {

    private final String title;

    /**
     * Constructs a new ComicBook object with the specified title.
     *
     * @param title the title of the comic book
     */
    public ComicBook(String title)
    {
        if(title == null ||
                title.isEmpty())
        {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        this.title = title;
    }

    /**
     * Returns the title of the comic book.
     *
     * @return the title
     */
    @Override
    public String getTitle()
    {
        return title;
    }
}
