/**
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 */
public class IPhone extends IDevice {
    private final double minutesRemaining;
    private final String carrier;

    public IPhone(double minutesRemaining, String carrier)
    {
        super("talking");
        this.minutesRemaining = minutesRemaining;
        this.carrier = carrier;
    }

    public double getMinutesRemaining()
    {
        return minutesRemaining;
    }

    public String getCarrier()
    {
        return carrier;
    }

    @Override
    public void printDetails()
    {
        System.out.println("Minutes remaining: " + minutesRemaining);
        System.out.println("Carrier: " + carrier);
    }

    @Override
    public String toString()
    {
        return "Minutes remaining: " + minutesRemaining + "\nCarrier: " + carrier;
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

        final IPhone other = (IPhone) obj;

        return this.minutesRemaining == other.minutesRemaining &&
                this.carrier.equals(other.carrier);
    }
}
