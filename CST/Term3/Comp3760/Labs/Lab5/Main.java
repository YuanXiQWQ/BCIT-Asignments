import java.util.ArrayList;

/**
 * Test Lab5
 *
 * @author Jerry Xing | A01354731
 */
public class Main {
    public static void main(String[] args)
    {
        String[] dataFiles = {
                "data0.txt",
                "data1.txt",
                "data2.txt",
                "data3.txt",
                "data4.txt",
                "data5.txt",
                "data11.txt",
                "data12.txt",
                "data37.txt",
                "data148.txt"
        };

        for(String fileName : dataFiles)
        {
            JobAssignmentFinder finder = new JobAssignmentFinder();
            finder.readDataFile(fileName);
            ArrayList<Integer> greedyAssignment = finder.getGreedyAssignment();
            int totalValue = finder.greedyAssignmentTotalValue();

            System.out.println("==== " + fileName + " ====");
            System.out.println("Greedy assignment: " + greedyAssignment);
            System.out.println("Greedy total value: " + totalValue);
        }
    }
}
