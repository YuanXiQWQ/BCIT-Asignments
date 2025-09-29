/**
 * Checked exception thrown when an Elf tries to cast a spell with insufficient mana.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class LowManaException extends Exception {
    public LowManaException(String message)
    {
        super(message);
    }
}
