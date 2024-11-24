import java.util.Scanner;

/**
 * The main entry point of the application. Displays the menu and handles user choices to
 * start different games. Ensures the program runs in a loop until the user decides to
 * quit.
 * <p>
 *
 * @author Jiarui Xing
 */
public class Main {
    /**
     * The main method that starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        final Scanner scanner = new Scanner(System.in);
        while(true)
        {
            printMenu();
            String choice = scanner.nextLine().trim().toLowerCase();
            switch(choice)
            {
                case "w":
                    WordGame wordGame = new WordGame();
                    wordGame.start();
                    break;
                case "n":
                    NumberGame numberGame = new NumberGame();
                    numberGame.start();
                    break;
                case "m":
                    MyGame myGame = new MyGame();
                    myGame.start();
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
        System.out.println("Press W to play the Word game.");
        System.out.println("Press N to play the Number game.");
        System.out.println("Press M to play the MyGame.");
        System.out.println("Press Q to quit.");
        System.out.print("Your choice: ");
    }
}
