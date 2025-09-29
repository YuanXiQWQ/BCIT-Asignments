import java.time.LocalDate;

/**
 * Test class for the fantasy creature management system. Demonstrates polymorphism,
 * instanceof, and getClass() usage, as well as exception handling.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class CreatureTest {
    public static void main(String[] args)
    {
        // Using polymorphism
        Creature dragon = new Dragon("Smaug", LocalDate.of(100, 1, 1), 100, 100);
        Creature elf = new Elf("Legolas", LocalDate.of(200, 1, 1), 100, 50);
        Creature orc = new Orc("Azog", LocalDate.of(150, 1, 1), 100, 30);

        // Calling getDetails() for each creature
        System.out.println(dragon.getDetails());
        System.out.println(elf.getDetails());
        System.out.println(orc.getDetails());

        // Using instanceof and getClass()
        if (dragon instanceof Dragon) {
            System.out.println(dragon.name + " is an instance of Dragon class");
        }
        if (elf.getClass() == Elf.class) {
            System.out.println(elf.name + " is an instance of Elf class");
        }
        if (orc instanceof Orc) {
            System.out.println(orc.name + " is an instance of Orc class");
        }

        // Exception handling
        try {
            ((Dragon) dragon).breatheFire(orc);
            ((Elf) elf).castSpell(dragon);
            ((Orc) orc).berserk(elf);
        } catch (LowFirePowerException | LowManaException e) {
            System.out.println("Caught checked exception: " + e.getMessage());
        } catch (LowRageException e) {
            System.out.println("Caught unchecked exception: " + e.getMessage());
        }

        // Triggering DamageException
        try {
            dragon.takeDamage(-10);
        } catch (DamageException e) {
            System.out.println("Caught DamageException: " + e.getMessage());
        }

        // Triggering HealingException
        try {
            elf.heal(-20);
        } catch (HealingException e) {
            System.out.println("Caught HealingException: " + e.getMessage());
        }

        // Using CreatureHealer
        CreatureHealer healer = new CreatureHealer();
        healer.healCreature(dragon);
        healer.healCreature(elf);
        healer.healCreature(orc);
    }
}
