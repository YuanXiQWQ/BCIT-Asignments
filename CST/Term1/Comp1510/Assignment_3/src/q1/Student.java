package q1;

import java.text.DecimalFormat;
import java.util.Arrays;

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
    private String firstName;

    /**
     * Last name of this student.
     */
    private String lastName;

    /**
     * Home address of this student.
     */
    private Address homeAddress;

    /**
     * School address of this student.  Can be shared by other students
     */
    private Address schoolAddress;

    /**
     * Three test scores.
     */
    private final int[] testScores = new int[TEST_NUM_MAX];

    /**
     * Maximum number of test scores.
     */
    private static final int TEST_NUM_MAX = 3;

    /**
     * Minimum number of test scores.
     */
    private static final int TEST_NUM_MIN = 1;

    /**
     * Array offset for test scores.
     */
    public static final int ARRAY_OFFSET = 1;

    /**
     * The start value.
     */
    public static final int STARTING_VALUE = 0;

    /**
     * Constructs a Student object that contains the specified values.
     *
     * @param first  a String representing the first name
     * @param last   a String representing the last name
     * @param home   an Address object containing the home address
     * @param school an Address object containing the school address
     * @param scores an array of ints representing the test scores
     * @throws IllegalArgumentException if test scores are not between 1 and 3 or if
     *                                  scores is null
     */
    public Student(String first, String last, Address home, Address school, int[] scores)
            throws IllegalArgumentException {
        firstName = first;
        lastName = last;
        homeAddress = home;
        schoolAddress = school;
        if (scores != null && scores.length == TEST_NUM_MAX) {
            System.arraycopy(scores, STARTING_VALUE, this.testScores, STARTING_VALUE,
                    scores.length);
        } else {
            throw new IllegalArgumentException(
                    "Test scores must be between 1 and 3 and not null");
        }
    }

    /**
     * Default constructor, each score set to 0.
     */
    public Student() {
        Arrays.fill(testScores, STARTING_VALUE);
    }

    /**
     * Sets the score for a specific test.
     *
     * @param testNumber the test number (1 through 3)
     * @param score      the score to set
     * @throws IllegalArgumentException if testNumber is not between 1 and 3
     */
    public void setTestScore(int testNumber, int score) throws IllegalArgumentException {
        if (testNumber >= TEST_NUM_MIN && testNumber <= TEST_NUM_MAX) {
            testScores[testNumber - ARRAY_OFFSET] = score;
        } else {
            throw new IllegalArgumentException("Test number must be between 1 and 3");
        }
    }

    /**
     * Retrieves the score for a specific test.
     *
     * @param testNumber the test number (1 through 3)
     * @return the score for the test
     *
     * @throws IllegalArgumentException if testNumber is not between 1 and 3
     */
    public int getTestScore(int testNumber) throws IllegalArgumentException {
        if (testNumber >= TEST_NUM_MIN && testNumber <= TEST_NUM_MAX) {
            return testScores[testNumber - ARRAY_OFFSET];
        }
        throw new IllegalArgumentException("Test number must be between 1 and 3");
    }

    /**
     * Calculates the average test score for this student.
     *
     * @return the average score
     */
    public double average() {
        double sum = STARTING_VALUE;
        for (int score : testScores) {
            sum += score;
        }
        return sum / testScores.length;
    }


    /**
     * Returns a String description of this Student object.
     *
     * @return description a String
     */
    @Override
    public String toString() {
        String result;
        DecimalFormat df = new DecimalFormat("#.###");

        result = firstName + " " + lastName + "\n";
        result += "Home Address:\n" + homeAddress + "\n";
        result += "School Address:\n" + schoolAddress;
        result += "\nTest Scores: " + Arrays.toString(testScores);
        result += "\nAverage Score: " + df.format(average());

        return result;
    }
}

