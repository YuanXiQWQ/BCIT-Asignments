/**
 * Command to set the fan speed to high.
 *
 * @author BCIT
 */
public class FanHighCommand implements Command {
    private final Fan fan;

    /**
     * Constructs a FanHighCommand with the specified fan.
     *
     * @param fan The fan to be set to high speed.
     */
    public FanHighCommand(final Fan fan) {
        this.fan = fan;
    }

    /**
     * Executes the command to set the fan speed to high.
     */
    @Override
    public void execute() {
        fan.fanHigh();
    }

    /**
     * Undoes the command by turning the fan off.
     */
    @Override
    public void undo() {
        fan.turnOff(); // Simplified to turn off the fan
    }
}
