/**
 * Command to set the fan speed to low.
 *
 * @author BCIT
 */
public class FanLowCommand implements Command {
    private final Fan fan;

    /**
     * Constructs a FanLowCommand with the specified fan.
     *
     * @param fan The fan to be set to low speed.
     */
    public FanLowCommand(final Fan fan) {
        this.fan = fan;
    }

    /**
     * Executes the command to set the fan speed to low.
     */
    @Override
    public void execute() {
        fan.fanLow();
    }

    /**
     * Undoes the command by turning the fan off.
     */
    @Override
    public void undo() {
        fan.turnOff(); // Simplified to turn off the fan
    }
}
