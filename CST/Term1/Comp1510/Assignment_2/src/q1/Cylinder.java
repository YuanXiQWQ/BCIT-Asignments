package q1;

/**
 * A class that contains instance data. Represents the cylinder’s radius and
 * height.
 *
 * @author XingJiarui
 * @version 2024.3.18
 */
public class Cylinder {
    /**
     * The radius of the cylinder.
     */
    private double radius;

    /**
     * The height of the cylinder.
     */
    private double height;

    /**
     * Constructor for objects of class Cylinder.
     *
     * @param radius The radius of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    /**
     * Getter for the radius.
     *
     * @return The radius of the cylinder.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter for the height.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Setter for the radius.
     *
     * @param radius The new radius of the cylinder.
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Setter for the height.
     *
     * @param height The new height of the cylinder.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Calculate the surface area of the cylinder.
     *
     * @return The surface area of the cylinder.
     */
    public double surfaceArea() {
        return 2 * Math.PI * radius * (radius + height);
    }

    /**
     * Calculate the volume of the cylinder.
     *
     * @return The volume of the cylinder.
     */
    public double volume() {
        return Math.PI * radius * radius * height;
    }

    /**
     * Get the description of the cylinder.
     *
     * @return A string representation of the cylinder.
     */
    @Override
    public String toString() {
        return String.format("Cylinder: radius = %.2f, height = %.2f",
                radius, height);
    }
}
