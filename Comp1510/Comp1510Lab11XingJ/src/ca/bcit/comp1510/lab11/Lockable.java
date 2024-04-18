package ca.bcit.comp1510.lab11;

/**
 * Defines the methods to lock and unlock an object using a key.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public interface Lockable {
    /**
     * Sets the key used to unlock or lock the object.
     *
     * @param key the key to be used
     */
    void setKey(int key);

    /**
     * Locks the object if the correct key is provided.
     *
     * @param key the key to lock the object
     * @return true if the object was successfully locked
     */
    boolean lock(int key);

    /**
     * Unlocks the object if the correct key is provided.
     *
     * @param key the key to unlock the object
     * @return true if the object was successfully unlocked
     */
    boolean unlock(int key);

    /**
     * Checks if the object is currently locked.
     *
     * @return true if the object is locked
     */
    boolean locked();
}
