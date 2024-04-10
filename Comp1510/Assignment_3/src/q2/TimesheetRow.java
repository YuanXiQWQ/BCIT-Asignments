package q2;


/**
 * This is where you put your description about what this class does. You don't have to write an
 * essay but you should describe exactly what it does. Describing it will help you to understand the
 * programming problem better.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class TimesheetRow {
    private int project;
    private String workPackage;
    private long hours;

    /**
     * The constructor of this class.
     */
    public TimesheetRow(int project, String workPackage, float... hours) {
        this.project = project;
        this.workPackage = workPackage;
        setHours(hours);
    }

    /**
     * The default constructor of this class.
     */
    public TimesheetRow() {

    }

    /**
     * Setter of project
     *
     * @param project the project
     */
    public void setProject(int project) {
        this.project = project;
    }

    /**
     * Getter of project
     *
     * @return the project
     */
    public int getProject() {
        return project;
    }

    /**
     * Setter of work package
     *
     * @param workPackage the work package
     */
    public void setWorkPackage(String workPackage) {
        this.workPackage = workPackage;
    }

    /**
     * Getter of work package
     *
     * @return the work package
     */
    public String getWorkPackage() {
        return workPackage;
    }

    /**
     * Setter of hours
     *
     * @param hours the hours
     */
    public void setHours(float... hours) {
    }

    /**
     * Getter of hours
     *
     * @param day the day
     *
     * @return the hours
     */
    public float getHour(int day) {
        return 0.0f;
    }

    /**
     * The toString method of this class.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "";
    }
}
