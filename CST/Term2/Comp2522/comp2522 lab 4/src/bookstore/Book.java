package bookstore;

import java.util.Objects;

/**
 * private static Represents a book with a title, publication year, and author.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Book implements Comparable<Book>, Printable, Reversible {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MIN_YEAR_PUBLISHED = 1;

    private final String title;
    private final int yearPublished;
    private final Author author;


    /**
     * Constructs a Book object with the specified title, publication year, and author.
     *
     * @param title         the title of the book, must not be null, blank, and less than
     *                      100 characters
     * @param yearPublished the year the book was published, must be between 1 and the
     *                      current year
     * @param author        the author of the book, must not be null
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Book(String title, int yearPublished, Author author)
    {
        if(title == null || title.isBlank() || title.length() >= MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(
                    "Title must not be null, blank, or exceed 100 characters.");
        }
        int currentYear = java.time.Year.now().getValue();
        if(yearPublished < MIN_YEAR_PUBLISHED || yearPublished > currentYear) {
            throw new IllegalArgumentException(
                    "Year published must be between 1 and " + currentYear + ".");
        }
        if(author == null) {
            throw new IllegalArgumentException("Author must not be null.");
        }
        this.title = title;
        this.yearPublished = yearPublished;
        this.author = author;
    }

    /**
     * Returns the title of the book.
     *
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Returns the publication year of the book.
     *
     * @return the year published
     */
    public int getYearPublished()
    {
        return yearPublished;
    }

    /**
     * Returns the author of the book.
     *
     * @return the Author object
     */
    public Author getAuthor()
    {
        return author;
    }

    /**
     * Displays the book's title, publication year, and author.
     */
    @Override
    public void display()
    {
        System.out.println(
                "Title: " + title + ", Year Published: " + yearPublished + ", Author: " +
                        author.getName().getFullName());
    }

    /**
     * Displays the book's title in reverse order.
     */
    @Override
    public void backward()
    {
        System.out.println(new StringBuilder(title).reverse().toString());
    }

    /**
     * Compares this book with another based on publication year. Older books are
     * considered "larger."
     *
     * @param other the other book to compare to
     * @return a negative integer, zero, or a positive integer as this book is older than,
     * equal to, or newer than the specified book
     */
    @Override
    public int compareTo(Book other)
    {
        return Integer.compare(other.yearPublished, this.yearPublished);
    }

    /**
     * Overrides equals to compare books based on title, yearPublished, and author.
     *
     * @param obj the object to compare
     * @return true if both books have the same title, yearPublished, and author, false
     * otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Book other)) {
            return false;
        }
        return yearPublished == other.yearPublished && title.equals(other.title) &&
                author.equals(other.author);
    }

    /**
     * Overrides hashCode based on title, yearPublished, and author.
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(title, yearPublished, author);
    }
}
