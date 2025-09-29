package ca.bcit.comp1510.lab09;

/**
 * A coin that can be flipped.
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class Coin {
    /**
     * The string representation of the coin.
     */
    private String face;

    /**
     * The string representation of the coin.
     */
    public Coin() {
        flip();
    }

    /**
     * Flip the coin.
     */
    public void flip() {
        if (Math.random() < 0.5) {
            face = "Heads";
        } else {
            face = "Tails";
        }
    }

    /**
     * Get the face of the coin.
     *
     * @return the face
     */
    public String getFace() {
        return face;
    }

    /**
     * Get the string representation of the coin.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Coin is "+getFace() + ".";
    }
}
