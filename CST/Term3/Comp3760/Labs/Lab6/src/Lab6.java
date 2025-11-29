/**
 * Implements street-walk counting using both straightforward recursion and dynamic programming to highlight performance
 * differences.
 *
 * @author Jerry Xing | A01354731
 */
public class Lab6 {

    /**
     * Computes the number of monotonic grid paths from (0,0) to (m,n) using the straightforward recursive relation.
     *
     * @param m number of blocks downward (non-negative)
     * @param n number of blocks to the right (non-negative)
     * @return total number of distinct paths
     */
    public long SW_Recursive(int m, int n)
    {
        if(m == 0 || n == 0)
        {
            return 1L;
        }
        return SW_Recursive(m - 1, n) + SW_Recursive(m, n - 1);
    }

    /**
     * Evaluates the recursive solver across a diagonal range and reports timing information for each computation.
     *
     * @param lower inclusive lower bound for m and n
     * @param upper inclusive upper bound for m and n
     */
    public void RunRecursive(int lower, int upper)
    {
        for(int i = lower; i <= upper; i++)
        {
            long start = System.nanoTime();
            long value = SW_Recursive(i, i);
            long elapsedMs = (System.nanoTime() - start) / 1_000_000L;

            System.out.println(
                    "SW_Recursive(" + i + "," + i + ") = " + value +
                            ", time is " + elapsedMs + " ms"
            );
        }
    }

    /**
     * Computes the number of monotonic grid paths from (0,0) to (m,n) using a bottom-up dynamic programming table.
     *
     * @param m number of blocks downward (non-negative)
     * @param n number of blocks to the right (non-negative)
     * @return total number of distinct paths
     */
    public long SW_DynamicProg(int m, int n)
    {
        long[][] dp = new long[m + 1][n + 1];

        for(int i = 0; i <= m; i++)
        {
            dp[i][0] = 1L;
        }
        for(int j = 0; j <= n; j++)
        {
            dp[0][j] = 1L;
        }

        for(int i = 1; i <= m; i++)
        {
            for(int j = 1; j <= n; j++)
            {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m][n];
    }

    /**
     * Evaluates the dynamic-programming solver across a diagonal range and reports timing information for each computation.
     *
     * @param lower inclusive lower bound for m and n
     * @param upper inclusive upper bound for m and n
     */
    public void RunDynamicProg(int lower, int upper)
    {
        for(int i = lower; i <= upper; i++)
        {
            long start = System.nanoTime();
            long value = SW_DynamicProg(i, i);
            long elapsedMs = (System.nanoTime() - start) / 1_000_000L;

            System.out.println(
                    "SW_DynamicProg(" + i + "," + i + ") = " + value +
                            ", time is " + elapsedMs + " ms"
            );
        }
    }
}
