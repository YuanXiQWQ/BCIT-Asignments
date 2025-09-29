/**
 * Command to turn a device on.
 *
 * @author BCIT
 */
public class DeviceOnCommand implements Command {
    private final Device device;

    /**
     * Constructs a DeviceOnCommand with the specified device.
     *
     * @param device The device to be turned on.
     */
    public DeviceOnCommand(final Device device) {
        this.device = device;
    }

    /**
     * Executes the command to turn the device on.
     */
    @Override
    public void execute() {
        device.turnOn();
    }

    /**
     * Undoes the command by turning the device off.
     */
    @Override
    public void undo() {
        device.turnOff();
    }
}
