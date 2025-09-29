package ca.bcit.comp1510.lab11;

import java.util.Scanner;

/**
 * Shopping class to simulate a shopping scenario using Transaction.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class Shopping {
    /**
     * Main method.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        // Initialise.
        Scanner scanner = new Scanner(System.in);
        Transaction transaction = new Transaction(5);

        // Interactive.
        while (true) {
            System.out.print("Enter item name (or 'done' to finish): ");
            String name = scanner.nextLine();
            if ("done".equalsIgnoreCase(name)) {
                break;
            }

            System.out.print("Enter price: ");
            double price = scanner.nextDouble();

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            transaction.addToCart(name, price, quantity);
        }

        System.out.println("Final shopping cart:");
        System.out.println(transaction);

        // End.
        scanner.close();
    }
}
