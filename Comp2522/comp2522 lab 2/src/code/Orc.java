import java.time.LocalDate;

/**
 * Represents an Orc, a type of creature with rage that can go berserk.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Orc extends Creature {
    private int rage;

    // Exception messages
    private static final String INFO_INVALID_RAGE = "Rage must be between 0 and 30.";
    private static final String INFO_LOW_RAGE =
            "LowRageException: Rage must be at least 5 to go berserk.";
    private static final String INFO_INVALID_RAGE_AMOUNT =
            "Rage amount must not be negative.";

    /**
     * Constructor to create an Orc object.
     *
     * @param name        the name of the orc, must not be null or empty
     * @param dateOfBirth the date of birth of the orc, must not be in the future
     * @param health      the health of the orc, must be between 0 and 100
     * @param rage        the rage of the orc, must be between 0 and 30
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Orc(final String name, final LocalDate dateOfBirth, final int health,
               final int rage)
    {
        super(name, dateOfBirth);

        // Validate health
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health must be between 0 and 100.");
        }
        this.health = health;

        // Validate rage
        if (rage < 0 || rage > 30) {
            throw new IllegalArgumentException(INFO_INVALID_RAGE);
        }
        this.rage = rage;
    }

    @Override
    public String getDetails()
    {
        return super.getDetails() + String.format(", Rage: %d", rage);
    }

    /**
     * Increases rage by 5. If rage exceeds 20, the Orc goes berserk and deals double
     * damage (30 damage) to the target creature. If rage is below 5, throws
     * LowRageException.
     *
     * @param target the creature to be attacked
     * @throws LowRageException if rage is below 5
     */
    public void berserk(Creature target)
    {
        if (rage < 5) {
            throw new LowRageException(INFO_LOW_RAGE);
        }
        rage += 5;
        int damage = (rage > 20) ? 30 : 15;
        target.takeDamage(damage);
    }

    /**
     * Increases rage by a specified amount but cannot exceed 30.
     *
     * @param amount the amount to increase rage
     * @throws IllegalArgumentException if the amount is negative
     */
    public void increaseRage(int amount)
    {
        if (amount < 0) {
            throw new IllegalArgumentException(INFO_INVALID_RAGE_AMOUNT);
        }
        rage = Math.min(30, rage + amount);
    }
}
