package q1;

import java.util.ArrayList;

import static q1.Student.STARTING_VALUE;

/**
 * A class to represent a course in a school.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class Course {

    /**
     * The students enrolled in this course.
     */
    private final ArrayList<Student> students = new ArrayList<>();

    /**
     * The number of students in this course.
     */
    int studentNum = STARTING_VALUE;

    /**
     * This class keep track of up to 5 students in a course.
     */
    private static final int MAX_TRACKS = 5;

    /**
     * Constructs a Course object only need course name.
     *
     * @param courseName the name of the course
     */
    public Course(String courseName) {
    }

    /**
     * Function to add student in course, maximum is 5.
     *
     * @param student the student
     * @throws IllegalStateException if the course is full
     */
    public void addStudent(Student student) throws IllegalStateException {
        if (students.size() < MAX_TRACKS) {
            students.add(student);
            // track the number of students.
            studentNum = students.size();
        } else {
            throw new IllegalStateException("Course is full");
        }
    }

    /**
     * Calculates the average score of all students in this course.
     *
     * @return the average score
     */
    public double average() {
        double sum = 0;
        for (Student student : students) {
            sum += student.average();
        }
        return sum / studentNum;
    }

    /**
     * Prints all students in this course.
     */
    public void role() {
        System.out.println(students);
    }
}
