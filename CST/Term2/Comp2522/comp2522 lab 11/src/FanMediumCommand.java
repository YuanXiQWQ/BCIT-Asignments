/**
 * Command to set the fan speed to medium.
 *
 * @author BCIT
 */
public class FanMediumCommand implements Command {
    private final Fan fan;

    /**
     * Constructs a FanMediumCommand with the specified fan.
     *
     * @param fan The fan to be set to medium speed.
     */
    public FanMediumCommand(final Fan fan) {
        this.fan = fan;
    }

    /**
     * Executes the command to set the fan speed to medium.
     */
    @Override
    public void execute() {
        fan.fanMedium();
    }

    /**
     * Undoes the command by turning the fan off.
     */
    @Override
    public void undo() {
        fan.turnOff(); // Simplified to turn off the fan
    }
}
