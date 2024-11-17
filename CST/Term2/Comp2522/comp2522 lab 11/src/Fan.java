/**
 * Represents a Fan device that can be turned on, off, and have its speed adjusted.
 *
 * @author BCIT
 */
public class Fan implements Device {
    /**
     * Turns the fan on and prints a message to the terminal.
     */
    @Override
    public void turnOn() {
        System.out.println("Fan is ON");
    }

    /**
     * Turns the fan off and prints a message to the terminal.
     */
    @Override
    public void turnOff() {
        System.out.println("Fan is OFF");
    }

    /**
     * Sets the fan speed to low and prints a message to the terminal.
     */
    public void fanLow() {
        System.out.println("Fan is set to LOW speed");
    }

    /**
     * Sets the fan speed to medium and prints a message to the terminal.
     */
    public void fanMedium() {
        System.out.println("Fan is set to MEDIUM speed");
    }

    /**
     * Sets the fan speed to high and prints a message to the terminal.
     */
    public void fanHigh() {
        System.out.println("Fan is set to HIGH speed");
    }
}
