package ca.bcit.comp1510.lab10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The driver class to test the Name class.
 *
 * @author Xing Jiarui
 * @version 2024.4.4
 */
public class Driver {

    /**
     * The main method to test the Name class.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        // Initialise
        List<Name> names = new ArrayList<>();
        initNames(names);

        // Ask if user want to add more names
        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                printNames("Now the names BEFORE SORTING are:\n", names);
                System.out.println("\nDo you want to add another name? (y/N)");
                String choice = input.nextLine().trim().toLowerCase();

                switch (choice) {
                    case "y":
                        addName(input, names);
                        break;
                    case "n", "":
                        Collections.sort(names);
                        printNames("Now the names AFTER SORTING are:\n", names);
                        return;
                    default:
                        System.out.println("Invalid input.");
                        break;
                }
            }
        }
    }

    /**
     * Initialise the list of names.
     *
     * @param names the list of names
     */
    private static void initNames(List<Name> names) {
        // Add some names
        names.addAll(List.of(
                new Name("MAX", "KARl ERNst LUDWig", "PlANck"),
                new Name("KArl", null, "MArx"),
                new Name("ElOn", "REeve", "MUsk"),
                new Name("BRUCe", "LEE"),
                new Name("JIArUi", "XINg"),
                new Name("MiCHael", "JoSEPh", "JaCKSon"),
                new Name("JoE", "RobINETte", "BIDEn")
        ));
    }

    /**
     * Add a name.
     *
     * @param input the scanner for user input
     * @param names the list of names
     */
    private static void addName(Scanner input, List<Name> names) {
        System.out.println("Enter the first name:");
        String first = input.nextLine();
        System.out.println("Enter the middle name, if none, press enter:");
        String middle = input.nextLine().trim();
        middle = middle.isEmpty() ? null : middle;
        System.out.println("Enter the last name:");
        String last = input.nextLine();
        names.add(new Name(first, middle, last));
    }

    /**
     * Print the list of names.
     *
     * @param message the message to print before the names
     * @param names   the list of names to print
     */
    private static void printNames(String message, List<Name> names) {
        System.out.println(message + names.stream()
                .map(Name::getFullName)
                .collect(Collectors.joining("\n")));
    }
}
