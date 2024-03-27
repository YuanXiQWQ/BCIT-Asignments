package q4;

/**
 * Calculates the value of e to the power of x.
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class Exponential {
    /**
     * Calculates the value of e to the power of x.
     *
     * @param x The exponent to raise e to.
     *
     * @return The calculated value of e^x.
     */
    public static double exp(double x) {
        return expCalc(x, 1, 1, 1);
    }

    private static double expCalc(double x, double term, double sum, int k) {
        if (Math.abs(term) < 0.00001 || k > 100) {
            return sum;
        }
        return expCalc(x, term * x / k, sum + term, k + 1);
    }

    /**
     * Prints the value of e to the power of x.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        System.out.println("x\te^x");
        for (int i = -10; i <= 10; i++) {
            System.out.printf("%d\t%f\n", i, exp(i));
        }
    }
}
