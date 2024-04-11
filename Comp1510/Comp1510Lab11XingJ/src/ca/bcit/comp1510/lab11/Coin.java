package ca.bcit.comp1510.lab11;

/**
 * A coin that can be flipped and locked.
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class Coin implements Lockable {
    private String face;
    private int key;
    private boolean isLocked = false;

    /**
     * Constructs a new ca.bcit.comp1510.lab11.Coin and flips it initially.
     */
    public Coin() {
        flip();
    }

    /**
     * Sets the key used for locking and unlocking the coin.
     *
     * @param key the key to set
     */
    @Override
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Locks the coin if the correct key is provided.
     *
     * @param key the key to lock the coin
     * @return true if the coin was successfully locked, false otherwise
     */
    @Override
    public boolean lock(int key) {
        if (this.key == key) {
            this.isLocked = true;
            return true;
        }
        return false;
    }

    /**
     * Unlocks the coin if the correct key is provided.
     *
     * @param key the key to unlock the coin
     * @return true if the coin was successfully unlocked, false otherwise
     */
    @Override
    public boolean unlock(int key) {
        if (this.key == key) {
            this.isLocked = false;
            return true;
        }
        return false;
    }

    /**
     * Returns the lock status of the coin.
     *
     * @return true if the coin is locked, false otherwise
     */
    @Override
    public boolean locked() {
        return isLocked;
    }

    /**
     * Flips the coin if it is not locked.
     */
    public void flip() {
        if (!isLocked) {
            if (Math.random() < 0.5) {
                face = "Heads";
            } else {
                face = "Tails";
            }
        }
    }

    /**
     * Returns the current face of the coin.
     *
     * @return the face of the coin
     */
    public String getFace() {
        return face;
    }

    /**
     * Returns the string representation of the coin.
     *
     * @return the string representation of the coin's status
     */
    @Override
    public String toString() {
        return "ca.bcit.comp1510.lab11.Coin is " + getFace() + (isLocked ? " (Locked)" : "");
    }
}
