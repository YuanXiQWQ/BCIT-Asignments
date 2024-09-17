import java.util.Scanner;

/**
 * A program that calculates some important values for a Circle
 *
 * @author JiaruiXing
 * @version 2022/8/18
 */

public class Circle {
    /**
     * Drives the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        //初始化
        final double PI = 3.14159;                      //常量π
        double radius;                                  //半径

        //输入
        Scanner myScanner = new Scanner(System.in);     //创建扫描器myScanner
        System.out.println("Input a radius: ");
        radius = myScanner.nextDouble();                //用户输入赋值给radius
        myScanner.close();                              //好习惯

        //计算输出周长
        double circumference = 2 * PI * radius;        //周长=2πr
        System.out.println("The circumference is " + circumference);
        //第七项结束

        //计算输出面积
        double area = PI * radius * radius;
        System.out.println("The area is " + area);
        //第八项结束

        //第九项
        double doubleRadius = 2 * radius;               //第九项a
        double doubleRadiusCircumference = 2 * PI * doubleRadius;     //周长
        double doubleRadiusArea = PI * doubleRadius * doubleRadius;   //面积
        double beiCircumference = doubleRadiusCircumference / circumference;
        double beiArea = doubleRadiusArea / area;
        System.out.println("Double radius will cause the circumference become " + beiCircumference + "of the original while the area become " + beiArea + "of the original");
        //第九项结束
    }
}