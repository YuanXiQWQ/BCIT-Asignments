/**
 * Represents an iPhone, a type of iDevice with the purpose of talking. It contains
 * specific attributes such as the number of minutes remaining on the phone plan and the
 * carrier.
 *
 * <p>
 * The iPhone class overrides the `toString()` method to provide detailed information
 * about the device, including the minutes remaining and the carrier. It also overrides
 * the `equals()` and `hashCode()` methods based on the minutes remaining.
 * </p>
 *
 * <p>
 * Purpose of this device is defined as "talking".
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
public class IPhone extends IDevice {
    private double minutesRemaining;
    private String carrier;

    /**
     * Constructor for IPhone
     *
     * @param minutesRemaining the number of minutes remaining on the phone plan
     * @param carrier          the carrier
     */
    public IPhone(double minutesRemaining, String carrier)
    {
        super("talking");
        this.minutesRemaining = minutesRemaining;
        this.carrier = carrier;
    }

    /**
     * Get the number of minutes remaining
     *
     * @return the number of minutes remaining
     */
    public double getMinutesRemaining()
    {
        return minutesRemaining;
    }

    /**
     * Set the number of minutes remaining
     *
     * @param minutesRemaining the number of minutes remaining
     */
    public void setMinutesRemaining(double minutesRemaining)
    {
        this.minutesRemaining = minutesRemaining;
    }

    /**
     * Get the carrier
     *
     * @return the carrier
     */
    public String getCarrier()
    {
        return carrier;
    }

    /**
     * Set the carrier
     *
     * @param carrier the carrier
     */
    public void setCarrier(String carrier)
    {
        this.carrier = carrier;
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
        return super.toString() + ", Minutes Remaining: " + minutesRemaining +
                ", Carrier: " + carrier;
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

        final IPhone other = (IPhone) obj;

        return Double.compare(this.minutesRemaining, other.minutesRemaining) == 0;
    }

    /**
     * Overridden hashCode function
     *
     * @return the hashcode of the object
     */
    @Override
    public int hashCode()
    {
        return Double.hashCode(minutesRemaining);
    }
}
