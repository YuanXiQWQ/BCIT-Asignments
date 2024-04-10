package q1;

/**
 * Represents a BCIT student.
 *
 * @author Lewis & Loftus 9e
 * @author BCIT
 * @author Xing Jiarui
 * @version 2017
 */
public class Student {
    /**
     * First name of this student.
     */
    private final String firstName;

    /**
     * Last name of this student.
     */
    private final String lastName;

    /**
     * Home address of this student.
     */
    private final Address homeAddress;

    /**
     * School address of this student.  Can be shared by other students
     */
    private final Address schoolAddress;

    /**
     * Three test scores.
     */
    private final int[] testScores = new int[3];

    /**
     * Constructs a Student object that contains the specified values.
     *
     * @param first  a String representing the first name
     * @param last   a String representing the last name
     * @param home   an Address object containing the home address
     * @param school an Address object containing the school address
     * @param scores an array of ints representing the test scores
     */
    public Student(String first, String last, Address home, Address school, int[] scores) {
        firstName = first;
        lastName = last;
        homeAddress = home;
        schoolAddress = school;
        if (scores != null && scores.length == 3) {
            System.arraycopy(scores, 0, this.testScores, 0, scores.length);
        }
    }

    /**
     * Returns a String description of this Student object.
     *
     * @return description a String
     */
    public String toString() {
        String result;

        result = firstName + " " + lastName + "\n";
        result += "Home Address:\n" + homeAddress + "\n";
        result += "School Address:\n" + schoolAddress;

        return result;
    }
}

