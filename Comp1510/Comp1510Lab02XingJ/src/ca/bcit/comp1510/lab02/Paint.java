package ca.bcit.comp1510.lab02;

import java.util.Scanner;

/**
 * A program to help people print house
 *
 * @author JiaruiXing
 * @version 2022/8/18
 */
public class Paint {
    /**
     * Drives the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        final int COVERAGE = 400;

        //输入赋值阶段
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Input the length of the house: ");
        double length = myScanner.nextDouble();
        System.out.println("Input the width of the house: ");
        double width = myScanner.nextDouble();

        //如果输反了
        if (width > length) {
            System.out.println("The width should be larger than the length, but we can still proceed:");
        }

        System.out.println("Input the height of the house: ");
        double height = myScanner.nextDouble();
        System.out.println("Input the coats of the paint: ");
        int coats = myScanner.nextInt();
        myScanner.close();                                         //好习惯

        //计算
        double surfaceArea = length * width/*房顶*/ + length * height * 2/*长面*/ + width * height * 2/*宽面*/;                                                   //粉刷总面积
        double coverageNeeded = surfaceArea * (double) coats;
        double cansOfPaintNeeded = coverageNeeded / COVERAGE;

        //超市不卖半罐,要向后取整
        if (cansOfPaintNeeded % 1 != 0) {                                   //条件:有小数点
            cansOfPaintNeeded++;
            System.out.println("You need to buy " + (int) cansOfPaintNeeded + " cans of paint and there might be some extra.");
        } else {
            System.out.println("You need to buy " + (int) cansOfPaintNeeded + " cans of paint, and it's quite likely that we'll use up all the cans.");
        }
    }
}
