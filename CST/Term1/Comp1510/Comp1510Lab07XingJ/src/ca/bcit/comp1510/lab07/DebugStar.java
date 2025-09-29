package ca.bcit.comp1510.lab07;

/**
 * The class to help us get familiar with debugging.
 *
 * @author Xing Jiarui
 * @version 2024.3.4
 */
public class DebugStar {
    /**
     * To run the program
     *
     * @param args unused
     */
    public static void main(String[] args) {
        run("+", 6, 7);
        run("-", 6, 7);
        run(6);
    }

    /**
     * A method to get the operation
     *
     * @param key the operation key
     * @return the operation
     */
    private static Operation getOperation(final String key) {
        final Operation operation;
        if ("+".equals(key)) {
            operation = new Add();
        } else {
            operation = new Subtract();
        }
        return (operation);
    }

    /**
     * Give two numbers, and use the operation to get the result.
     * The result will be printed out, the return is void.
     *
     * @param key the operation key
     * @param a the first number
     * @param b the second number
     */
    private static void run(final String key, final int a, final int b) {
        final Operation operation;
        final int result;
        operation = getOperation(key);
        result = operation.perform(a, b);
        System.out.println("result = " + result);
    }

    /**
     * Give one number and calculate its factorial
     *
     * @param n one number
     */
    private static void run(final int n) {
        final Factorial factorial;
        final int result;
        factorial = new Factorial();
        result = factorial.perform(n);
        System.out.println("result = " + result);
    }
}

/**
 * An interface that defines the template of operation method
 * *I think it is easier to understand if we create a new class which type is Interface
 */
interface Operation {
    int perform(int a, int b);
}

/**
 * Define add operation from the interface Operation
 */
class Add implements Operation {
    @Override
    public int perform(final int a, final int b) {
        return (a + b);
    }
}

/**
 * Define subtract operation from the interface Operation
 */
class Subtract implements Operation {
    @Override
    public int perform(final int a, final int b) {
        return (a - b);
    }
}

/**
 * Creat factorial operation
 */
class Factorial {
    int perform(final int n) {
        int ret;
        ret = 1;
        for (int i = 1; i < n; i++) {
            ret *= i;
        }
        return (ret);
    }
}

