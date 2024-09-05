package ca.bcit.comp2522.bank;

/**
 * Represents a date with year, month, and day. Handles validation for valid dates.
 *
 * @author Jiarui Xing
 * @version 1.0
 */
public class Date {
    private final int year;
    private final int month;
    private final int day;

    private static final int SATURDAY = 0;
    private static final int SUNDAY = 1;
    private static final int MONDAY = 2;
    private static final int TUESDAY = 3;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 5;
    private static final int FRIDAY = 6;

    private static final int[] MONTH_CODES = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};

    /**
     * Constructs a new Date object.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @throws IllegalArgumentException if the date is invalid
     */
    public Date(int year, int month, int day) {
        if (year < 1 || year > getCurrentYear() || month < 1 || month > 12 || day < 1 ||
                day > getMaxDaysInMonth(year, month)) {
            throw new IllegalArgumentException("Invalid date provided");
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Returns the year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Returns the day.
     *
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns the date in YYYY-MM-DD format.
     *
     * @return the formatted date
     */
    public String getYYYYMMDD() {
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    /**
     * Returns the day of the week for the date.
     *
     * @return the day of the week
     */
    public String getDayOfTheWeek() {
        int yearOffset = (year >= 2000) ? 6 : (year < 1900) ? 2 : 0;
        int twelveInYear = (year % 100) / 12;
        int remainder = (year % 100) - twelveInYear * 12;
        int foursInRemainder = remainder / 4;
        int daySum = day + twelveInYear + remainder + foursInRemainder;

        if (isLeapYear(year) && (month == 1 || month == 2)) {
            daySum += 6;
        }

        int monthCode = getMonthCode(month);
        int result = (daySum + monthCode + yearOffset) % 7;

        return getDayOfWeekName(result);
    }

    /**
     * Returns the maximum number of days in a given month and year.
     *
     * @param year  the year
     * @param month the month
     * @return the maximum number of days in the month
     */
    private int getMaxDaysInMonth(int year, int month) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private int getMonthCode(int month) {
        return MONTH_CODES[month - 1];
    }

    private String getDayOfWeekName(int dayOfWeek) {
        String[] days =
                {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                        "Friday"};
        return days[dayOfWeek];
    }

    private int getCurrentYear() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }
}