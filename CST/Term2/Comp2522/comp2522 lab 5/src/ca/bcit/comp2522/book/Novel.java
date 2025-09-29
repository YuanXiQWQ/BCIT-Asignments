package ca.bcit.comp2522.book;

/**
 * The Novel class represents a book with a title, author, and publication year.
 *
 * @author Jack Le
 * @author Daniel Wang
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Novel {

    private static final int MAX_YEAR_PUBLISHED = 2024;
    private static final int MIN_YEAR_PUBLISHED = 0;

    final String title;
    final String authorName;
    final int yearPublished;

    /**
     * Constructs a new Novel object with the specified title, author name, and year published.
     *
     * @param title         the title of the novel
     * @param authorName    the name of the author
     * @param yearPublished the year the novel was published
     */
    public Novel(final String title,
                 final String authorName,
                 final int yearPublished) {

        validateNovel(title, authorName, yearPublished);

        this.title = title;
        this.authorName = authorName;
        this.yearPublished = yearPublished;
    }

    /**
     * Validates the properties of the novel.
     *
     * @param title         the title to validate
     * @param authorName    the author name to validate
     * @param yearPublished the year published to validate
     * @throws IllegalArgumentException if the title or author name is null or empty,
     *                                  or if the year published is not within the valid range
     */
    private void validateNovel(final String title,
                               final String authorName,
                               final int yearPublished) {
        if(title == null ||
                title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }

        if(authorName == null ||
                authorName.isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty or null");
        }

        if(yearPublished < MIN_YEAR_PUBLISHED ||
                yearPublished > MAX_YEAR_PUBLISHED) {
            throw new IllegalArgumentException("Year published is not within the valid range");
        }
    }

    /**
     * Returns the title of the novel.
     *
     * @return the title of the novel
     */
    protected String getTitle() {
        return title;
    }

    /**
     * Returns the author's name.
     *
     * @return the author's name
     */
    protected String getAuthorName() {
        return authorName;
    }

    /**
     * Returns the year the novel was published.
     *
     * @return the year published
     */
    protected int getYearPublished() {
        return yearPublished;
    }

    /**
     * Returns a string representation of the novel, including the title, author, and year published.
     *
     * @return a string representation of the novel
     */
    @Override
    public String toString() {
        StringBuilder book = new StringBuilder();
        book.append(title)
                .append(" by ")
                .append(authorName)
                .append(", published in ")
                .append(yearPublished);
        return book.toString();
    }
}
