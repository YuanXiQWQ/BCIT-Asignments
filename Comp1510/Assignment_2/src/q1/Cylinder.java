package q1;

/**
 * A class that contains instance data that represents the cylinder’s radius and height.
 *
 * @author XingJiarui
 * @version 2024.3.18
 */
public class Cylinder {
    private double radius;
    private double height;

    //Constructor

    /**
     * Constructor for objects of class Cylinder
     */
    public Cylinder() {
        this.radius = radius;
        this.height = height;
    }

    //Getters

    /**
     * Getter for the radius
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter for the height
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    //Setters

    /**
     * Setter for the radius
     *
     * @param radius radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Setter for the height
     *
     * @param height height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    //Methods

    /**
     * Calculate the surface area of the cylinder
     *
     * @return surface area
     */
    public double surfaceArea() {
        //2 * pi * r * (r + h)
        return 2 * Math.PI * radius * (radius + height);
    }

    /**
     * Calculate the volume of the cylinder
     *
     * @return volume
     */
    public double volume() {
        //V = pi * r^2 * h
        return Math.PI * radius * radius * height;
    }

    /**
     * Get the description of the cylinder
     *
     * @return description
     */
    @Override
    public String toString() {
        return "Cylinder: radius = " + radius + ", height = " + height;
    }
}
