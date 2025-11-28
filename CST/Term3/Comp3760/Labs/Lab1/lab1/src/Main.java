/**
 * Author: Jerry Xing (A01354731) | Set: 3G
 * <p>
 * Purpose: Local driver for COMP 3760 Lab 1 to exercise JobAssignmentFinder. Loads
 * several data files, displays the benefit matrix, the max assignment, and the total
 * value. This main() is for YOUR testing only; markers will use their own driver per the
 * lab handout.
 * <p>
 * Notes (per Coding Requirements / Lab Tips): - Do not rely on args[] or console input;
 * keep file names as visible constants. - Label every line of output clearly to avoid
 * unidentified output. - JobAssignmentFinder's public methods must NOT print anything.
 */

import java.util.*;

/**
 * @author YuanXi
 */
public class Main {

    // === Change these file names if needed (no console/args input) ===
    private static final String[] TEST_FILES =
            {"data0.txt", "data1.txt", "data2.txt", "data3.txt", "data4.txt",
                    "data5.txt"};

    // Expected results from the lab handout (for quick self-check).
    // If a file is missing from this map, we just skip the check.
    private static final Map<String, Expected> EXPECTED = createExpectedMap();

    public static void main(String[] args)
    {
        System.out.println("=== COMP 3760 Lab 1: Job Assignment - Local Driver ===");

        for(String file : TEST_FILES)
        {
            System.out.println();
            System.out.println("------------------------------------------------------");
            System.out.println("Input file: " + file);

            JobAssignmentFinder finder = new JobAssignmentFinder();
            try
            {
                finder.readDataFile(file);

                int[][] benefitMatrix = finder.getBenefitMatrix();
                int benefit = finder.getBenefit(1, 2);
                int n = finder.getInputSize();
                System.out.println("N (matrix size): " + n);

                System.out.println("Benefit matrix:");
                System.out.println(finder.benefitMatrixToString());
                System.out.println("Benefit at (1, 2): " + benefit);
                System.out.println("Benefit matrix: " +
                        Arrays.deepToString(benefitMatrix));

                ArrayList<Integer> assignment = finder.getMaxAssignment();
                int total = finder.getMaxAssignmentTotalValue();

                System.out.println("Max assignment: " + assignment);
                System.out.println("Total value: " + total);

                // Optional: compare against expected results from the handout
                Expected exp = EXPECTED.get(file);
                if(exp != null)
                {
                    boolean matchAssign = assignment.equals(exp.assignment);
                    boolean matchTotal = (total == exp.total);
                    System.out.println("Matches expected assignment? " + (matchAssign
                                                                          ? "YES"
                                                                          : "NO"));
                    System.out.println("Matches expected total? " + (matchTotal
                                                                     ? "YES"
                                                                     : "NO"));
                    if(!matchAssign || !matchTotal)
                    {
                        System.out.println("Expected assignment: " + exp.assignment);
                        System.out.println("Expected total: " + exp.total);
                    }
                } else
                {
                    System.out.println(
                            "No expected baseline recorded for this file (skipping " +
                                    "check).");
                }

            } catch(RuntimeException ex)
            {
                // Your JobAssignmentFinder converts IO issues to RuntimeException;
                // report and continue.
                System.out.println(
                        "ERROR processing file '" + file + "': " + ex.getMessage());
            }
        }

        System.out.println();
        System.out.println("=== Done. ===");
    }

    // ----- helpers -----

    private static Map<String, Expected> createExpectedMap()
    {
        Map<String, Expected> map = new LinkedHashMap<>();

        map.put("data0.txt", new Expected(asList(2, 3, 4, 1, 0), 37));
        map.put("data1.txt", new Expected(asList(3, 2, 6, 4, 1, 5, 0), 58));
        map.put("data2.txt", new Expected(asList(5, 0, 9, 2, 4, 3, 7, 6, 8, 1), 8658));
        map.put("data3.txt", new Expected(asList(7, 5, 8, 4, 1, 0, 9, 3, 6, 2), 335));
        map.put("data4.txt", new Expected(asList(1, 0, 4, 5, 2, 3), 42));
        map.put("data5.txt", new Expected(asList(2, 1, 4, 8, 6, 7, 3, 0, 5), 74));

        return map;
    }

    private static ArrayList<Integer> asList(int... vals)
    {
        ArrayList<Integer> list = new ArrayList<>(vals.length);
        for(int v : vals)
        {
            list.add(v);
        }
        return list;
    }

    private record Expected(ArrayList<Integer> assignment, int total) {
    }
}
