package ca.bcit.comp1510.lab03;

import java.util.Random;


public class Dice {
    /**
     * Drives the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        //初始化
        Random random = new Random();

        //开始执行随机数
        int d4 = random.nextInt(4) + 1;
        int d6 = random.nextInt(6) + 1;
        int d8 = random.nextInt(8) + 1;
        int d10 = random.nextInt(10) + 1;
        int d12 = random.nextInt(12) + 1;
        int d20 = random.nextInt(20) + 1;

        //输出
        System.out.println("Result of 4 die: " + d4);
        System.out.println("Result of 6 die: " + d6);
        System.out.println("Result of 8 die: " + d8);
        System.out.println("Result of 10 die: " + d10);
        System.out.println("Result of 12 die: " + d12);
        System.out.println("Result of 20 die: " + d20);
        System.out.println("Result of total dice: " + (d4 + d6 + d8 + d10 + d12 + d20));
    }
}