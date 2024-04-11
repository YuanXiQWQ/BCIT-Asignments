package q2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Timesheet.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class TimesheetTest {

    /**
     * A basic timesheet for testing.
     */
    private Timesheet timesheet;

    /**
     * Set up a basic timesheet for testing.
     */
    @BeforeEach
    void setUp() {
        // Assumes that April 12, 2024, is a Friday.
        timesheet = new Timesheet("12345", LocalDate.of(2024, 4, 12));
    }

    /**
     * Test the Timesheet constructor with parameters and getters.
     */
    @Test
    void testConstructorAndGetter() {
        assertEquals("12345", timesheet.getEmpNum());
        assertEquals(LocalDate.of(2024, 4, 12), timesheet.getEndWeek());
    }

    /**
     * Test setting the end week date to a non-Friday throws an IllegalArgumentException.
     */
    @Test
    void testSetEndWeek_NotFriday() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            // Assuming April 11, 2024, is not a Friday.
            timesheet.setEndWeek(LocalDate.of(2024, 4, 11));
        });

        String expectedMessage = "End week must be a Friday";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    /**
     * Test adding a TimesheetRow to the Timesheet.
     */
    @Test
    void testAddRowAndDetails() {
        TimesheetRow row =
                new TimesheetRow(1, "Package A", 3.2f, 1.1f, 4.5f, 5.5f, 3.2f, 2.0f,
                        5.0f);
        timesheet.addRow(row);

        assertEquals(1, timesheet.getDetails().size());
        assertEquals(row, timesheet.getDetails().getFirst());
    }

    /**
     * Test the toString method of Timesheet.
     */
    @Test
    void testToString() {
        TimesheetRow row1 = new TimesheetRow(1, "Package A", 8.0f);
        TimesheetRow row2 = new TimesheetRow(2, "Package B", 4.0f);
        timesheet.addRow(row1);
        timesheet.addRow(row2);

        String timesheetString = timesheet.toString();
        assertTrue(timesheetString.contains("Employee Number: 12345"));
        assertTrue(timesheetString.contains("End Week: 2024-04-12"));
        assertTrue(timesheetString.contains("Package A"));
        assertTrue(timesheetString.contains("Package B"));
        // This is a basic check. You might want to make the check more comprehensive.
    }

    /**
     * Test the no-argument constructor and setters.
     */
    @Test
    void testNoArgConstructorAndSetters() {
        Timesheet timesheetNoArg = new Timesheet();
        timesheetNoArg.setEmpNum("54321");
        timesheetNoArg.setEndWeek(LocalDate.of(2024, 4, 12));

        assertEquals("54321", timesheetNoArg.getEmpNum());
        assertEquals(LocalDate.of(2024, 4, 12), timesheetNoArg.getEndWeek());
    }
}
