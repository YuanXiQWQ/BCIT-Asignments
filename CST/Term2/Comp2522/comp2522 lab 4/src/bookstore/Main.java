package bookstore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The main class to simulate the Enhanced Bookstore System.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Main {
    public static void main(String[] args)
    {
        // Create Authors
        Author georgeOrwell = new Author(
                new Name("George", "Orwell"),
                new Date(1903, 6, 25),
                null,
                "Dystopian"
        );

        Author harperLee = new Author(
                new Name("Harper", "Lee"),
                new Date(1926, 4, 28),
                new Date(2016, 2, 19),
                "Southern Gothic"
        );

        Author janeAusten = new Author(
                new Name("Jane", "Austen"),
                new Date(1775, 12, 16),
                new Date(1817, 7, 18),
                "Romance"
        );

        Author fScottFitzgerald = new Author(
                new Name("F. Scott", "Fitzgerald"),
                new Date(1896, 9, 24),
                new Date(1940, 12, 21),
                "Tragedy"
        );

        Author hermanMelville = new Author(
                new Name("Herman", "Melville"),
                new Date(1819, 8, 1),
                new Date(1891, 9, 28),
                "Adventure"
        );

        // Create Books
        Book book1 = new Book("1984", 1949, georgeOrwell);
        Book book2 = new Book("To Kill a Mockingbird", 1960, harperLee);
        Book book3 = new Book("Pride and Prejudice", 1813, janeAusten);
        Book book4 = new Book("The Great Gatsby", 1925, fScottFitzgerald);
        Book book5 = new Book("Moby-Dick", 1851, hermanMelville);

        // Create Persons for Biographies
        Person robertMoses = new Person(
                new Name("Robert", "Moses"),
                new Date(1888, 7, 18),
                new Date(1981, 7, 29)
        );

        Person steveJobs = new Person(
                new Name("Steve", "Jobs"),
                new Date(1955, 2, 24),
                new Date(2011, 10, 5)
        );

        Person winstonChurchill = new Person(
                new Name("Winston", "Churchill"),
                new Date(1874, 11, 30),
                new Date(1965, 1, 24)
        );

        Person alexanderHamilton = new Person(
                new Name("Alexander", "Hamilton"),
                new Date(1755, 1, 11),
                new Date(1804, 7, 12)
        );

        Person albertEinstein = new Person(
                new Name("Albert", "Einstein"),
                new Date(1879, 3, 14),
                new Date(1955, 4, 18)
        );

        // Create Authors for Biographies
        Author robertCaro = new Author(
                new Name("Robert", "Caro"),
                new Date(1935, 10, 30),
                null,
                "Political Biography"
        );

        Author walterIsaacson = new Author(
                new Name("Walter", "Isaacson"),
                new Date(1952, 5, 20),
                null,
                "Biography"
        );

        Author martinGilbert = new Author(
                new Name("Martin", "Gilbert"),
                new Date(1936, 2, 20),
                new Date(2015, 1, 2),
                "Historical Biography"
        );

        Author ronChernow = new Author(
                new Name("Ron", "Chernow"),
                new Date(1949, 3, 3),
                null,
                "Biography"
        );

        // Reuse Walter Isaacson for multiple biographies

        // Create Biographies
        Biography bio1 = new Biography("The Power Broker", 1974, robertCaro, robertMoses);
        Biography bio2 = new Biography("Steve Jobs", 2011, walterIsaacson, steveJobs);
        Biography bio3 =
                new Biography("Churchill: A Life", 1991, martinGilbert, winstonChurchill);
        Biography bio4 =
                new Biography("Alexander Hamilton", 2004, ronChernow, alexanderHamilton);
        Biography bio5 =
                new Biography("Einstein: His Life and Universe", 2007, walterIsaacson,
                        albertEinstein);

        // Create Authors for Autobiographies
        Author anneFrank = new Author(
                new Name("Anne", "Frank"),
                new Date(1929, 6, 12),
                new Date(1945, 3, 1),
                "Autobiography"
        );

        Author nelsonMandela = new Author(
                new Name("Nelson", "Mandela"),
                new Date(1918, 7, 18),
                new Date(2013, 12, 5),
                "Autobiography"
        );

        Author mahatmaGandhi = new Author(
                new Name("Mahatma", "Gandhi"),
                new Date(1869, 10, 2),
                new Date(1948, 1, 30),
                "Autobiography"
        );

        Author mayaAngelou = new Author(
                new Name("Maya", "Angelou"),
                new Date(1928, 4, 4),
                new Date(2014, 5, 28),
                "Autobiography"
        );

        // Create Composite Author for Autobiography of Malcolm X
        Author malcolmXAndAlexHaley = new Author(
                new Name("Malcolm X and Alex", "Haley"),
                new Date(1925, 5, 19),
                new Date(1965, 2, 21),
                "Autobiography"
        );

        // Create Autobiographies
        Autobiography auto1 =
                new Autobiography("The Diary of a Young Girl", 1947, anneFrank);
        Autobiography auto2 =
                new Autobiography("Long Walk to Freedom", 1994, nelsonMandela);
        Autobiography auto3 =
                new Autobiography("The Story of My Experiments with Truth", 1927,
                        mahatmaGandhi);
        Autobiography auto4 =
                new Autobiography("I Know Why the Caged Bird Sings", 1969, mayaAngelou);
        Autobiography auto5 = new Autobiography("Autobiography of Malcolm X", 1965,
                malcolmXAndAlexHaley);

        // Collect all books, biographies, and autobiographies
        List<Book> books = new ArrayList<>();
        Collections.addAll(books, book1, book2, book3, book4, book5);

        List<Biography> biographies = new ArrayList<>();
        Collections.addAll(biographies, bio1, bio2, bio3, bio4, bio5);

        List<Autobiography> autobiographies = new ArrayList<>();
        Collections.addAll(autobiographies, auto1, auto2, auto3, auto4, auto5);

        // Display all books
        System.out.println("=== Books ===");
        for(Book book : books) {
            book.display();
            book.backward();
            System.out.println();
        }

        // Display all biographies
        System.out.println("=== Biographies ===");
        for(Biography bio : biographies) {
            bio.display();
            bio.backward();
            System.out.println();
        }

        // Display all autobiographies
        System.out.println("=== Autobiographies ===");
        for(Autobiography auto : autobiographies) {
            auto.display();
            auto.backward();
            System.out.println();
        }

        // Perform comparisons
        System.out.println("=== Comparisons ===");

        // Compare books by publication year (older books are "larger")
        Collections.sort(books);
        System.out.println("Books sorted by publication year (oldest to newest):");
        for(Book book : books) {
            System.out.println(book.getTitle() + " (" + book.getYearPublished() + ")");
        }
        System.out.println();

        // Compare authors by birthdate (older authors are "larger")
        List<Author> authors = new ArrayList<>();
        authors.add(georgeOrwell);
        authors.add(harperLee);
        authors.add(janeAusten);
        authors.add(fScottFitzgerald);
        authors.add(hermanMelville);
        authors.add(robertCaro);
        authors.add(walterIsaacson);
        authors.add(martinGilbert);
        authors.add(ronChernow);
        Collections.sort(authors);
        System.out.println("Authors sorted by birthdate (oldest to youngest):");
        for(Author author : authors) {
            System.out.println(author.getName().getFullName() + " (" +
                    author.getDateOfBirth().getYYYYMMDD() + ")");
        }
        System.out.println();

        // Compare biographies by subjects' birthdate (older subjects are "larger")
        biographies.sort(Comparator.comparing(Biography::getSubject));
        System.out.println(
                "Biographies sorted by subjects' birthdate (oldest to youngest):");
        for(Biography bio : biographies) {
            System.out.println(bio.getTitle() + " about " +
                    bio.getSubject().getName().getFullName() + " (" +
                    bio.getSubject().getDateOfBirth().getYYYYMMDD() + ")");
        }
        System.out.println();

        // Example of equals() method
        System.out.println("=== Equality Checks ===");
        System.out.println("bio1 equals bio2: " + bio1.equals(bio2));
        System.out.println("auto1 equals auto2: " + auto1.equals(auto2));
    }
}
