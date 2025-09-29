/**
 * Command to turn a device off.
 *
 * @author BCIT
 */
public class DeviceOffCommand implements Command {
    private final Device device;

    /**
     * Constructs a DeviceOffCommand with the specified device.
     *
     * @param device The device to be turned off.
     */
    public DeviceOffCommand(final Device device) {
        this.device = device;
    }

    /**
     * Executes the command to turn the device off.
     */
    @Override
    public void execute() {
        device.turnOff();
    }

    /**
     * Undoes the command by turning the device on.
     */
    @Override
    public void undo() {
        device.turnOn();
    }
}
