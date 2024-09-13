import java.time.LocalDate;

/**
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class Dragon extends Creature {
    /**
     * Constructor to create a Creature object.
     *
     * @param name        the name of the creature, must not be null or empty
     * @param dateOfBirth the date of birth of the creature, must not be in the future
     * @throws IllegalArgumentException if the name is invalid or dateOfBirth is in the
     *                                  future
     */
    public Dragon(String name, LocalDate dateOfBirth)
    {
        super(name, dateOfBirth);
    }
}
