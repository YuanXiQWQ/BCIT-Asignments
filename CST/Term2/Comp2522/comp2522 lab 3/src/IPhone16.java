/**
 * Represents the IPhone16, a specific model of iPhone with additional features. It
 * extends the IPhone class and adds attributes such as high-resolution camera and memory
 * size in gigabytes.
 *
 * <p>
 * The IPhone16 class overrides the `toString()` method to include the additional
 * attributes. It also overrides the `equals()` and `hashCode()` methods to compare based
 * on both the minutes remaining and the high-resolution camera attribute.
 * </p>
 *
 * <p>
 * IPhone16 objects are considered equal if they have the same amount of minutes remaining
 * and the same high-resolution camera value.
 * </p>
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @see IPhone
 * @see IPhone#getMinutesRemaining()
 * @see Object#toString()
 * @see Object#equals(Object)
 * @see Object#hashCode()
 */
public class IPhone16 extends IPhone {
    private boolean highResolutionCamera;
    private int gigabytesOfMemory;

    /**
     * Constructor for IPhone16
     *
     * @param minutesRemaining     the number of minutes remaining on the phone plan
     * @param carrier              the carrier
     * @param highResolutionCamera whether the phone has a high-resolution camera
     * @param gigabytesOfMemory    the memory size in gigabytes
     */
    public IPhone16(double minutesRemaining, String carrier, boolean highResolutionCamera,
                    int gigabytesOfMemory)
    {
        super(minutesRemaining, carrier);
        this.highResolutionCamera = highResolutionCamera;
        this.gigabytesOfMemory = gigabytesOfMemory;
    }

    /**
     * Get whether the phone has a high-resolution camera
     *
     * @return true if the phone has a high-resolution camera
     */
    public boolean isHighResolutionCamera()
    {
        return highResolutionCamera;
    }

    /**
     * Set whether the phone has a high-resolution camera
     *
     * @param highResolutionCamera true if the phone has a high-resolution camera
     */
    public void setHighResolutionCamera(boolean highResolutionCamera)
    {
        this.highResolutionCamera = highResolutionCamera;
    }

    /**
     * Get the memory size in gigabytes
     *
     * @return the memory size in gigabytes
     */
    public int getGigabytesOfMemory()
    {
        return gigabytesOfMemory;
    }

    /**
     * Set the memory size in gigabytes
     *
     * @param gigabytesOfMemory the memory size in gigabytes
     */
    public void setGigabytesOfMemory(int gigabytesOfMemory)
    {
        this.gigabytesOfMemory = gigabytesOfMemory;
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
        return super.toString() + ", High-Resolution Camera: " + highResolutionCamera +
                ", Memory (GB): " + gigabytesOfMemory;
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
        if(!super.equals(obj)) {
            return false;
        }

        if(this == obj) {
            return true;
        }

        if(getClass() != obj.getClass()) {
            return false;
        }

        final IPhone16 other = (IPhone16) obj;

        return this.highResolutionCamera == other.highResolutionCamera;
    }

    /**
     * Overridden hashCode function
     *
     * @return the hashcode of the object
     */
    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + Boolean.hashCode(highResolutionCamera);
        return result;
    }
}
