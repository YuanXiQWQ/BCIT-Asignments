package q1;

/**
 * A class that contains instance data that represents the cylinder’s radius and height.
 *
 * @author XingJiarui
 * @version 2024.3.18
 */
public class MultiCylinder {
    private static double radius;
    private static double height;

    private void outHere(String status) {
        System.out.println(status + ": "
                + "radius = " + radius + "; "
                + "height = " + height);
    }

    public static void main(String[] args) {
        MultiCylinder fnHere = new MultiCylinder();
        Cylinder cylinder = new Cylinder();

        //Before set
        radius = cylinder.getRadius();
        height = cylinder.getHeight();
        fnHere.outHere("Before set");

        //After set
        radius = 5.21;
        height = 13.14;
        cylinder.setRadius(radius);
        cylinder.setHeight(height);
        fnHere.outHere("After set");
    }
}
