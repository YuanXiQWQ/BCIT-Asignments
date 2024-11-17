import java.util.Stack;

/**
 * The Controller class acts as the invoker in the Command design pattern.
 * It stores and executes commands and maintains a history for undo operations.
 *
 * @author BCIT
 */
public class Controller {
    private Command currentCommand;
    private final Stack<Command> commandHistory;

    /**
     * Constructs a Controller with an empty command history.
     */
    public Controller() {
        commandHistory = new Stack<>();
    }

    /**
     * Sets the current command to be executed.
     *
     * @param command The command to be set.
     */
    public void setCommand(Command command) {
        this.currentCommand = command;
    }

    /**
     * Executes the current command and adds it to the history.
     */
    public void pressButton() {
        if (currentCommand != null) {
            currentCommand.execute();
            // Record the executed command
            commandHistory.push(currentCommand);
        }
    }

    /**
     * Undoes the last executed command.
     */
    public void pressUndo() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("No commands to undo.");
        }
    }
}
