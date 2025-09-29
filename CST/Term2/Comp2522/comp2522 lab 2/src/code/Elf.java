import java.time.LocalDate;

/**
 * Represents an Elf, a type of creature with mana to cast spells.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Elf extends Creature {
    private int mana;

    // Exception messages
    private static final String INFO_INVALID_MANA = "Mana must be between 0 and 50.";
    private static final String INFO_LOW_MANA =
            "LowManaException: Mana must be at least 5 to cast a spell.";
    private static final String INFO_INVALID_MANA_AMOUNT =
            "Mana amount must not be negative.";

    /**
     * Constructor to create an Elf object.
     *
     * @param name        the name of the elf, must not be null or empty
     * @param dateOfBirth the date of birth of the elf, must not be in the future
     * @param health      the health of the elf, must be between 0 and 100
     * @param mana        the mana of the elf, must be between 0 and 50
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Elf(final String name, final LocalDate dateOfBirth, final int health,
               final int mana)
    {
        super(name, dateOfBirth);

        // Validate health
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health must be between 0 and 100.");
        }
        this.health = health;

        // Validate mana
        if (mana < 0 || mana > 50) {
            throw new IllegalArgumentException(INFO_INVALID_MANA);
        }
        this.mana = mana;
    }

    /**
     * Returns the details of the Elf.
     *
     * @return the details of the Elf
     */
    @Override
    public String getDetails()
    {
        return super.getDetails() + String.format(", Mana: %d", mana);
    }

    /**
     * Reduces mana by 5 and deals 10 damage to the target creature. Throws a
     * LowManaException if mana is less than 5.
     *
     * @param target the creature to be attacked
     * @throws LowManaException if mana is less than 5
     */
    public void castSpell(final Creature target) throws LowManaException
    {
        if (mana < 5) {
            throw new LowManaException(INFO_LOW_MANA);
        }
        mana -= 5;
        target.takeDamage(10);
    }

    /**
     * Restores mana by a specified amount but cannot exceed 50.
     *
     * @param amount the amount to restore mana
     * @throws IllegalArgumentException if the amount is negative
     */
    public void restoreMana(final int amount)
    {
        if (amount < 0) {
            throw new IllegalArgumentException(INFO_INVALID_MANA_AMOUNT);
        }
        mana = Math.min(50, mana + amount);
    }
}
