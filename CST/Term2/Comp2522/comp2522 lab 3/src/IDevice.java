/**
 * Represents a generic device with a specific purpose. This is the parent class for all
 * iDevices such as iPod, iPad, and iPhone. It contains a method to get the purpose and an
 * abstract method to print details.
 *
 * <p>
 * Each subclass must implement the abstract method `printDetails()` to display their
 * specific information.
 * </p>
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 */
public abstract class IDevice {
    protected String purpose;

    /**
     * Constructor for IDevice
     *
     * @param purpose purpose of device
     */
    public IDevice(String purpose)
    {
        this.purpose = purpose;
    }

    /**
     * Function to return purpose of device
     *
     * @return purpose of device
     */
    public String getPurpose()
    {
        return purpose;
    }

    /**
     * Abstract function to print device details
     */
    public abstract void printDetails();

    /**
     * Overridden toString function to print purpose of device.
     *
     * @return purpose of device
     */
    @Override
    public String toString()
    {
        return "Purpose: " + purpose;
    }
}
