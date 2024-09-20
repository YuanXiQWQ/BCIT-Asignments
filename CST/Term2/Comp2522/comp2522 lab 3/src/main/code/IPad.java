import java.util.Objects;

/**
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 */
public class IPad extends IDevice {
    private final boolean hasCase;
    private final String osVersion;

    public IPad(boolean hasCase, String osVersion)
    {
        super("learning");
        this.hasCase = hasCase;
        this.osVersion = osVersion;
    }

    public boolean isHasCase()
    {
        return hasCase;
    }

    public String getOsVersion()
    {
        return osVersion;
    }

    @Override
    public void printDetails()
    {
        System.out.println("Has case: " + hasCase);
        System.out.println("OS version: " + osVersion);
    }

    @Override
    public String toString()
    {
        return "Has case: " + hasCase + "\nOS version: " + osVersion;
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

        final IPad other = (IPad) obj;

        return this.hasCase == other.hasCase &&
                !Objects.equals(this.osVersion, other.osVersion);
    }
}
