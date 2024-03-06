package ca.bcit.comp1510.lab05;

/**
 * To represent Cube concept.
 *
 * @author Xing Jiarui
 * @version 2024.2.15
 */
public class Cube {
    private double xCoordinate;
    private double yCoordinate;
    private double zCoordinate;
    private double edgeLength;

    /**
     * Constructor.
     *
     * @param xCoordinate x coordinate of a cube
     * @param yCoordinate y coordinate of a cube
     * @param zCoordinate z coordinate of a cube
     * @param edgeLength  edge length of a cube
     */
    public Cube(double xCoordinate, double yCoordinate, double zCoordinate, double edgeLength) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.edgeLength = edgeLength;
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
     * Getter of edgeLength.
     *
     * @return edgeLength
     */
    public double getEdgeLength() {
        return edgeLength;
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
     * Setter of edgeLength.
     *
     * @param edgeLength edgeLength
     */
    public void setEdgeLength(double edgeLength) {
        this.edgeLength = edgeLength;
    }

    /**
     * To get the surface area of a cube.
     *
     * @return surface area
     */
    public double getSurfaceArea() {
        return 6 * Math.pow(edgeLength, 2);
        //S = 6 * a^2
    }

    /**
     * To get the volume of a cube.
     *
     * @return volume
     */
    public double getVolume() {
        return Math.pow(edgeLength, 3);
        //V = a^3
    }

    /**
     * To get the face diagonal of a cube.
     *
     * @return face diagonal
     */
    public double getFaceDiagonal() {
        return edgeLength * Math.sqrt(2);
        //d = a * sqrt(2)
    }

    /**
     * To get the space diagonal of a cube.
     *
     * @return space diagonal
     */
    public double getSpaceDiagonal() {
        return edgeLength * Math.sqrt(3);
        //D = a * sqrt(3)
    }

    /**
     * To get the information of this cube.
     *
     * @return center
     */
    @Override
    public String toString() {
        return "Cube: x = " + xCoordinate
                + ", y = " + yCoordinate
                + ", z = " + zCoordinate
                + ", edge length = " + edgeLength
                + ", surface area = " + getSurfaceArea()
                + ", volume = " + getVolume()
                + ", face diagonal = " + getFaceDiagonal()
                + ", space diagonal = " + getSpaceDiagonal();
    }

}
