package ca.bcit.comp1510.lab05;

/**
 * Test the class Name
 *
 * @author Jiarui Xing
 * @version 2024.2.15
 */

public class NameDriver {
    public static void main(String[] args) {
        String firstName = "Jiarui";
        String middleName = " ";
        String lastName = "Xing";
        Name testName = new Name(firstName, middleName, lastName);

        // Test methods
        //a
        System.out.println("A: Full name length: " + testName.getFullNameLength());
        //b
        System.out.println("B: FirstLetters: " + testName.getFirstLetters());
        //c
        System.out.println("C:The 6 character is: " + testName.getNthCharacter(6));
        //d
        System.out.println("Formatted name: " + testName.getFullName());
        //e
        String firstName2 = "Jiarui";
        String firstName3 = "Xing";
        System.out.println(firstName2 + " = " + firstName + " : " + testName.isSameName(firstName2));
        System.out.println(firstName3 + " = " + firstName + " : " + testName.isSameName(firstName3));

        //f
        Name anotherName = new Name(firstName, middleName, lastName);
        System.out.println("Is the same name object: " + testName.isSameNameObject(anotherName));

    }
}
