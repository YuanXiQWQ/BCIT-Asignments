package ca.bcit.comp1510.lab05;

/**
 * This is a program with several methods.
 *
 * @author Jiarui Xing
 * @version 2024.2.15
 */
public class Mathematics {
    /**
     * It doesn't mention that which kind of pi,
     * so I just use Math.PI.
     *
     * @see Math
     */
    public static final double PI = Math.PI;

    /**
     * Returns the area of the circle with the specified radius.
     *
     * @param radius radius of the circle.
     * @return area as a double
     */
    public double getCircleArea(double radius) {
        return PI * Math.pow(radius, 2);
    }

    /**
     * Returns the sum of the positive integers between 0 and the specified
     * number inclusive. If the specified number is negative, returns zero.
     *
     * @param number upper bound
     * @return sum as an integer
     */
    public int sumOfInts(int number) {
        // Stop condition
        if (number < 0) {
            return 0;
            /*
            I think the stop condition should be (number == 0) or (number < 1)
            instead of "number is negative(number < 0)"
            because sumOfInts(0) returns 0 + sumOfInts(-1),
            sumOfInts(-1) returns 0
            the 0 + 0 is meaningless
            */
        }

        return number + sumOfInts(number - 1);
    }

    /**
     * Returns true if the specified value is positive, else false.
     *
     * @param number to test
     * @return true if the number is positive, else false.
     */
    public boolean isPositive(int number) {
        return number > 0;
    }

    /**
     * Returns true if the specified value is even, else false.
     *
     * @param number to test
     * @return true if number is even, else false.
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Returns sum of the even numbers between 0 and the specified value,
     * inclusive. The value can be positive, negative, or zero.
     *
     * @param number upper bound
     * @return sum of the even numbers between 0 and number
     */
    public int sumOfEvens(int number) {
        // 0 and Stop condition
        if (Math.abs(number) < 2) {
            return 0;
        }

        // Positive
        if (isPositive(number)) {
            if (isEven(number)) {
                return number + sumOfEvens(number - 2);
            }
            // toEven
            return sumOfEvens(number - 1);
        }

        // Negative
        if (isEven(number)) {
            return number + sumOfEvens(number + 2);
        }
        // toEven
        return sumOfEvens(number + 1);
    }
}
