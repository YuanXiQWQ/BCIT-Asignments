package q2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * JUnit test class for TimesheetRow.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class TimesheetRowTest {

    /**
     * Test method for {@link q2.TimesheetRow#setHour(int, float)}.
     *
     * @see q2.TimesheetRow#setHour(int, float)
     */
    @Test
    public void testHourSettingAndGetting() {
        TimesheetRow row = new TimesheetRow();
        float[] hours = {3.2f, 1.1f, 4.5f, 5.5f, 3.2f, 2.0f, 5.0f};

        for (int i = 0; i < hours.length; i++) {
            row.setHour(i, hours[i]);
        }

        for (int i = 0; i < hours.length; i++) {
            assertEquals(hours[i], row.getHour(i), 0.1,
                    "Hours for day " + i + " should match.");
        }
    }

    /**
     * Test method for {@link q2.TimesheetRow#setHour(int, float)}.
     *
     * @see q2.TimesheetRow#setHour(int, float)
     */
    @Test
    public void testHourSettingBeyondRange() {
        TimesheetRow row = new TimesheetRow();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            row.setHour(0, 25.0f);
        });
        String expectedMessage = "Hours must be between 0.0 and 24.0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test method for {@link q2.TimesheetRow#setHour(int, float)}.
     *
     * @see q2.TimesheetRow#setHour(int, float)
     */
    @Test
    public void testSettingHourForInvalidDay() {
        TimesheetRow row = new TimesheetRow();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            row.setHour(-1, 10.0f);
        });

        String expectedMessage = "Invalid day of the week";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    /**
     * Test method for {@link q2.TimesheetRow#toString()}.
     *
     * @see q2.TimesheetRow#toString()
     */
    @Test
    public void testToString() {
        TimesheetRow row =
                new TimesheetRow(1, "WP1",
                        3.2f, 1.1f, 4.5f, 5.5f, 3.2f, 2.0f, 5.0f);
        String expected =
                "Project: 1, Work Package: WP1, Hours: 3.2 1.1 4.5 5.5 3.2 2.0 5.0";
        assertEquals(expected, row.toString(),
                "The toString method should format correctly.");
    }
}
