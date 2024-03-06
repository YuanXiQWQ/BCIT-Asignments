package ca.bcit.comp1510.lab05;

/**
 * To represent Cube concept.
 *
 * @author Xing Jiarui
 * @version 2024.2.15
 */
public class Cone {
    private double radius;
    private double height;

    /**
     * Constructor.
     *
     * @param radius radius of the cone
     * @param height height of the cone
     */
    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    /**
     * Getter of radius.
     *
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Getter of height.
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Setter of radius.
     *
     * @param radius radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Setter of height.
     *
     * @param height height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * To get a Volume of a cone.
     *
     * @return volume
     */
    public double getVolume() {
        return 1.0 / 3.0 * Math.PI * Math.pow(radius, 2) * height;
        //V = 1/3 * pi * r^2 * h
    }

    /**
     * To get the slant height of a cone.
     *
     * @return slant height
     */
    public double getSlantHeight() {
        return Math.sqrt(Math.pow(radius, 2) + Math.pow(height, 2));
        //h = sqrt(r^2 + h^2)
    }

    /**
     * To get the surface area of a cone
     *
     * @return surface area
     */
    public double getSurfaceArea() {
        return Math.PI * Math.pow(radius, 2) + Math.PI * radius * getSlantHeight();
        //S = pi * r^2 + pi * r * h
    }

    @Override
    public String toString() {
        return "Cone: radius = " + radius
                + ", height = " + height
                + ", surface area = " + getSurfaceArea()
                + ", volume = " + getVolume();
    }
}
