package ca.bcit.comp1510.lab03;

import java.util.Random;

/**
 * A card game.
 *
 * @author Jiarui Xing
 * @version 2024/1/29
 */
public class CardGame {
    /**
     * Drives the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Random random = new Random();

        //抽牌
        int randomRankChoice = random.nextInt(Rank.values().length);        //调用数组长度并随机一个数
        Rank randomRank = Rank.values()[randomRankChoice];                  //访问随机数在数组的坐标

        //抽花色
        int randomSuitChoice = random.nextInt(Suit.values().length);
        Suit randomSuit = Suit.values()[randomSuitChoice];

        //输出结果
        System.out.println("You get a " + randomRank + " of " + randomSuit);
    }

    //牌大小
    enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    //牌花色
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }
}
