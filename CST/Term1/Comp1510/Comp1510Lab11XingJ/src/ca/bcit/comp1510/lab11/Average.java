package ca.bcit.comp1510.lab11;

/**
 * Average class to compute the average of command line integer arguments.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class Average {
    /**
     * The main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments");
        } else {
            double sum = 0;
            int count = 0;
            for (String arg : args) {
                try {
                    int num = Integer.parseInt(arg);
                    sum += num;
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Non-integer value '" + arg +
                            "' encountered. Skipping...");
                }
            }
            if (count > 0) {
                double average = sum / count;
                System.out.println("Average: " + average);
            } else {
                System.out.println("No valid integer arguments provided.");
            }
        }
    }
}
