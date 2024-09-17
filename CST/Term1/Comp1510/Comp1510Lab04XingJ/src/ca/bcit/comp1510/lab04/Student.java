package ca.bcit.comp1510.lab04;

/**
 * This is a Lab4 Assignment, to represent a student.
 * This class is not a complete program, it has not had the main method.
 *
 * @author Jiarui Xing
 * @version 2024.2.9
 */
public class Student {
    /**
     * Student's first name
     */
    private String firstName;
    /**
     * Student's last name
     */
    private String lastName;
    /**
     * Student's year of birth
     */
    private int yearOfBirth;
    /**
     * Student's number
     */
    private String studentNumber;
    /**
     * Student's GPA
     */
    private double gpa;

    /**
     * Constructs a new student with the specified details.
     *
     * @param firstName     the first name
     * @param lastName      the last name
     * @param yearOfBirth   the year of birth
     * @param studentNumber the student number
     * @param gpa           the gpa
     */
    public Student(String firstName, String lastName, int yearOfBirth, String studentNumber, double gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.studentNumber = studentNumber;
        this.gpa = gpa;
    }

    //Accessors
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public double getGpa() {
        return gpa;
    }

    //Mutators
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Returns the full name of the student.
     *
     * @return the full name
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " "
                + yearOfBirth + " " + studentNumber
                + " " + gpa;
    }
}
