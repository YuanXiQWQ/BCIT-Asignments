package q1;

import java.text.DecimalFormat;

/**
 * A class to test the Course class.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class TestCourse {
    /**
     * This is the main method (entry point) that gets called by the JVM.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // Initialise
        // Course comp1510 = new Course("COMP1510");
        Course comp1510 = new Course("COMP1510");

        // Address
        Address homeStay = new Address("4545 Picton Str.", "Vancouver",
                "BC", "V5R 3X1");
        Address fakeLocation = new Address("123 Fake St.", "Surrey",
                "BC", "FAK ECD");
        Address bcit = new Address("3700 Willingdon Ave.", "Burnaby",
                "BC", "V5G 3H2");

        // Student
        Student me = new Student
                ("Jiarui", "Xing", homeStay, bcit, new int[]{13, 18, 15});
        Student vincent = new Student
                ("Vincent", "Fung", fakeLocation, bcit, new int[]{16, 19, 17});

        // start test
        comp1510.addStudent(me);
        comp1510.addStudent(vincent);
        comp1510.role();

        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println(df.format(comp1510.average()));


        System.out.println("Question one was called and ran successfully.");
    }

}
