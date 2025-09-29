package ca.bcit.comp1510.lab10;

/**
 * Class to test the RandomWalker class.
 *
 * @author Xing Jiarui
 * @version 2024.4.5
 */
public class Collisions {
    /**
     * The maximum number of steps the walker can take.
     */
    private static final int MAX_STEPS = 100000;

    /**
     * The boundary of the grid.
     */
    private static final int BOUNDARY_SIZE = 2000000;

    /**
     * The starting positions of the walkers.
     */
    private static final int START_X1 = -3;

    /**
     * The starting positions of the walkers.
     */
    private static final int START_Y1 = 0;

    /**
     * The starting positions of the walkers.
     */
    private static final int START_X2 = 3;

    /**
     * The starting positions of the walkers.
     */
    private static final int START_Y2 = 0;

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        RandomWalker particle1 = new RandomWalker(MAX_STEPS, START_X1, START_Y1, BOUNDARY_SIZE);
        RandomWalker particle2 = new RandomWalker(MAX_STEPS, START_X2, START_Y2, BOUNDARY_SIZE);

        int collisions = simulateCollisions(particle1, particle2);

        System.out.printf("Total collisions: %d\n", collisions);
    }

    /**
     * Simulates the collisions of two walkers.
     *
     * @param one the first walker
     * @param two the second walker
     *
     * @return the number of collisions
     */
    private static int simulateCollisions(RandomWalker one, RandomWalker two) {
        int collisions = 0;
        for (int i = 0; i < Collisions.MAX_STEPS; i++) {
            one.takeStep();
            two.takeStep();
            if (samePosition(one, two)) {
                collisions++;
            }
        }
        return collisions;
    }

    /**
     * Checks if two walkers have the same position.
     *
     * @param one the first walker
     * @param two the second walker
     *
     * @return true if the walkers have the same position
     */
    public static boolean samePosition(RandomWalker one, RandomWalker two) {
        return one.getCurrentX() == two.getCurrentX() && one.getCurrentY() == two.getCurrentY();
    }
}
