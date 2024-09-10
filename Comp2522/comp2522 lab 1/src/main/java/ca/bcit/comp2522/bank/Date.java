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

    private static final int[] MONTH_CODES = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};

    // Zeller's Congruence
    private static final int YEAR_2000_OFFSET = 6;
    private static final int YEAR_1900_OFFSET = 0;
    private static final int PRE_1900_OFFSET = 2;
    private static final int LEAP_YEAR_ADJUSTMENT = 6;
    private static final int JANUARY = 1;
    private static final int FEBRUARY = 2;
    private static final int DAYS_IN_FEBRUARY_IN_LEAP_YEAR = 29;
    private static final int DAYS_IN_FEBRUARY_IN_NON_LEAP_YEAR = 28;
    private static final int[] MONTHS_WITH_30_DAYS = {4, 6, 9, 11};
    private static final int NUMBER_OF_DAYS_IN_MONTH_WITH_31_DAYS = 31;
    private static final int NUMBER_OF_DAYS_IN_MONTH_WITH_30_DAYS = 30;

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
     * Returns the day.
     *
     * @return the day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Returns the month.
     *
     * @return the month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Returns the year.
     *
     * @return the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the date in YYYY-MM-DD format.
     *
     * @return the formatted date
     */
    public String getYYYYMMDD() {
        return String.format("%04d-%02d-%02d", this.year, this.month, this.day);
    }

    /**
     * Returns the day of the week for the date.
     *
     * @return the day of the week
     */
    public String getDayOfTheWeek() {
        // Step 0: Calculate the year offset
        int yearOffset = (this.year >= 2000) ? YEAR_2000_OFFSET
                                             : (this.year < 1900) ? YEAR_1900_OFFSET
                                                                  : PRE_1900_OFFSET;
        // Step 1: Calculate the number of twelves in last 2 numbers of the year
        int twelveInYear = (this.year % 100) / 12;
        // Step 2: Calculate the remainder from step 1
        int remainderOfTwelveInYear = (this.year % 100) - twelveInYear * 12;
        // Step 3: Calculate the number of fours from step 2
        int foursInRemainder = remainderOfTwelveInYear / 4;
        // Step 4: Add the day of the month to each step above
        int daySum = this.day + twelveInYear + remainderOfTwelveInYear + foursInRemainder;
        // Extra notes: For January/February dates in leap years, add 6 at the start
        if (isLeapYear(this.year) && (this.month == JANUARY || this.month == FEBRUARY)) {
            daySum += LEAP_YEAR_ADJUSTMENT;
        }
        // Step 5: Get the month code
        int monthCode = getMonthCode(this.month);
        // Step 6: Add the previous five numbers and mod by 7
        int result = (yearOffset + daySum + monthCode) % 7;
        // Step 7: Return the day of the week
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
        if (month == FEBRUARY) {
            return isLeapYear(year) ? DAYS_IN_FEBRUARY_IN_LEAP_YEAR
                                    : DAYS_IN_FEBRUARY_IN_NON_LEAP_YEAR;
        } else if (getThirtyDayMonth(month)) {
            return NUMBER_OF_DAYS_IN_MONTH_WITH_30_DAYS;
        } else {
            return NUMBER_OF_DAYS_IN_MONTH_WITH_31_DAYS;
        }
    }

    /**
     * Checks if the year is a leap year.
     *
     * @param year the year
     * @return true if the year is a leap year
     */
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Checks if the month has 30 days.
     *
     * @param month the month
     * @return true if the month has 30 days
     */
    private boolean getThirtyDayMonth(int month) {
        for (int m : MONTHS_WITH_30_DAYS) {
            if (month == m) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the month code.
     *
     * @param month the month
     * @return the month code
     */
    private int getMonthCode(int month) {
        return MONTH_CODES[month - 1];
    }

    /**
     * Returns the day of the week name.
     *
     * @param dayOfWeek the day of the week
     * @return the day of the week name
     */
    private String getDayOfWeekName(int dayOfWeek) {
        String[] days =
                {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                        "Friday"};
        return days[dayOfWeek];
    }

    /**
     * Returns the current year.
     *
     * @return the current year
     */
    private int getCurrentYear() {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }
}
