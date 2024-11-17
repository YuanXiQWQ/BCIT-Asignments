/**
 * An interface that defines the basic operations of a command, including executing
 * and undoing the command.
 *
 * @author BCIT
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the command.
     */
    void undo();
}
