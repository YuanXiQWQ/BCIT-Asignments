import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Jerry Xing | Student ID: A01354731 | Set: 3G
 * @author BCIT COMP3760 for function getPermutations
 *         <p>
 *         A utility class for solving the "Job Assignment" problem using a
 *         brute-force approach.
 *         After reading an NxN benefit matrix, it uses the getPermutations
 *         algorithm to generate
 *         all permutations from 0 to N-1, calculates the total benefit for
 *         each, and selects the
 *         assignment with the maximum total value.
 */
public class JobAssignmentFinder {
    // Constants
    // Initialization Constants
    private static final int UNINITIALIZED_SIZE = -1;
    private static final int MINIMUM_MATRIX_SIZE = 1;
    private static final int MIN_COLUMN_WIDTH = 1;

    // Permutation Algorithm Constants
    private static final int NULL_CASE_SIZE = 0;
    private static final int BASE_CASE_SIZE = 1;
    private static final int DECREMENT_STEP = 1;

    // Error & Status Messages Constants
    private static final String ERROR_MSG_MISSING_SIZE_LINE = "Invalid data file: missing size line.";
    private static final String ERROR_MSG_INVALID_SIZE_LINE = "Invalid size line: not an integer.";
    private static final String ERROR_MSG_INVALID_SIZE_N = "Invalid size: N must be positive.";
    private static final String ERROR_MSG_MISSING_MATRIX_ROWS = "Invalid data file: missing matrix rows.";
    private static final String ERROR_MSG_INSUFFICIENT_ROW_INTEGERS = "Invalid data file: not enough integers in a row.";
    private static final String ERROR_MSG_INVALID_INTEGER_TOKEN = "Invalid integer token in matrix.";
    private static final String ERROR_MSG_FILE_READ_FAILED = "Failed to read data file: ";
    private static final String ERROR_MSG_NO_MATRIX_LOADED = "No benefit matrix loaded. Call readDataFile(String) first.";
    private static final String MSG_NO_MATRIX_LOADED = "No matrix loaded.";

    // Instance Variables
    // The benefit matrix; null if no matrix has been loaded
    private int[][] benefitMatrix = null;

    // The size of the matrix; UNINITIALIZED_SIZE if no matrix has been loaded
    private int sizeN = UNINITIALIZED_SIZE;

    // Cached the best assignment; null if no matrix has been loaded
    private ArrayList<Integer> cachedBestAssignment = null;
    // Cached the best total value; Integer.MIN_VALUE if no matrix has been loaded
    private int cachedBestTotalValue = Integer.MIN_VALUE;

    /**
     * Reads a data file and initializes the benefit matrix.
     *
     * @param filename The path to the data file
     */
    public void readDataFile(String filename) {
        int[][] newMatrix;
        int newN;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Read the first line: N
            String line = nextNonEmptyLine(br);
            if (line == null) {
                throw new IllegalArgumentException(ERROR_MSG_MISSING_SIZE_LINE);
            }
            newN = parseSingleInt(line, ERROR_MSG_INVALID_SIZE_LINE);
            if (newN < MINIMUM_MATRIX_SIZE) {
                throw new IllegalArgumentException(ERROR_MSG_INVALID_SIZE_N);
            }

            newMatrix = new int[newN][newN];

            // Read the next N lines, each containing N integers
            for (int i = 0; i < newN; i++) {
                line = nextNonEmptyLine(br);
                if (line == null) {
                    throw new IllegalArgumentException(ERROR_MSG_MISSING_MATRIX_ROWS);
                }
                StringTokenizer st = new StringTokenizer(line);
                for (int j = 0; j < newN; j++) {
                    if (!st.hasMoreTokens()) {
                        throw new IllegalArgumentException(
                                ERROR_MSG_INSUFFICIENT_ROW_INTEGERS);
                    }
                    newMatrix[i][j] = parseSingleInt(st.nextToken(),
                            ERROR_MSG_INVALID_INTEGER_TOKEN);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(ERROR_MSG_FILE_READ_FAILED + e.getMessage(), e);
        }

        this.benefitMatrix = newMatrix;
        this.sizeN = newN;
        this.cachedBestAssignment = null;
        this.cachedBestTotalValue = Integer.MIN_VALUE;
    }

    /**
     * Returns the size of the matrix N; returns UNINITIALIZED_SIZE if no matrix has
     * been
     * loaded.
     *
     * @return N or UNINITIALIZED_SIZE
     */
    public int getInputSize() {
        return (benefitMatrix == null)
                ? UNINITIALIZED_SIZE
                : sizeN;
    }

    /**
     * Returns the loaded benefit matrix.
     *
     * @return The loaded benefit matrix; null if no matrix has been loaded
     */
    public int[][] getBenefitMatrix() {
        if (benefitMatrix == null) {
            return null;
        }
        return copyMatrix(benefitMatrix);
    }

    /**
     * Returns the loaded benefit matrix as a string.
     *
     * @return The benefit matrix as a string; MSG_NO_MATRIX_LOADED if no matrix
     */
    public String benefitMatrixToString() {
        if (benefitMatrix == null) {
            return MSG_NO_MATRIX_LOADED;
        }

        StringBuilder sb = new StringBuilder();
        int n = sizeN;
        int[] colWidth = new int[n];
        for (int j = 0; j < n; j++) {
            int w = MIN_COLUMN_WIDTH;
            for (int i = 0; i < n; i++) {
                int len = String.valueOf(benefitMatrix[i][j]).length();
                if (len > w) {
                    w = len;
                }
            }
            colWidth[j] = w;
        }
        for (int i = 0; i < n; i++) {
            sb.append("[ ");
            for (int j = 0; j < n; j++) {
                String cell = String.valueOf(benefitMatrix[i][j]);
                sb.append(padLeft(cell, colWidth[j]));
                if (j < n - 1) {
                    sb.append("  ");
                }
            }
            sb.append(" ]");
            if (i < n - 1) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    /**
     * Returns the best assignment (0 to N-1) for the loaded benefit matrix.
     *
     * @return The best assignment
     *
     * @throws IllegalStateException If no matrix has been loaded
     */
    public ArrayList<Integer> getMaxAssignment() {
        checkMatrixLoaded();

        if (cachedBestAssignment != null) {
            return new ArrayList<>(cachedBestAssignment);
        }

        final int n = sizeN;
        ArrayList<ArrayList<Integer>> allPerms = getPermutations(n);

        int bestSum = Integer.MIN_VALUE;
        ArrayList<Integer> bestPerm = null;

        // Brute-force evaluation of all permutations
        for (ArrayList<Integer> perm : allPerms) {
            int sum = 0;
            for (int person = 0; person < n; person++) {
                int job = perm.get(person);
                sum += benefitMatrix[person][job];
            }
            if (sum > bestSum) {
                bestSum = sum;
                bestPerm = new ArrayList<>(perm);
            }
        }

        this.cachedBestTotalValue = bestSum;
        this.cachedBestAssignment = (bestPerm == null)
                ? new ArrayList<>()
                : new ArrayList<>(bestPerm);
        return new ArrayList<>(cachedBestAssignment);
    }

    /**
     * Returns the best assignment (0 to N-1) for the loaded benefit matrix.
     *
     * @return The best assignment
     *
     * @throws IllegalStateException If no matrix has been loaded
     */
    public int getMaxAssignmentTotalValue() {
        checkMatrixLoaded();
        if (cachedBestAssignment == null) {
            getMaxAssignment();
        }
        return cachedBestTotalValue;
    }

    /**
     * Returns the benefit value for a given person and job.
     *
     * @param person The person
     * @param job    The job
     * @return The benefit value
     *
     * @throws IllegalStateException If no matrix has been loaded
     */
    public int getBenefit(int person, int job) {
        checkMatrixLoaded();
        return benefitMatrix[person][job];
    }

    /**
     * Recursive decrease-and-conquer algorithm to generate a list of all
     * permutations of
     * the numbers 0 to N-1. This follows the "decrease by 1" pattern of decrease
     * and
     * conquer algorithms.
     * <p>
     * This method returns an ArrayList of ArrayLists. One permutation is an
     * ArrayList
     * containing 0,1,2,...,N-1 in some order. The final result is an ArrayList
     * containing
     * N! of those permutations.
     *
     * @param n The size of the set (from 0 to n-1) for which to generate
     *          permutations.
     * @return An ArrayList containing all n! permutations, where each permutation
     *         is an
     *         ArrayList of integers.
     * @author BCIT COMP3760
     */
    private ArrayList<ArrayList<Integer>> getPermutations(int n) {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();

        /*
         * This isn't a "base case", it's a "null case". This function does not call
         * itself with an argument of zero, but we can't prevent another caller from
         * doing so. It's a weird result, though. The list of permutations has one
         * permutation, but the one permutation is empty (0 elements).
         */
        if (n == NULL_CASE_SIZE) {
            ArrayList<Integer> emptyList = new ArrayList<>();
            results.add(emptyList);

        } else if (n == BASE_CASE_SIZE) {
            /*
             * Now THIS is the base case. Create an ArrayList with a single integer,
             * and add
             * it to the results list.
             */
            ArrayList<Integer> singleton = new ArrayList<>();
            singleton.add(0);
            results.add(singleton);

        } else {
            /*
             * And: the main part. First a recursive call (this is a decrease and conquer
             * algorithm) to get all the permutations of length N-1.
             */
            ArrayList<ArrayList<Integer>> smallList = getPermutations(n - DECREMENT_STEP);

            /*
             * Iterate over the list of smaller permutations and insert the value 'N-1'
             * into
             * every permutation in every possible position, adding each new
             * permutation to
             * the big list of permutations.
             */
            for (ArrayList<Integer> perm : smallList) {

                /*
                 * Add 'N-1' -- the biggest number in the new permutation -- at each of
                 * the
                 * positions from 0 to N-1.
                 */
                for (int i = 0; i < perm.size(); i++) {
                    ArrayList<Integer> newPerm = new ArrayList<>(perm);
                    newPerm.add(i, n - DECREMENT_STEP);
                    results.add(newPerm);
                }

                /*
                 * Add 'N-1' at the end (i.e. at position "size").
                 */
                ArrayList<Integer> newPerm = new ArrayList<>(perm);
                newPerm.add(n - DECREMENT_STEP);
                results.add(newPerm);

            }

        }
        return results;
    }

    /**
     * Checks if a matrix has been loaded.
     *
     * @throws IllegalStateException If no matrix has been loaded
     */
    private void checkMatrixLoaded() {
        if (benefitMatrix == null || sizeN < MINIMUM_MATRIX_SIZE) {
            throw new IllegalStateException(ERROR_MSG_NO_MATRIX_LOADED);
        }
    }

    /**
     * Reads the next non-empty line from the provided BufferedReader, skipping any
     * empty
     *
     * @param br The BufferedReader to read from
     * @return The next non-empty line, or null if the end of the stream is reached
     *
     * @throws IOException If an I/O error occurs while reading from the
     *                     BufferedReader
     */
    private static String nextNonEmptyLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            return line;
        }
        return null;
    }

    /**
     * Parses a string to an integer and throws an IllegalArgumentException with the
     * specified error message if parsing fails.
     *
     * @param token          The string to parse as an integer
     * @param onErrorMessage The error message to use in the exception if parsing
     *                       fails
     * @return The parsed integer
     *
     * @throws IllegalArgumentException If the token cannot be parsed as an integer
     */
    private static int parseSingleInt(String token, String onErrorMessage) {
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(onErrorMessage);
        }
    }

    /**
     * Left-pads a string with spaces to the specified width.
     *
     * @param string The string to pad
     * @param width  The width to pad the string to
     * @return The left-padded string
     */
    private static String padLeft(String string, int width) {
        int pad = width - string.length();
        if (pad <= 0) {
            return string;
        }
        return " ".repeat(pad) + string;
    }

    /**
     * Creates a deep copy of the source NxN matrix.
     *
     * @param src The source matrix
     * @return The deep copy of the source matrix
     */
    private static int[][] copyMatrix(int[][] src) {
        int n = src.length;
        int[][] dst = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(src[i], 0, dst[i], 0, n);
        }
        return dst;
    }
}
