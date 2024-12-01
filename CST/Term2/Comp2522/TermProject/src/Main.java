import java.util.Scanner;

/**
 * The main entry point of the application. Displays the menu and handles user choices to
 * start different games. Ensures the program runs in a loop until the user decides to
 * quit.
 *
 * @author Jiarui Xing
 */
public class Main {
    /**
     * The main method that starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        while(true)
        {
            printMenu();
            final String choice = scanner.nextLine().trim().toLowerCase();
            switch(choice)
            {
                case "w":
                    final WordGame wordGame = new WordGame();
                    wordGame.start();
                    break;
                case "n":
                    final NumberGame numberGame = new NumberGame();
                    numberGame.start();
                    break;
                case "m":
                    final MinecraftItem minecraftItem = new MinecraftItem();
                    minecraftItem.start();
                    break;
                case "q":
                    System.out.println("Quitting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Please enter W, N, M, or Q.");
            }
        }
    }

    /**
     * Prints the main menu options to the console.
     */
    private static void printMenu()
    {
        final StringBuilder sb;
        sb = new StringBuilder();
        sb.append("Press W to play the Word game.\n")
                .append("Press N to play the Number game.\n")
                .append("Press M to play the MinecraftItem.\n")
                .append("Press Q to quit.\n")
                .append("Your choice: ");
        System.out.print(sb);
    }
}
