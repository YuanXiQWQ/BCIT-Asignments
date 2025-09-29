import java.util.Scanner;

/**
 * The Main class contains the main method to test the Command design pattern implementation.
 *
 * @author BCIT
 */
public class Main {
    /**
     * The main method creates devices, commands, and a controller, then allows the user
     * to interact with the devices through a console-based menu.
     *
     * @param args unused.
     */
    public static void main(final String[] args) {
        // Create receiver objects
        final Light livingRoomLight = new Light();
        final Fan ceilingFan = new Fan();

        // Create command objects
        final Command lightOn = new DeviceOnCommand(livingRoomLight);
        final Command lightOff = new DeviceOffCommand(livingRoomLight);
        final Command fanOn = new DeviceOnCommand(ceilingFan);
        final Command fanOff = new DeviceOffCommand(ceilingFan);
        final Command fanLow = new FanLowCommand(ceilingFan);
        final Command fanMedium = new FanMediumCommand(ceilingFan);
        final Command fanHigh = new FanHighCommand(ceilingFan);

        // Create invoker object
        final Controller remote = new Controller();
        final Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // User interaction loop
        while (running) {
            printMenu();

            final String input = scanner.nextLine();
            switch (input) {
                case "1":
                    remote.setCommand(lightOn);
                    remote.pressButton();
                    break;
                case "2":
                    remote.setCommand(lightOff);
                    remote.pressButton();
                    break;
                case "3":
                    remote.setCommand(fanOn);
                    remote.pressButton();
                    break;
                case "4":
                    remote.setCommand(fanLow);
                    remote.pressButton();
                    break;
                case "5":
                    remote.setCommand(fanMedium);
                    remote.pressButton();
                    break;
                case "6":
                    remote.setCommand(fanHigh);
                    remote.pressButton();
                    break;
                case "7":
                    remote.setCommand(fanOff);
                    remote.pressButton();
                    break;
                case "8":
                    remote.pressUndo();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Program exited.");
    }

    /**
     * Prints the menu options to the console using StringBuilder for simplification.
     */
    private static void printMenu() {
        final StringBuilder menu = new StringBuilder();
        menu.append("\nEnter a command:\n");
        menu.append("1: Turn ON the Light\n");
        menu.append("2: Turn OFF the Light\n");
        menu.append("3: Turn ON the Fan\n");
        menu.append("4: Set Fan to LOW speed\n");
        menu.append("5: Set Fan to MEDIUM speed\n");
        menu.append("6: Set Fan to HIGH speed\n");
        menu.append("7: Turn OFF the Fan\n");
        menu.append("8: Undo last command\n");
        menu.append("0: Exit\n");
        menu.append("Your choice: ");
        System.out.print(menu);
    }
}
