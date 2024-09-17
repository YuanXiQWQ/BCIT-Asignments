package ca.bcit.comp1510.lab06;

import java.util.Scanner;

/**
 * Test the class Name
 *
 * @author Xing Jiarui
 * @version 2024.2.22
 */

public class NameDriver {
    public static void main(String[] args) {
        //Initialise
        Scanner input = new Scanner(System.in);

        //Interaction
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter your middle name: ");
        String middleName = input.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
        Name testName = new Name(firstName, middleName, lastName);

        // Test methods
        //setters and constructor
        System.out.println("Formatted name: " + testName.getFullName());
        //getCharacter
        System.out.println("Type a number: ");
        int number = input.nextInt();
        System.out.println("C:The " + number + " character is: " + testName.getNthCharacter(number));
    }
}
