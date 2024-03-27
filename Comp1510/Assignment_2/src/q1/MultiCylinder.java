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
     * This is 5.0.
     */
    private static final double FIVE_POINT_ZERO = 5.0;

    /**
     * This is 10.0.
     */
    private static final double TEN_POINT_ZERO = 10.0;

    /**
     * This is 3.0.
     */
    private static final double THREE_POINT_ZERO = 3.0;

    /**
     * This is 7.0.
     */
    private static final double SEVEN_POINT_ZERO = 7.0;

    /**
     * This is 6.0.
     */
    private static final double SIX_POINT_ZERO = 6.0;

    /**
     * This is 12.0.
     */
    private static final double TWELVE_POINT_ZERO = 12.0;

    /**
     * This is 4.0.
     */
    private static final double FOUR_POINT_ZERO = 4.0;

    /**
     * This is 8.0.
     */
    private static final double EIGHT_POINT_ZERO = 8.0;

    /**
     * The main method that runs the program.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        // Instantiates and updates two Cylinder objects.
        Cylinder cylinder1 = new Cylinder(FIVE_POINT_ZERO, TEN_POINT_ZERO);
        Cylinder cylinder2 = new Cylinder(THREE_POINT_ZERO, SEVEN_POINT_ZERO);

        // Printing their state before and after modification.
        System.out.println("Initial State:");
        printCylinderState(cylinder1);
        printCylinderState(cylinder2);

        // Modification.
        cylinder1.setRadius(SIX_POINT_ZERO);
        cylinder1.setHeight(TWELVE_POINT_ZERO);
        cylinder2.setRadius(FOUR_POINT_ZERO);
        cylinder2.setHeight(EIGHT_POINT_ZERO);

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
