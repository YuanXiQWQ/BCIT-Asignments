package ca.bcit.comp1510.lab05;

/**
 * To represent Sphere concept.
 *
 * @author Xing Jiarui
 * @version 2024.2.15
 */
public class Sphere {
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;
    private double radius;

    /**
     * Constructor.
     *
     * @param xCoordinate x coordinate of a sphere
     * @param yCoordinate y coordinate of a sphere
     * @param zCoordinate z coordinate of a sphere
     * @param radius      radius of a sphere
     */
    public Sphere(double xCoordinate, double yCoordinate, double zCoordinate, double radius) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.radius = radius;
    }

    /**
     * Getter of xCoordinate.
     *
     * @return xCoordinate
     */
    public double getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Getter of yCoordinate.
     *
     * @return yCoordinate
     */
    public double getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Getter of zCoordinate.
     *
     * @return zCoordinate
     */
    public double getzCoordinate() {
        return zCoordinate;
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
     * Setter of xCoordinate.
     *
     * @param xCoordinate xCoordinate
     */
    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
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
     * Setter of yCoordinate.
     *
     * @param yCoordinate yCoordinate
     */
    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Setter of zCoordinate.
     *
     * @param zCoordinate zCoordinate
     */
    public void setzCoordinate(double zCoordinate) {
        this.zCoordinate = zCoordinate;
    }

    /**
     * To get the surface area of a sphere.
     *
     * @return surface area
     */
    public double getSurfaceArea() {
        return 4 * Math.PI * Math.pow(radius, 2);
        //S = 4 * pi * r^2
    }

    /**
     * To get the volume of a sphere.
     *
     * @return volume
     */
    public double getVolume() {
        return 4.0 / 3.0 * Math.PI * Math.pow(radius, 3);
        //V = 4/3 * pi * r^3
    }

    /**
     * To get the information of this sphere.
     *
     * @return center
     */
    @Override
    public String toString() {
        return "Sphere: x = " + xCoordinate
                + ", y = " + yCoordinate
                + ", z = " + zCoordinate
                + ", radius = " + radius
                + ", surface area = " + getSurfaceArea()
                + ", volume = " + getVolume();
    }
}
