package ca.bcit.comp2522.book;

/**
 * The Literature class is an abstract base class for different types of literature items.
 * It enforces the implementation of the getTitle method for all subclasses.
 *
 * @author Jack Le
 * @author Daniel Wang
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public abstract class Literature {
    /**
     * Gets the title of the literature.
     *
     * @return the title
     */
    public abstract String getTitle();
}
