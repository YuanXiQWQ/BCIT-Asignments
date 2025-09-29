import java.util.Random;

/**
 * The CreatureHealer class provides functionality to heal creatures with a random amount
 * of health. It catches and handles the HealingException if a negative healing amount is
 * applied.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class CreatureHealer {
    private final Random random;

    /**
     * Constructor to create a CreatureHealer object.
     */
    public CreatureHealer()
    {
        random = new Random();
    }

    /**
     * Heals a creature with a random amount between -20 and 50. Catches and handles the
     * HealingException if the healing amount is negative.
     *
     * @param creature the creature to be healed
     */
    public void healCreature(Creature creature)
    {
        int healAmount = random.nextInt(71) - 20;
        try {
            creature.heal(healAmount);
            System.out.println(
                    "Healed successfully: " + healAmount + " points of health restored.");
        } catch (HealingException e) {
            System.out.println("Healing failed:" + e.getMessage());
        }
    }
}
