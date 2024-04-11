package q2;

/**
 * Represents a row in a timesheet, corresponding to a work package for a project.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class TimesheetRow {
    /**
     * Project number.
     */
    private int project;

    /**
     * Work package name.
     */
    private String workPackage;

    /**
     * Work hours for each day of the week.
     */
    private long hours;

    /**
     * Bit masks for each day of the week.
     */
    private static final long[] MASK = {
            0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L,
            0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L
    };

    /**
     * Unsigned bit masks for each day of the week.
     */
    private static final long[] UMASK = {
            0xFFFFFFFFFFFFFF00L, 0xFFFFFFFFFFFF00FFL,
            0xFFFFFFFFFF00FFFFL, 0xFFFFFFFF00FFFFFFL,
            0xFFFFFF00FFFFFFFFL, 0xFFFF00FFFFFFFFFFL,
            0xFF00FFFFFFFFFFFFL
    };

    /**
     * No-argument constructor.
     */
    public TimesheetRow() {
    }

    /**
     * Constructor with project, work package, and hours for each day.
     *
     * @param project     Project number.
     * @param workPackage Work package name.
     * @param hours       Daily hours worked (variable length parameter list).
     */
    public TimesheetRow(int project, String workPackage, float... hours) {
        this.project = project;
        this.workPackage = workPackage;
        setHours(hours);
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getWorkPackage() {
        return workPackage;
    }

    public void setWorkPackage(String workPackage) {
        this.workPackage = workPackage;
    }

    /**
     * Sets hours worked for each day of the week.
     *
     * @param hours Array of daily hours worked.
     */
    public void setHours(float[] hours) {
        this.hours = 0;
        for (int i = 0; i < hours.length; i++) {
            setHour(i, hours[i]);
        }
    }

    /**
     * Gets the hours worked on a specific day of the week.
     *
     * @param dayOfWeek The day of the week number (0 is Saturday, 6 is Friday).
     * @return Hours worked on the specified day.
     */
    public float getHour(int dayOfWeek) {
        return ((hours & MASK[dayOfWeek]) >>> (dayOfWeek * 8)) / 10.0f;
    }

    /**
     * Sets the hours worked on a specific day of the week.
     *
     * @param dayOfWeek The day of the week number (0 is Saturday, 6 is Friday).
     * @param hours     Hours worked on the specified day.
     */
    public void setHour(int dayOfWeek, float hours) {
        long scaledHours = (long) (hours * 10);
        this.hours = (this.hours & UMASK[dayOfWeek]) | (scaledHours << (dayOfWeek * 8));
    }

    /**
     * Returns a string representation of this object.
     *
     * @return A string representation of this object.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Project: ").append(project)
                .append(", Work Package: ").append(workPackage)
                .append(", Hours: ");
        for (int i = 0; i < 7; i++) {
            builder.append(getHour(i)).append(" ");
        }
        return builder.toString().trim();
    }
}
