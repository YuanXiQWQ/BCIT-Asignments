package q4;

/**
 * Calculates the value of e to the power of x.
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class Exponential {
    /**
     * This is 0.00001.
     */
    private static final double ZERO_POINT_ZERO_ZERO_ZERO_ZERO_ONE = 0.00001;

    /**
     * This is 100.
     */
    private static final double ONE_HUNDRED = 100;

    /**
     * This is -10.
     */
    private static final int NEGATIVE_TEN = -10;

    /**
     * This is 10.
     */
    private static final int TEN = 10;

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
        if (Math.abs(term) < ZERO_POINT_ZERO_ZERO_ZERO_ZERO_ONE || k > ONE_HUNDRED) {
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
        for (int i = NEGATIVE_TEN; i <= TEN; i++) {
            System.out.printf("%d\t%f\n", i, exp(i));
        }

        System.out.println("Question four was called and ran successfully.");
    }
}
