/**
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 */
public class IPod extends IDevice {
    private final int numberOfSongs;
    private final double maxVolume;

    public IPod(int numberOfSongs, double maxVolume)
    {
        super("music");
        this.numberOfSongs = numberOfSongs;
        this.maxVolume = maxVolume;
    }

    public int getNumberOfSongs()
    {
        return numberOfSongs;
    }

    public double getMaxVolume()
    {
        return maxVolume;
    }

    @Override
    public void printDetails()
    {
        System.out.println("Number of songs: " + numberOfSongs);
        System.out.println("Max volume: " + maxVolume);
    }

    @Override
    public String toString()
    {
        return "Number of songs: " + numberOfSongs + "\nMax volume: " + maxVolume;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null) {
            return false;
        }

        if(getClass() != obj.getClass()) {
            return false;
        }

        final IPod other = (IPod) obj;

        return this.numberOfSongs == other.numberOfSongs &&
                this.maxVolume == other.maxVolume;
    }
}
