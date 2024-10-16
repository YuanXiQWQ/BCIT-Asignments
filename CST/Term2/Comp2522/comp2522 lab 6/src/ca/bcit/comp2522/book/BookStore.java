package ca.bcit.comp2522.book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The BookStore class represents a collection of various literature items. It provides
 * methods to manage and retrieve information about the literature items.
 *
 * @param <T> the type of literature the bookstore holds, extending Literature
 * @author Jack Le
 * @author Daniel Wang
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class BookStore<T extends Literature> {

    private static final int DECADE_RANGE = 10;
    private final String name;
    private final List<T> items;

    /**
     * Constructs a new BookStore object with the specified name.
     *
     * @param name the name of the bookstore
     */
    public BookStore(final String name)
    {
        validateBookStore(name);
        this.name = name;
        this.items = new ArrayList<>();
    }

    /**
     * Validates the bookstore name.
     *
     * @param name the name to validate
     * @throws IllegalArgumentException if the name is null or empty
     */
    private void validateBookStore(final String name)
    {
        if(name == null || name.isEmpty())
        {
            throw new IllegalArgumentException("Collection Name cannot be null or empty");
        }
    }

    /**
     * Adds an item to the bookstore.
     *
     * @param item the item to add
     */
    public void addItem(T item)
    {
        items.add(Objects.requireNonNull(item, "Item cannot be null"));
    }

    /**
     * Prints all item titles.
     */
    public void printItems()
    {
        items.forEach(item -> System.out.println(item.getTitle()));
    }

    /**
     * Prints all item titles in uppercase.
     */
    public void printAllTitles()
    {
        items.forEach(item -> System.out.println(item.getTitle().toUpperCase()));
    }

    /**
     * Prints all item titles that contain the specified substring (case-sensitive). Uses
     * a lambda expression and String::contains within the lambda.
     *
     * @param title the substring to search for in the titles
     */
    public void printBookTitle(String title)
    {
        items.forEach(item ->
        {
            if(item.getTitle().contains(title))
            {
                System.out.println(item.getTitle());
            }
        });
    }

    /**
     * Prints all item titles in alphabetical order using String::compareToIgnoreCase.
     */
    public void printTitlesInAlphaOrder()
    {
        items.sort(
                Comparator.comparing(Literature::getTitle, String::compareToIgnoreCase));
        items.forEach(item -> System.out.println(item.getTitle()));
    }

    /**
     * Sorts items using the provided comparator.
     *
     * @param comparator the comparator to determine the order of the items
     */
    public void sortItems(Comparator<? super T> comparator)
    {
        items.sort(comparator);
    }

    /**
     * Adds novels to a collection using the PECS principle.
     *
     * @param novelCollection the collection to add novels to
     */
    public void addNovelsToCollection(List<? super Novel> novelCollection)
    {
        for(T item : items)
        {
            if(item instanceof Novel)
            {
                novelCollection.add((Novel) item);
            }
        }
    }

    /**
     * Gets the name of the bookstore.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the list of items in the bookstore.
     *
     * @return the items
     */
    public List<T> getItems()
    {
        return items;
    }

    /**
     * Static nested class representing bookstore information.
     */
    public static class BookStoreInfo {
        /**
         * Displays information about the bookstore.
         *
         * @param storeName the name of the store
         * @param itemCount the number of items
         */
        public void displayInfo(String storeName, int itemCount)
        {
            System.out.println("BookStore: " + storeName + ", Items: " + itemCount);
        }
    }

    /**
     * Inner class for novel statistics.
     */
    public class NovelStatistics {
        /**
         * Calculates the average title length of novels.
         *
         * @return the average title length
         */
        public double averageTitleLength()
        {
            int totalLength = 0;
            int novelCount = 0;
            for(T item : items)
            {
                if(item instanceof Novel)
                {
                    totalLength += item.getTitle().length();
                    novelCount++;
                }
            }
            return novelCount == 0 ? 0 : (double) totalLength / novelCount;
        }
    }

    /**
     * Prints novels from the specified decade.
     *
     * @param decade the starting year of the decade
     */
    public void printNovelsFromDecade(int decade)
    {
        for(T item : items)
        {
            if(item instanceof Novel novel)
            {
                int year = novel.getYearPublished();
                if(year >= decade && year < decade + DECADE_RANGE)
                {
                    System.out.println(novel.getTitle());
                }
            }
        }
    }
}
