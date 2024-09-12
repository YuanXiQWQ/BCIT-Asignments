import java.time.LocalDate;
import java.time.Period;

/**
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Creature {
    private final String name;
    private final LocalDate dateOfBirth;
    private int health;

    private static final String INFO_INVALID_NAME = "name cannot be null";
    private static final String INFO_INVALID_DATE =
            "dateOfBirth cannot be null or in the future";
    private static final String INFO_DAMAGE_EXCEPTION =
            "Damage Exception: cannot take negative damage";
    private static final String INFO_HEALING_EXCEPTION =
            "Healing Exception: healing amount must not be negative";
    private static final String DETAILS_TEMPLATE =
            "Name: %s, Date of Birth: %s, Age: %d, Health: %d";

    /**
     * Constructor for objects of class Creature
     *
     * @param name        the name of the creature
     * @param dateOfBirth the date of birth of the creature
     */
    public Creature(String name, LocalDate dateOfBirth)
    {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(INFO_INVALID_NAME);
        } else if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(INFO_INVALID_DATE);
        } else {
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.health = 100;
        }
    }

    private boolean isAlive()
    {
        return health > 0;
    }

    public void takeDamage(int damage)
    {
        if (damage < 0) {
            throw new IllegalArgumentException(INFO_DAMAGE_EXCEPTION);
        }
        // Set to 0 if health will be less than 0 after taking damage.
        health = Math.max(0, health - damage);
    }

    public void heal(int healAmount)
    {
        if (healAmount < 0) {
            throw new IllegalArgumentException(INFO_HEALING_EXCEPTION);
        }

        // Set to 100 if health will be more than 100 after healing.
        health = Math.min(100, health + healAmount);
    }

    public int getAgeYears()
    {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public String getDetails()
    {
        return String.format(DETAILS_TEMPLATE, name, dateOfBirth, getAgeYears(), health);
    }
}
