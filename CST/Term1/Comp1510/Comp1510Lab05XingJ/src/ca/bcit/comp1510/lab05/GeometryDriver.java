package ca.bcit.comp1510.lab05;

import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * Using the Scanner to interact with the user and create some Shapes.
 *
 * @author Xing Jiarui
 * @version 2024.2.15
 */
public class GeometryDriver {

    /**
     * Driving the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {

        //Initialise
        Scanner input = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("#.###");

        //Sphere
        //Interactive
        System.out.println("Enter a radius for the sphere:");
        double radiusSphere = input.nextDouble();

        System.out.println("Now enter the x coordinate for the center of the sphere:");
        double xSphere = input.nextDouble();

        System.out.println("Now enter the y coordinate for the center of the sphere:");
        double ySphere = input.nextDouble();

        System.out.println("Now enter the z coordinate for the center of the sphere:");
        double zSphere = input.nextDouble();

        //Object
        Sphere sphere = new Sphere(xSphere, ySphere, zSphere, radiusSphere);

        //Output
        System.out.println("Sphere surface area: " + decimalFormat.format(sphere.getSurfaceArea()));
        System.out.println("Sphere volume: " + decimalFormat.format(sphere.getVolume()));

        //Cube
        //Interactive
        System.out.println("Enter an edge length for the cube:");
        double edgeLengthCube = input.nextDouble();

        System.out.println("Now enter the x coordinate for the center point of the cube:");
        double xCube = input.nextDouble();

        System.out.println("Now enter the y coordinate for the center point of the cube:");
        double yCube = input.nextDouble();

        System.out.println("Now enter the z coordinate for the center point of the cube:");
        double zCube = input.nextDouble();

        //Object
        Cube cube = new Cube(xCube, yCube, zCube, edgeLengthCube);

        //Output
        System.out.println("Cube surface area: " + decimalFormat.format(cube.getSurfaceArea()));
        System.out.println("Cube volume: " + decimalFormat.format(cube.getVolume()));
        System.out.println("Cube face diagonal: " + decimalFormat.format(cube.getFaceDiagonal()));
        System.out.println("Cube space diagonal: " + decimalFormat.format(cube.getSpaceDiagonal()));

        //Cone
        //Interactive
        System.out.println("Enter a radius for the cone:");
        double radiusCone = input.nextDouble();

        System.out.println("Now enter the height for the cone:");
        double heightCone = input.nextDouble();

        //Object
        Cone cone = new Cone(radiusCone, heightCone);

        //Output
        System.out.println("Cone volume: " + decimalFormat.format(cone.getVolume()));
        System.out.println("Cone slant height: " + decimalFormat.format(cone.getSlantHeight()));
        System.out.println("Cone surface area: " + decimalFormat.format(cone.getSurfaceArea()));

        input.close();
    }

}
