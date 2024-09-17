package ca.bcit.comp1510.lab03;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * A program to accurate distance
 *
 * @author Jiarui Xing
 * @version 2024/1/29
 */
public class Distance {
    /**
     * Drives the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        //初始化
        Scanner scan = new Scanner(System.in);

        //用户交互
        System.out.println("Please type in the location of point 1（x1 y1）：");
        double x1 = scan.nextDouble();
        double y1 = scan.nextDouble();                                          //点1的坐标
        System.out.println("Please type in the location of point 2（x2 y2）：");
        double x2 = scan.nextDouble();
        double y2 = scan.nextDouble();                                          //点2的坐标
        scan.close();

        //执行运算
        double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));     //距离公式
        System.out.println("The distance between point 1 and point 2 is " + distance);

        //优化
        DecimalFormat idkWhatImDoing = new DecimalFormat("#.##");       //公共类
        System.out.println("The distance between point 1 and point 2 is " + idkWhatImDoing.format(distance));
    }
}
