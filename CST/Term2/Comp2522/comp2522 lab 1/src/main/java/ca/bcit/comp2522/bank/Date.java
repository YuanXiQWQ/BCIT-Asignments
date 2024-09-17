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

    // Information
    private static final String INFO_DATE_INVALID = "Invalid date provided";

    // Month codes
    private static final int[] MONTH_CODES = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};
    private static final String[] DAYS_OF_WEEK =
            {"Saturday", "Sunday", "Monday", "Tuesday",
                    "Wednesday", "Thursday", "Friday"};

    // Zeller's Congruence
    private static final int YEAR_2000_OFFSET = 6;
    private static final int YEAR_1900_OFFSET = 0;
    private static final int PRE_1900_OFFSET = 2;
    private static final int LEAP_YEAR_ADJUSTMENT = 6;

    // Months numbers
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
    public Date(final int year, final int month, final int day)
    {
        if (!isValidDate(year, month, day)) {
            throw new IllegalArgumentException(INFO_DATE_INVALID);
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Getter for the day.
     *
     * @return the day
     */
    public int getDay()
    {
        return this.day;
    }

    /**
     * Getter for the month.
     *
     * @return the month
     */
    public int getMonth()
    {
        return this.month;
    }

    /**
     * Getter for the year.
     *
     * @return the year
     */
    public int getYear()
    {
        return this.year;
    }

    /**
     * Getter for the formatted date in YYYY-MM-DD.
     *
     * @return the formatted date
     */
    public String getYYYYMMDD()
    {
        return String.format("%04d-%02d-%02d", this.year, this.month, this.day);
    }

    /**
     * Getter for the day of the week. Using several steps based on Zeller's Congruence to
     * calculate the day of the week step by step.
     *
     * @return the day of the week
     */
    public String getDayOfTheWeek()
    {
        /* Step 0: Calculate the year offset, if the year is before 2000, the offset is 6,
        if the year is after 2000, the offset is 0, otherwise it is 2 */
        final int yearOffset = (this.year >= 2000) ? YEAR_2000_OFFSET
                                                   : (this.year < 1900) ? YEAR_1900_OFFSET
                                                                        : PRE_1900_OFFSET;

        // Step 1-4: get the day sum
        final int daySum = getDaySum();

        // Step 5: Get the month code
        final int monthCode = getMonthCode(this.month);

        // Step 6: Add the previous five numbers and mod by 7
        final int result = (yearOffset + daySum + monthCode) % 7;

        // Step 7: Return the day of the week
        return getDayOfWeekName(result);
    }

    /**
     * Checks if the date is valid.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return true if the date is valid, false otherwise
     */
    private boolean isValidDate(final int year, final int month, final int day)
    {
        if (year < 1 || year > getCurrentYear()) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        return day >= 1 && day <= getMaxDaysInMonth(year, month);
    }

    /**
     * Calculates the day sum for calculating the day of the week.
     *
     * @return the day sum
     */
    private int getDaySum()
    {
        // Step 1: Calculate the number of twelves in last 2 numbers of the year
        final int twelveInYear = (this.year % 100) / 12;

        // Step 2: Calculate the remainder from step 1
        final int remainderOfTwelveInYear = (this.year % 100) - twelveInYear * 12;

        // Step 3: Calculate the number of fours from step 2
        final int foursInRemainder = remainderOfTwelveInYear / 4;

        // Step 4: Add the day of the month to each step above
        int daySum =
                this.day + twelveInYear + remainderOfTwelveInYear + foursInRemainder;

        // Extra adjustment: For January/February dates in leap years, add 6
        if (isLeapYear(this.year) && (this.month == JANUARY || this.month == FEBRUARY)) {
            daySum += LEAP_YEAR_ADJUSTMENT;
        }
        return daySum;
    }

    /**
     * Returns the maximum number of days in a given month and year.
     *
     * @param year  the year
     * @param month the month
     * @return the maximum number of days in the month
     */
    private int getMaxDaysInMonth(final int year, final int month)
    {
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
    private boolean isLeapYear(final int year)
    {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Checks if the month has 30 days.
     *
     * @param month the month
     * @return true if the month has 30 days
     */
    private boolean getThirtyDayMonth(final int month)
    {
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
    private int getMonthCode(final int month)
    {
        return MONTH_CODES[month - 1];
    }

    /**
     * Returns the day of the week name.
     *
     * @param dayOfWeek the day of the week
     * @return the day of the week name
     */
    private String getDayOfWeekName(final int dayOfWeek)
    {
        return DAYS_OF_WEEK[dayOfWeek];
    }

    /**
     * Returns the current year.
     *
     * @return the current year
     */
    private int getCurrentYear()
    {
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    }
}
