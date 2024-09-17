package ca.bcit.comp1510.lab03;

import java.util.Scanner;

/**
 * A fun(maybe not) program with Strings
 *
 * @author Jiarui Xing
 * @version 2024/1/29
 */
public class FunWithStrings {
    /**
     * Drives the program
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        //初始化:
        Scanner input = new Scanner(System.in);
        String favouriteBook;

        //用户交互:
        System.out.println("What is your favourite book?");
        favouriteBook = input.nextLine();               //读取输入直到遇到换行符
        System.out.println("Who is the author of this book?");
        String author = input.next();                   //原理是读取输入直到遇到空白字符(空格换行啥的)

        //length()方法
        System.out.println("The length of this book name is" + favouriteBook.length());

        //startsWith()方法
        if (favouriteBook.startsWith("The")) {          //条件:书名以"The"开头
            System.out.println("This book name start with \"The\" ");
        } else {
            System.out.println("This book name does not start with \"The\" ");
        }

        //toUpperCase()方法
        System.out.println(author + " is the author of " + favouriteBook.toUpperCase());
        favouriteBook = favouriteBook.toUpperCase();    //将字符串转换为大写
        System.out.println(favouriteBook);              //第14项完成

        System.out.println("Type something.");
        String something = input.next().toUpperCase();  //应用toUpperCase并赋值新的变量
        input.nextLine();                               //拍虫子:多了一个换行符,要消掉
        System.out.println(something);                  //第15项完成

        /*Mutable:可变性
        指对象的状态或内容在创建后是否可以更改
        */
        System.out.println(favouriteBook.toUpperCase() + favouriteBook.toLowerCase());

        //trim()方法:去除字符串前后的空白
        System.out.println("Type something again, this time needs to type some blank before and behind the content.");
        String userInput = input.nextLine();
        String trimmedUserInput = userInput.trim();
        System.out.println("the length of the input is " + userInput.length() + " before trim and " + trimmedUserInput.length() + " after trim");//18完成
        input.close();                                   //不能忘

        //substring()方法：从字符串中提取子字符串
        String firstWord = userInput.trim().substring(0, 1);
        String lastWord = userInput.trim().substring(userInput.length() - 1);
        userInput = userInput.trim().toLowerCase().substring(1, userInput.length() - 1);
        System.out.println(firstWord.toUpperCase() + userInput + lastWord.toUpperCase());
    }
}
