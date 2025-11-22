import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides exhaustive and greedy solutions to the job assignment problem, and supports reading matrix data from a
 * file.
 *
 * @author Jerry Xing | A01354731
 */
@SuppressWarnings({"unused", "AlibabaLowerCamelCaseVariableNaming"})
public class JobAssignmentFinder {

    /**
     * This data is the input to the problem; it will be read from a data file.
     */
    private int[][] benefitMatrix;
    private int problemSize = -1;
    private int lastGreedyValue;
    private boolean hasGreedyResult;

    /**
     * Reads the data file and stores the benefit matrix data for subsequent use.
     *
     * @param fileName path to the input data file containing the benefit matrix
     */
    public void readDataFile(String fileName)
    {
        try
        {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            this.problemSize = sc.nextInt();
            this.benefitMatrix = new int[this.problemSize][this.problemSize];
            for(int r = 0; r < this.problemSize; r++)
            {
                for(int c = 0; c < this.problemSize; c++)
                {
                    this.benefitMatrix[r][c] = sc.nextInt();
                }
            }
            sc.close();
            this.lastGreedyValue = 0;
            this.hasGreedyResult = false;
        } catch(FileNotFoundException e)
        {
            throw new RuntimeException("Data file not found: " + fileName, e);
        }
    }

    /**
     * Basic getter for the problem size aka "N".
     *
     * @return the size of the assignment problem
     */
    public int getInputSize()
    {
        return this.problemSize;
    }

    /**
     * Basic getter for the benefit matrix (input).
     *
     * @return the benefit matrix read from the data file
     */
    public int[][] getBenefitMatrix()
    {
        return this.benefitMatrix;
    }

    /**
     * Returns the benefit of assigning the given person to the given job.
     *
     * @param person index of the person (0…N-1)
     * @param job    index of the job (0…N-1)
     * @return benefitMatrix[person][job]
     */
    public int getBenefit(int person, int job)
    {
        return this.benefitMatrix[person][job];
    }

    /**
     * Returns a string representation of the (input) benefit matrix.
     *
     * @return formatted string representation of the benefit matrix
     */
    public String benefitMatrixToString()
    {
        StringBuilder result = new StringBuilder();
        result.append("Matrix size is ").append(this.problemSize).append(" x ").append(this.problemSize).append("\n");
        for(int row = 0; row < this.problemSize; row++)
        {
            result.append("[");
            for(int col = 0; col < this.problemSize - 1; col++)
            {
                result.append(this.benefitMatrix[row][col]).append(" ");
            }
            result.append(this.benefitMatrix[row][this.problemSize - 1]).append("]\n");
        }
        return result.toString();
    }

    /**
     * Do exhaustive search to find the maximum-valued assignment of jobs to people. This blindly performs the entire
     * exhaustive search, even if this has already been done before on the current data set. It would (obvs) be smarter
     * to "save" the result any time we calculate it, in order to avoid this recalculation. Oh well.
     *
     * @return job assignments yielding the maximum benefit
     */
    public ArrayList<Integer> getMaxAssignment()
    {
        AssignmentResult result = findMaxAssignment();
        return new ArrayList<>(result.assignment);
    }

    /**
     * Applies a greedy strategy that assigns each person the highest-benefit job that remains available.
     *
     * @return permutation representing the greedy job assignment
     */
    public ArrayList<Integer> getGreedyAssignment()
    {
        if(this.benefitMatrix == null)
        {
            throw new IllegalStateException("Benefit matrix not loaded.");
        }
        ArrayList<Integer> assignment = new ArrayList<>();
        boolean[] usedJobs = new boolean[this.problemSize];
        for(int person = 0; person < this.problemSize; person++)
        {
            int bestJob = getBestJob(usedJobs, person);
            assignment.add(bestJob);
            usedJobs[bestJob] = true;
        }
        this.lastGreedyValue = checkValueOfAssignment(assignment, this.benefitMatrix);
        this.hasGreedyResult = true;
        return assignment;
    }

    /**
     * Selects the best available job for the specified person based on the benefit matrix.
     *
     * @param usedJobs flags indicating which jobs have already been assigned
     * @param person   index of the person whose job is being selected
     * @return job index that yields the highest benefit for this person
     */
    private int getBestJob(boolean[] usedJobs, int person)
    {
        int bestJob = -1;
        int bestValue = Integer.MIN_VALUE;
        for(int job = 0; job < this.problemSize; job++)
        {
            if(!usedJobs[job] && this.benefitMatrix[person][job] > bestValue)
            {
                bestValue = this.benefitMatrix[person][job];
                bestJob = job;
            }
        }
        if(bestJob == -1)
        {
            throw new IllegalStateException("Unable to determine greedy assignment.");
        }
        return bestJob;
    }

    /**
     * Returns the total benefit of the most recent greedy assignment, triggering the greedy algorithm if necessary.
     *
     * @return total benefit produced by the greedy assignment
     */
    public int greedyAssignmentTotalValue()
    {
        if(this.benefitMatrix == null)
        {
            throw new IllegalStateException("Benefit matrix not loaded.");
        }
        if(!this.hasGreedyResult)
        {
            getGreedyAssignment();
        }
        return this.lastGreedyValue;
    }

    /**
     * Return the total value of the max assignment. This blindly performs the entire exhaustive search, even if this
     * has already been done before. It would (obvs) be smarter to "save" the result any time we calculate it, in order
     * to avoid this recalculation. Oh well.
     *
     * @return highest total benefit value from all job assignments
     */
    public int getMaxAssignmentTotalValue()
    {
        return findMaxAssignment().totalValue;
    }

    /**
     * Recursive decrease-and-conquer algorithm to generate a list of all permutations of the numbers 0…N-1. This
     * follows the "decrease by 1" pattern of decrease and conquer algorithms.
     * <p>
     * This method returns an ArrayList of ArrayLists. One permutation is an ArrayList containing 0,1,2,...,N-1 in some
     * order. The final result is an ArrayList containing N! of those permutations.
     *
     * @param N size of the permutation set to generate
     * @return list containing all permutations of values 0..N-1
     */
    private ArrayList<ArrayList<Integer>> getPermutations(int N)
    {
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();

        /*
         * This isn't a "base case", it's a "null case". This function does not call itself with an
         * argument of zero, but we can't prevent another caller from doing so. It's a weird result,
         * though. The list of permutations has one permutation, but the one permutation is empty
         * (0 elements).
         */
        if(N == 0)
        {
            ArrayList<Integer> emptyList = new ArrayList<>();
            results.add(emptyList);
        } else if(N == 1)
        {
            /*
             * Now THIS is the base case. Create an ArrayList with a single integer, and add it to the
             * results list.
             */
            ArrayList<Integer> singleton = new ArrayList<>();
            singleton.add(0);
            results.add(singleton);
        } else
        {
            /*
             * And: the main part. First a recursive call (this is a decrease and conquer algorithm) to
             * get all the permutations of length N-1.
             */
            ArrayList<ArrayList<Integer>> smallList = getPermutations(N - 1);

            /*
             * Iterate over the list of smaller permutations and insert the value 'N-1' into every permutation in
             * every possible position.
             */
            for(ArrayList<Integer> perm : smallList)
            {

                /*
                 * Add 'N-1' -- the biggest number in the new permutation -- at each of the positions from 0…N-1.
                 */
                for(int i = 0; i < perm.size(); i++)
                {
                    ArrayList<Integer> newPerm = new ArrayList<>(perm);
                    newPerm.add(i, N - 1);
                    results.add(newPerm);
                }

                /*
                 * Add 'N-1' at the end (i.e. at position "size").
                 */
                ArrayList<Integer> newPerm = new ArrayList<>(perm);
                newPerm.add(N - 1);
                results.add(newPerm);
            }
        }

        // Nothing left to do except:
        return results;
    }

    /**
     * Executes an exhaustive search to locate the assignment with the highest benefit.
     *
     * @return aggregate result containing the maximizing assignment and its total benefit
     */
    private AssignmentResult findMaxAssignment()
    {
        AssignmentResult result = new AssignmentResult();
        // First generate all the permutations of size N.
        ArrayList<ArrayList<Integer>> allPermutations = getPermutations(benefitMatrix.length);

        // Loop over the permutations, checking the benefit of each one, and remembering the maximum.
        int maxBenefit = Integer.MIN_VALUE;
        ArrayList<Integer> maxAssignment = new ArrayList<>();
        for(ArrayList<Integer> jobAssignment : allPermutations)
        {
            int benefit = checkValueOfAssignment(jobAssignment, this.benefitMatrix);
            if(benefit > maxBenefit)
            {
                maxBenefit = benefit;
                maxAssignment = jobAssignment;
            }
        }
        result.assignment = maxAssignment;
        result.totalValue = maxBenefit;
        return result;
    }

    /**
     * Value object for returning both the assignment permutation and its computed benefit.
     */
    private static class AssignmentResult {
        private ArrayList<Integer> assignment;
        private int totalValue;
    }

    /**
     * Helper function determines the total value of making the given job assignment.
     *
     * @return total benefit produced by the provided job assignment
     */
    private int checkValueOfAssignment(ArrayList<Integer> jobAssignment, int[][] benefitMatrix)
    {
        int total = 0;
        for(int person = 0; person < jobAssignment.size(); person++)
        {
            int job = jobAssignment.get(person);
            total = total + benefitMatrix[person][job];
        }
        return total;
    }
}
