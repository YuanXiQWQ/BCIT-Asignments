package ca.bcit.comp1510.lab04;

import java.util.Scanner;

/**
 * This program is used to test the Name class.
 *
 * @author Jiarui Xing
 * @version 2024.2.9
 */
public class NameDrive {
    /**
     * Driving the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {

        //Initialise
        Scanner input = new Scanner(System.in);

        //Interaction
        System.out.println("Enter your first name: ");
        String firstName = input.nextLine();
        System.out.println("Enter your middle name: ");
        String middleName = input.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = input.nextLine();
        Name name = new Name(firstName, middleName, lastName);

        //Capitalise method's diao yong
        name.setFirstName(capitalize(name.getFirstName()));
        name.setMiddleName(capitalize(name.getMiddleName()));
        name.setLastName(capitalize(name.getLastName()));

        //Output
        System.out.println("Your full name is: " + name);

        //End
        input.close();
    }

    /**
     * Capitalise the first letter of each name.
     *
     * @param name the name
     * @return the name
     */
    private static String capitalize(String name) {
        //Check
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
