package ca.bcit.comp1510.lab10;

import java.util.Random;

/**
 * Class to simulate the movement of a RandomWalker.
 *
 * @author Xing Jiarui
 * @version 2024.4.5
 */
public class RandomWalker {
    /**
     * The current X coordinate of the walker.
     */
    private int currentX;
    /**
     * The current Y coordinate of the walker.
     */
    private int currentY;
    /**
     * The maximum number of steps the walker can take.
     */
    private final int maxStep;
    /**
     * The number of steps taken by the walker.
     */
    private int numSteps;
    /**
     * The boundary of the grid.
     */
    private final int boundary;
    /**
     * The maximum distance reached by the walker.
     */
    private int maximumDistance;
    /**
     * The random number generator.
     */
    private static final Random RAND = new Random();

    /**
     * Constructs a RandomWalker with specified values.
     *
     * @param maxStep  the maximum number of steps the walker can take
     * @param currentX the current X coordinate of the walker
     * @param currentY the current Y coordinate of the walker
     * @param boundary the boundary of the grid
     */
    public RandomWalker(int maxStep, int currentX, int currentY, int boundary) {
        this.maxStep = maxStep;
        this.currentX = currentX;
        this.currentY = currentY;
        this.boundary = boundary;
        numSteps = maximumDistance = 0;
    }

    /**
     * Constructs a RandomWalker with default values.
     *
     * @param maxStep  the maximum number of steps the walker can take
     * @param boundary the boundary of the grid
     */
    public RandomWalker(int maxStep, int boundary) {
        this(maxStep, 0, 0, boundary);
    }

    /**
     * Takes a step in the random walker's direction.
     */
    void takeStep() {
        int direction = RAND.nextInt(4);
        switch (direction) {
            case 0 -> currentY++;
            case 1 -> currentX++;
            case 2 -> currentY--;
            case 3 -> currentX--;
        }
        numSteps++;
        updateMaximumDistance();
    }

    /**
     * Updates the maximum distance reached by the walker.
     */
    private void updateMaximumDistance() {
        maximumDistance = Math.max(max(currentX, currentY), maximumDistance);
    }

    /**
     * Gets the maximum of two abs numbers.
     *
     * @param a a
     * @param b b
     *
     * @return the maximum of two abs numbers
     */
    private int max(int a, int b) {
        return Math.max(Math.abs(a), Math.abs(b));
    }

    /**
     * Checks if the walker has more steps to take.
     *
     * @return true if the walker has more steps to take, false otherwise
     */
    private boolean moreSteps() {
        return numSteps < maxStep;
    }

    /**
     * Checks if the walker is in bounds.
     *
     * @return true if the walker is in bounds, false otherwise
     */
    boolean inBounds() {
        return Math.abs(currentX) <= boundary && Math.abs(currentY) <= boundary;
    }

    /**
     * Takes steps until the walker is out of bounds.
     */
    public void walk() {
        while (moreSteps() && inBounds()) {
            takeStep();
        }
    }

    /**
     * Gets the current X coordinate of the walker.
     *
     * @return the current X coordinate
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * Gets the current Y coordinate of the walker.
     *
     * @return the current Y coordinate
     */
    public int getCurrentY() {
        return currentY;
    }

    /**
     * Gets the maximum distance reached by the walker.
     *
     * @return the maximum distance
     */
    public int getMaxMDistance() {
        return maximumDistance;
    }

    /**
     * Returns a string representation of the RandomWalker.
     *
     * @return a string representation of the RandomWalker
     */
    @Override
    public String toString() {
        return String.format("RandomWalker [currentX=%d, currentY=%d, maximumDistance=%d]",
                currentX, currentY, maximumDistance);
    }
}
