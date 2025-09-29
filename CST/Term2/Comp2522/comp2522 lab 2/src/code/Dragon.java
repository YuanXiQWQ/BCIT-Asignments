import java.time.LocalDate;

/**
 * Represents a Dragon, a type of creature with firePower that can breathe fire.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Dragon extends Creature {
    private int firePower;

    // Exception messages
    private static final String INFO_INVALID_FIREPOWER =
            "FirePower must be between 0 and 100.";
    private static final String INFO_LOW_FIREPOWER =
            "LowFirePowerException: FirePower must be at least 10 to breathe fire.";
    private static final String INFO_INVALID_FIREPOWER_AMOUNT =
            "FirePower amount must not be negative.";

    /**
     * Constructor to create a Dragon object.
     *
     * @param name        the name of the dragon, must not be null or empty
     * @param dateOfBirth the date of birth of the dragon, must not be in the future
     * @param health      the health of the dragon, must be between 0 and 100
     * @param firePower   the firepower of the dragon, must be between 0 and 100
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Dragon(String name, LocalDate dateOfBirth, int health, int firePower)
    {
        super(name, dateOfBirth);

        // Validate health
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health must be between 0 and 100.");
        }
        this.health = health;

        // Validate firePower
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

    /**
     * Reduces firePower by 10 and deals 20 damage to the target creature. Throws a
     * LowFirePowerException if firePower is less than 10.
     *
     * @param target the creature to be attacked
     * @throws LowFirePowerException if firePower is less than 10
     */
    public void breatheFire(Creature target) throws LowFirePowerException
    {
        if (firePower < 10) {
            throw new LowFirePowerException(INFO_LOW_FIREPOWER);
        }
        firePower -= 10;
        target.takeDamage(20);
    }

    /**
     * Restores firePower by a specified amount but cannot exceed 100.
     *
     * @param amount the amount to restore firePower
     * @throws IllegalArgumentException if the amount is negative
     */
    public void restoreFirePower(int amount)
    {
        if (amount < 0) {
            throw new IllegalArgumentException(INFO_INVALID_FIREPOWER_AMOUNT);
        }
        firePower = Math.min(100, firePower + amount);
    }
}
