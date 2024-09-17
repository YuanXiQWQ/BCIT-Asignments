package ca.bcit.comp1510.lab04;

/**
 * Represents one die (singular of dice) with faces showing values between 1 and
 * 6.
 * This class has been edited by Jiarui Xing for finishing Lab4.
 *
 * @author Lewis & Loftus 9e
 * @author BCIT
 * @author Jiarui Xing
 * @version 2017
 */
public class MultiDie {
    /**
     * Maximum face value.
     */
    public final int max;

    /**
     * Current value showing on the die.
     */
    private int faceValue;

    /**
     * Constructor sets the initial face value to 1.
     */
    public MultiDie(int numSides) {
        max = numSides;
        roll();
    }

    /**
     * Rolls this MultiDie and returns the result.
     *
     * @return faceValue as an int
     */
    public int roll() {
        faceValue = (int) (Math.random() * max) + 1;

        return faceValue;
    }

    /**
     * Sets the face value of this MultiDie to the specified value.
     *
     * @param value an int
     */
    public void setFaceValue(int value) {
        faceValue = value;
    }

    /**
     * Returns the face value of this MultiDie as an int.
     *
     * @return faceValue as an int
     */
    public int getFaceValue() {
        return faceValue;
    }

    /**
     * Returns a String representation of this MultiDie.
     *
     * @return toString description
     */
    @Override
    public String toString() {

        return Integer.toString(faceValue);
    }
}

