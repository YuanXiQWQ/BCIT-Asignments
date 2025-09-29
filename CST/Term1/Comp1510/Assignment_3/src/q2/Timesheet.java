package q2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a timesheet for an employee, including details of work done on projects.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class Timesheet {
    /**
     * Employee number.
     */
    private String empNum;

    /**
     * End week date.
     */
    private LocalDate endWeek;

    /**
     * Details of work done on projects.
     */
    private final List<TimesheetRow> details = new ArrayList<>();

    /**
     * No-argument constructor.
     */
    public Timesheet() {
    }

    /**
     * Constructor with employee number and end week date.
     *
     * @param empNum  Employee number.
     * @param endWeek End week date, adjusted to the nearest Friday.
     */
    public Timesheet(String empNum, LocalDate endWeek) {
        this();
        this.empNum = empNum;
        setEndWeek(endWeek);
    }

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public LocalDate getEndWeek() {
        return endWeek;
    }

    /**
     * Sets the end week date, ensuring it is a Friday.
     *
     * @param endWeek The intended end week date.
     * @throws IllegalArgumentException if the provided date is not a Friday.
     */
    public void setEndWeek(LocalDate endWeek) throws IllegalArgumentException {
        if (endWeek.getDayOfWeek() != DayOfWeek.FRIDAY) {
            throw new IllegalArgumentException("End week must be a Friday");
        }
        this.endWeek = endWeek;
    }

    public List<TimesheetRow> getDetails() {
        return details;
    }

    /**
     * Adds a timesheet row to the details.
     *
     * @param row The timesheet row to add.
     */
    public void addRow(TimesheetRow row) {
        this.details.add(row);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Employee Number: ").append(empNum)
                .append(", End Week: ").append(endWeek)
                .append(", Details:\n");
        for (TimesheetRow row : details) {
            builder.append(row.toString()).append("\n");
        }
        return builder.toString().trim();
    }

    /**
     * Main method for quick demonstration.
     */
    public static void main(String[] args) {
        Timesheet timesheet = new Timesheet("12345",
                LocalDate.of(2024, 4, 12));
        timesheet.addRow(
                new TimesheetRow(1, "Package A", 3.2f, 1.1f, 4.5f, 5.5f, 3.2f, 2.0f,
                        5.0f));
        timesheet.addRow(
                new TimesheetRow(2, "Package B", 2.5f, 3.0f, 4.0f, 1.5f, 2.5f, 3.0f,
                        4.0f));
        timesheet.addRow(
                new TimesheetRow(3, "Package C", 5.0f, 4.5f, 3.0f, 2.5f, 1.0f, 3.5f,
                        4.0f));
        System.out.println(timesheet);
    }
}
