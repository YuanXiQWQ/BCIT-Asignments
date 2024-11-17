/**
 * The Controller class acts as the invoker in the Command design pattern.
 * It stores and executes commands and maintains a history for undo operations.
 *
 * @author BCIT
 */
public class Controller {
    private Command currentCommand;
    private Command lastCommand;

    /**
     * Constructs a Controller.
     */
    public Controller() {
        this.currentCommand = null;
        this.lastCommand = null;
    }

    /**
     * Sets the current command to be executed.
     *
     * @param command The command to be set.
     */
    public void setCommand(final Command command) {
        this.currentCommand = command;
    }

    /**
     * Executes the current command and stores it as the last command for undo.
     */
    public void pressButton() {
        if (currentCommand != null) {
            currentCommand.execute();
            // Store the current command as the last command(for undo)
            lastCommand = currentCommand;
        }
    }

    /**
     * Undoes the last executed command.
     */
    public void pressUndo() {
        if (lastCommand != null) {
            lastCommand.undo();
            lastCommand = null;
        } else {
            System.out.println("No commands to undo.");
        }
    }
}
