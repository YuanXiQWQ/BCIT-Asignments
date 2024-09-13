import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a generic fantasy creature with basic properties such as name, date of
 * birth, and health. Provides functionality for taking damage, healing, and calculating
 * the creature's age.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Creature {
    protected final String name;
    protected final LocalDate dateOfBirth;
    protected int health;

    // Exception messages
    private static final String INFO_INVALID_NAME = "Name cannot be null or empty.";
    private static final String INFO_INVALID_DATE =
            "Date of birth cannot be null or in the future.";
    private static final String INFO_DAMAGE_EXCEPTION =
            "DamageException: Damage cannot be negative.";
    private static final String INFO_HEALING_EXCEPTION =
            "HealingException: Healing amount cannot be negative.";

    // Details template
    private static final String DETAILS_TEMPLATE =
            "Name: %s, Date of Birth: %s, Age: %d, Health: %d";

    /**
     * Constructor to create a Creature object.
     *
     * @param name        the name of the creature, must not be null or empty
     * @param dateOfBirth the date of birth of the creature, must not be in the future
     * @throws IllegalArgumentException if the name is invalid or dateOfBirth is in the
     *                                  future
     */
    public Creature(final String name, final LocalDate dateOfBirth)
    {
        // Validate the name is not null or empty
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(INFO_INVALID_NAME);
        }

        // Validate the date of birth is not null and is not in the future
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(INFO_INVALID_DATE);
        }

        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.health = 100;
    }

    /**
     * Checks if the creature is alive.
     *
     * @return true if the creature's health is greater than 0, false otherwise
     */
    public boolean isAlive()
    {
        return health > 0;
    }

    /**
     * Reduces the creature's health by a specified amount of damage. If the damage amount
     * is negative, an exception is thrown. The health will never drop below 0.
     *
     * @param damage the amount of damage to apply
     * @throws DamageException if the damage is negative
     */
    public void takeDamage(final int damage)
    {
        // Validate damage is not negative
        if (damage < 0) {
            throw new DamageException(INFO_DAMAGE_EXCEPTION);
        }
        // Set to 0 if health will be less than 0 after taking damage
        health = Math.max(0, health - damage);
    }

    /**
     * Increases the creature's health by a specified amount. If the healing amount is
     * negative, an exception is thrown. The health will not exceed 100.
     *
     * @param healAmount the amount of health to restore
     * @throws HealingException if the heal amount is negative
     */
    public void heal(final int healAmount)
    {
        // Validate healAmount is not negative
        if (healAmount < 0) {
            throw new HealingException(INFO_HEALING_EXCEPTION);
        }

        // Set to 100 if health will be more than 100 after healing
        health = Math.min(100, health + healAmount);
    }

    /**
     * Calculates the age of the creature in years based on its date of birth.
     *
     * @return the creature's age in years
     */
    public int getAgeYears()
    {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Provides a formatted string containing the creature's details, including its name,
     * date of birth, age, and current health.
     *
     * @return a string representing the creature's details
     */
    public String getDetails()
    {
        return String.format(
                DETAILS_TEMPLATE,
                name,
                dateOfBirth,
                getAgeYears(),
                health);
    }
}
