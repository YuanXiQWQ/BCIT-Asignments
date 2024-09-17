package ca.bcit.comp1510.lab11;

/**
 * Driver class for the Coin class.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class Driver {
    /**
     * Main method.
     * @param args unused
     */
    public static void main(String[] args) {
        Coin coin = new Coin();

        int myKey = 12345;
        coin.setKey(myKey);

        boolean isLocked = coin.lock(myKey);
        System.out.println("Coin locked: " + isLocked);

        coin.flip();
        System.out.println("Coin state after attempting to flip when locked: " +
                coin);

        boolean isUnlocked = coin.unlock(myKey);
        System.out.println("Coin unlocked: " + isUnlocked);

        coin.flip();
        System.out.println("Coin state after flipping when unlocked: " +
                coin);

        isLocked = coin.lock(11111);
        System.out.println(
                "Coin locked with wrong key: " + isLocked);
    }
}
