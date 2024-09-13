/**
 * Checked exception thrown when a Dragon tries to breathe fire with insufficient
 * firePower.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class LowFirePowerException extends Exception {
    public LowFirePowerException(String message)
    {
        super(message);
    }
}
