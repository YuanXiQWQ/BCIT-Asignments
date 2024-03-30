package ca.bcit.comp1510.lab09;

/**
 * This class is used to test the Coin class.
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class CoinRunner {
    /**
     * Main method.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        Coin coin = new Coin();
        int maxRun = 0;
        int currentRun = 0;
        String lastFace = "";

        for (int i = 0; i < 100; i++) {
            coin.flip();
            System.out.println(coin);

            if (coin.getFace().equals(lastFace) && "Heads".equals(
                    coin.getFace())) {
                currentRun++;
            } else if ("Heads".equals(coin.getFace())) {
                currentRun = 1;
            } else {
                currentRun = 0;
            }

            if (currentRun > maxRun) {
                maxRun = currentRun;
            }

            lastFace = coin.getFace();
        }

        System.out.println("The longest run of heads is " + maxRun + ".");
    }
}
