/**
 * Represents an iPod, a type of iDevice with the purpose of playing music. It contains
 * specific attributes such as the number of songs stored and maximum volume.
 *
 * <p>
 * The iPod class overrides the `toString()` method to provide detailed information about
 * the device, including the number of songs and maximum volume. It also overrides the
 * `equals()` and `hashCode()` methods based on the number of songs stored.
 * </p>
 *
 * <p>
 * Purpose of this device is defined as "music".
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
public class IPod extends IDevice {
    private int numberOfSongs;
    private double maxVolume;

    /**
     * Constructor for IPod
     *
     * @param numberOfSongs the number of songs in the iPod
     * @param maxVolume     the maximum volume of the iPod
     */
    public IPod(int numberOfSongs, double maxVolume)
    {
        super("music");
        this.numberOfSongs = numberOfSongs;
        this.maxVolume = maxVolume;
    }

    /**
     * Get the number of songs
     *
     * @return the number of songs
     */
    public int getNumberOfSongs()
    {
        return numberOfSongs;
    }

    /**
     * Set the number of songs
     *
     * @param numberOfSongs the number of songs
     */
    public void setNumberOfSongs(int numberOfSongs)
    {
        this.numberOfSongs = numberOfSongs;
    }

    /**
     * Get the maximum volume
     *
     * @return the maximum volume
     */
    public double getMaxVolume()
    {
        return maxVolume;
    }

    /**
     * Set the maximum volume
     *
     * @param maxVolume the maximum volume
     */
    public void setMaxVolume(double maxVolume)
    {
        this.maxVolume = maxVolume;
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
        return super.toString() + ", Number of Songs: " + numberOfSongs +
                ", Max Volume: " + maxVolume + " dB";
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

        final IPod other = (IPod) obj;

        return this.numberOfSongs == other.numberOfSongs;
    }

    /**
     * Overridden hashCode function
     *
     * @return the hashcode of the object
     */
    @Override
    public int hashCode()
    {
        return Integer.hashCode(numberOfSongs);
    }
}
