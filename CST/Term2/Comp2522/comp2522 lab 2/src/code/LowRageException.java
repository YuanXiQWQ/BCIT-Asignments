/**
 * Unchecked exception thrown when an Orc cannot go berserk due to insufficient rage.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public class LowRageException extends RuntimeException {
    public LowRageException(String message)
    {
        super(message);
    }
}
