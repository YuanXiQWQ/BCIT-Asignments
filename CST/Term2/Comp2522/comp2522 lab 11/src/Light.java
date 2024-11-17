/**
 * Represents a Light device that can be turned on and off.
 *
 * @author BCIT
 */
public class Light implements Device {
    /**
     * Turns the light on and prints a message to the terminal.
     */
    @Override
    public void turnOn() {
        System.out.println("Light is ON");
    }

    /**
     * Turns the light off and prints a message to the terminal.
     */
    @Override
    public void turnOff() {
        System.out.println("Light is OFF");
    }
}
