package q1;

/**
 * A driver class that demonstrates the functionality of the Cylinder class
 * by instantiating, updating, displaying information about Cylinder objects.
 * For Assignment2 Cylinder Class
 *
 * @author XingJiarui
 * @version 2024.3.18
 */
public class MultiCylinder {

    /**
     * The main method that runs the program.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        // Instantiates and updates two Cylinder objects.
        Cylinder cylinder1 = new Cylinder(5.0, 10.0);
        Cylinder cylinder2 = new Cylinder(3.0, 7.0);

        // Printing their state before and after modification.
        System.out.println("Initial State:");
        printCylinderState(cylinder1);
        printCylinderState(cylinder2);

        // Modification.
        cylinder1.setRadius(6.0);
        cylinder1.setHeight(12.0);
        cylinder2.setRadius(4.0);
        cylinder2.setHeight(8.0);

        System.out.println("\nAfter Modification:");
        printCylinderState(cylinder1);
        printCylinderState(cylinder2);

        System.out.println("Question one was called and ran successfully.");
    }

    /**
     * Prints the state of a Cylinder object including its radius,
     * height, volume, and surface area.
     *
     * @param cylinder The Cylinder object to print the state of.
     */
    private static void printCylinderState(Cylinder cylinder) {
        System.out.println("Cylinder: radius = "
                + String.format("%.2f", cylinder.getRadius())
                + ", height = " + String.format("%.2f", cylinder.getHeight()));
        System.out.printf("Volume: %.2f, Surface Area: %.2f%n",
                cylinder.volume(), cylinder.surfaceArea());
    }
}
