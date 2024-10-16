package ca.bcit.comp2522.book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Main class demonstrates the usage of the BookStore with various literature items.
 *
 * @author Jack Le
 * @author Daniel Wang
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Main {

    public static void main(final String[] args)
    {

        final BookStore<Literature> store = new BookStore<>("Mixed Literature Store");
        store.addItem(new Novel("War and Peace", "Leo Tolstoy", 1869));
        store.addItem(new ComicBook("Spider-Man"));
        store.addItem(new Magazine("National Geographic"));

        System.out.println("All Items in the Store:");
        store.printItems();

        // Use the static nested class
        final BookStore.BookStoreInfo info = new BookStore.BookStoreInfo();
        info.displayInfo(store.getName(), store.getItems().size());

        // Use the inner class
        final BookStore<Literature>.NovelStatistics stats = store.new NovelStatistics();
        System.out.println("\nAverage Novel Title Length: " + stats.averageTitleLength());

        // Use lambda expression for searching titles
        System.out.println("\nBooks containing 'War':");
        store.printBookTitle("War");

        // Use method reference for sorting titles
        System.out.println("\nTitles in alphabetical order:");
        store.printTitlesInAlphaOrder();

        // PECS principal method
        final List<Literature> novelCollection = new ArrayList<>();
        store.addNovelsToCollection(novelCollection);
        System.out.println("\nNovels added to collection:");
        for(Literature lit : novelCollection)
        {
            System.out.println(lit.getTitle());
        }

        store.sortItems(new Comparator<Literature>() {
            @Override
            public int compare(final Literature o1, final Literature o2)
            {
                return Integer.compare(o1.getTitle().length(), o2.getTitle().length());
            }
        });
        System.out.println("\nItems sorted by title length:");
        store.printItems();

    }
}
