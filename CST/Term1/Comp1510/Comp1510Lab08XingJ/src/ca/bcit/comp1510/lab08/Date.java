package ca.bcit.comp1510.lab08;

import java.util.Scanner;


/**
 * Represent a valid Gregorian date on or after 24 February 1582
 *
 * @author blink
 */
public class Date {
    /**
     * day of the month.  1 to max days in month
     */
    private int day;

    /**
     * month of year.  1 to 12
     */
    private int month;

    /**
     * year in Gregorian calendar.  1001 .. 2999
     */
    private int year;

    /**
     * Constructor
     *
     * @param theDay   the day
     * @param theMonth the month
     * @param theYear  the year
     */
    public Date(int theDay, int theMonth, int theYear) throws IllegalArgumentException {
        if (!isDateValid(theDay, theMonth, theYear)) {
            throw new IllegalArgumentException("Invalid date: " + theYear + "/" + theMonth + "/" + theDay);
        }
        day = theDay;
        month = theMonth;
        year = theYear;
    }

    /**
     * To check if month is valid
     *
     * @param m the month
     * @return true if month is valid
     */
    public static boolean isMonthValid(int m) {
        return m >= 1 && m <= 12;
    }

    /**
     * To check if year is valid
     *
     * @param year the year
     * @return true if year is valid
     */
    public static boolean isYearValid(int year) {
        return year >= 1582 && year <= 2999;
    }

    /**
     * To check if year is a leap year
     *
     * @param year the year
     * @return true if year is a leap year
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * To determine number of days in month
     *
     * @param month      the month
     * @param isLeapYear true if year is a leap year
     * @return number of days in month
     */
    public static int daysInMonth(int month, boolean isLeapYear) {
        return switch (month) {
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 2 -> isLeapYear ? 29 : 28;
            case 4, 6, 9, 11 -> 30;
            default -> 0;
        };
    }

    /**
     * To check if the date is valid
     *
     * @param day   the day
     * @param month the month
     * @param year  the year
     * @return true if date is valid
     */
    public static boolean isDateValid(int day, int month, int year) {
        return isYearValid(year) && isMonthValid(month) && day <= daysInMonth(month, isLeapYear(year)) && day > 0;
    }

    public static void main(String[] args) {
        //date read in from user
        Scanner scan = new Scanner(System.in);
        int input;
        do {
            System.out.println("Enter month, day, and year: YYYYMMDD, e.g. 20040304");
            input = scan.hasNextInt() ? scan.nextInt() : scan.next().charAt(0) - 127;
        } while (input < 0);
        scan.close();
        //Get integer month, day, and year from user
        int year = input / 10000;
        int month = (input / 100) % 100;
        int day = input % 100;

        try {
            Date date = new Date(day, month, year);
            //Use the methods to determine whether it's a leap year
            String msg = isLeapYear(year) ? " is " : " is not ";
            //Print the appropriate message
            System.out.println("Date is valid, " + year + msg + "a leap year");
        } catch (IllegalArgumentException e) {
            System.out.println("Date is invalid");
        }

        //Other required methods:
        //true if parts of input from user are valid
        boolean monthValid = isMonthValid(month), yearValid = isYearValid(year), dayValid = isDateValid(day, month, year);
        //true if user's year is a leap year
        boolean leapYear = isLeapYear(year);
        //Use the methods to determine number of days in month
        int daysInMonth = daysInMonth(month, leapYear);
    }
}