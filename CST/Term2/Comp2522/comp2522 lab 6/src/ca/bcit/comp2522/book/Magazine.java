package ca.bcit.comp2522.book;

/**
 * The Magazine class extends the Literature abstract class.
 *
 * @author Jack Le
 * @author Daniel Wang
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Magazine extends Literature {

    private final String title;

    /**
     * Constructs a new Magazine object with the specified title.
     *
     * @param title the title of the magazine
     */
    public Magazine(String title)
    {
        if(title == null ||
                title.isEmpty())
        {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        this.title = title;
    }

    /**
     * Returns the title of the magazine.
     *
     * @return the title
     */
    @Override
    public String getTitle()
    {
        return title;
    }
}
