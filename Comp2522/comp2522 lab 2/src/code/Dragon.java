import java.time.LocalDate;

/**
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Dragon extends Creature {
    private int firePower;

    // Throw info
    private static final String INFO_INVALID_FIREPOWER =
            "FirePower must be between 0 and 100";
    private static final String INFO_LOW_FIREPOWER_EXCEPTION =
            "Not enough firePower to breathe fire";

    /**
     * Constructor to create a Creature object.
     *
     * @param name        the name of the creature, must not be null or empty
     * @param dateOfBirth the date of birth of the creature, must not be in the future
     * @throws IllegalArgumentException if the name is invalid or dateOfBirth is in the
     *                                  future
     */
    public Dragon(String name, LocalDate dateOfBirth, int health, int firePower)
    {
        super(name, dateOfBirth);
        if (firePower < 0 || firePower > 100) {
            throw new IllegalArgumentException(INFO_INVALID_FIREPOWER);
        }
        this.firePower = firePower;
    }

    @Override
    public String getDetails()
    {
        return super.getDetails() + String.format(", FirePower: %d", firePower);
    }

    public void breatheFire(Creature target) throws IllegalArgumentException
    {
        if (firePower < 10) {
            throw new IllegalArgumentException(INFO_LOW_FIREPOWER_EXCEPTION);
        }
        firePower -= 10;
        target.takeDamage(20);
    }

    public void restoreFirePower(int amount)
    {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "FirePower amount must not be negative");
        }
        firePower = Math.min(100, firePower + amount);
    }
}
