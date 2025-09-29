import java.util.Objects;

/**
 * Represents an iPad, a type of iDevice with the purpose of learning. It contains
 * specific attributes such as whether it has a case and the operating system version.
 *
 * <p>
 * The iPad class overrides the `toString()` method to provide detailed information about
 * the device, including the presence of a case and the operating system version. It also
 * overrides the `equals()` and `hashCode()` methods based on the operating system
 * version.
 * </p>
 *
 * <p>
 * Purpose of this device is defined as "learning".
 * </p>
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @see IDevice
 * @see IDevice#printDetails()
 * @see Object#toString()
 * @see Object#equals(Object)
 * @see Object#hashCode()
 */
public class IPad extends IDevice {
    private boolean hasCase;
    private String osVersion;

    /**
     * Constructor for IPad
     *
     * @param hasCase   whether the iPad has a case
     * @param osVersion the operating system version
     */
    public IPad(boolean hasCase, String osVersion)
    {
        super("learning");
        this.hasCase = hasCase;
        this.osVersion = osVersion;
    }

    /**
     * Get whether the iPad has a case
     *
     * @return true if the iPad has a case
     */
    public boolean isHasCase()
    {
        return hasCase;
    }

    /**
     * Set whether the iPad has a case
     *
     * @param hasCase true if the iPad has a case
     */
    public void setHasCase(boolean hasCase)
    {
        this.hasCase = hasCase;
    }

    /**
     * Get the operating system version
     *
     * @return the operating system version
     */
    public String getOsVersion()
    {
        return osVersion;
    }

    /**
     * Set the operating system version
     *
     * @param osVersion the operating system version
     */
    public void setOsVersion(String osVersion)
    {
        this.osVersion = osVersion;
    }

    /**
     * Print the details
     *
     * @see IDevice#printDetails()
     */
    @Override
    public void printDetails()
    {
        System.out.println(this);
    }

    /**
     * Overridden toString function
     *
     * @return the string representation of the object
     */
    @Override
    public String toString()
    {
        return super.toString() + ", Has case: " + hasCase + ", OS Version: " + osVersion;
    }

    /**
     * Overridden equals function
     *
     * @param obj the object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }

        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final IPad other = (IPad) obj;

        return Objects.equals(this.osVersion, other.osVersion);
    }

    /**
     * Overridden hashCode function
     *
     * @return the hashcode of the object
     */
    @Override
    public int hashCode()
    {
        return Objects.hashCode(osVersion);
    }
}
