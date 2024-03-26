package q1;

/**
 * A class that contains instance data that represents the cylinder’s radius and height.
 * For Assignment2 Cylinder Class
 *
 * @author XingJiarui
 * @version 2024.3.18
 */
public class MultiCylinder {
  public static void main(String[] args) {
    // Instantiates and updates two Cylinder objects (no user input required).
    Cylinder cylinder1 = new Cylinder(5.0, 10.0);
    Cylinder cylinder2 = new Cylinder(3.0, 7.0);

    // Printing their radius and height before modification.
    System.out.println("Initial State:");
    System.out.println(cylinder1);
    System.out.println(cylinder2);

    // Modification.
    cylinder1.setRadius(6.0);
    cylinder1.setHeight(12.0);
    cylinder2.setRadius(4.0);
    cylinder2.setHeight(8.0);

    // Printing their radius and height after modification.
    System.out.println("\nAfter Modification:");
    System.out.println(cylinder1);
    System.out.println(cylinder2);

    // Prints the final volume and surface area of each cylinder.
    System.out.println("\nFinal Volume and Surface Area:");
    System.out.println("Cylinder 1 - Volume: " + cylinder1.volume() + ", Surface Area: " + cylinder1.surfaceArea());
    System.out.println("Cylinder 2 - Volume: " + cylinder2.volume() + ", Surface Area: " + cylinder2.surfaceArea());
  }
}