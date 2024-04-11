package ca.bcit.comp1510.lab11;

/**
 * ca.bcit.comp1510.lab11.Driver class to demonstrate the locking functionality of the ca.bcit.comp1510.lab11.Coin class.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class Driver {
    public static void main(String[] args) {
        Coin myCoin = new Coin();

        int myKey = 12345;
        myCoin.setKey(myKey);

        boolean isLocked = myCoin.lock(myKey);
        System.out.println("ca.bcit.comp1510.lab11.Coin locked: " + isLocked);

        myCoin.flip();
        System.out.println("ca.bcit.comp1510.lab11.Coin state after attempting to flip when locked: " +
                myCoin);

        boolean isUnlocked = myCoin.unlock(myKey);
        System.out.println("ca.bcit.comp1510.lab11.Coin unlocked: " + isUnlocked);

        myCoin.flip();
        System.out.println("ca.bcit.comp1510.lab11.Coin state after flipping when unlocked: " +
                myCoin);

        isLocked = myCoin.lock(11111);
        System.out.println(
                "ca.bcit.comp1510.lab11.Coin locked with wrong key: " + isLocked);
    }
}
