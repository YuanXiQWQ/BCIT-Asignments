/**
 * Unchecked exception thrown when an invalid damage amount is applied.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class DamageException extends RuntimeException {
    public DamageException(String message)
    {
        super(message);
    }
}
