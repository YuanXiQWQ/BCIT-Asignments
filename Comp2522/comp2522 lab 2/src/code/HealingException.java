/**
 * Unchecked exception thrown when an invalid healing amount is applied.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class HealingException extends RuntimeException {
    public HealingException(String message)
    {
        super(message);
    }
}
