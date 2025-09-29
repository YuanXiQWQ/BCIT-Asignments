package bookstore;

/**
 * Represents a date with year, month, and day.
 *
 * @author Daniel Wang
 * @author Jack Le
 * @author Jiarui Xing
 * @author Uppnoor Panesar
 * @version 1.0
 */
public record Date(int year, int month, int day) implements Printable {
    private static final int MIN_YEAR = 1600;
    private static final int CENTURY_1600S = 1600;
    private static final int CENTURY_1700S = 1700;
    private static final int CENTURY_1800S = 1800;
    private static final int CENTURY_1900S = 1900;
    private static final int CENTURY_2000S = 2000;

    private static final int CENTURY_1600S_CODE = 6;
    private static final int CENTURY_1700S_CODE = 4;
    private static final int CENTURY_1800S_CODE = 2;
    private static final int CENTURY_1900S_CODE = 0;
    private static final int CENTURY_2000S_CODE = 6;

    private static final int MIN_DAY = 1;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;

    private static final int LEAP_YEAR_JAN_FEB_ADJUSTMENT = 6;

    private static final int DAYS_IN_FEB_LEAP = 29;
    private static final int DAYS_IN_FEB_NON_LEAP = 28;
    private static final int DAYS_IN_30_MONTH = 30;
    private static final int DAYS_IN_31_MONTH = 31;

    private static final int[] MONTH_CODES = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};

    private static final int SATURDAY = 0;
    private static final int SUNDAY = 1;
    private static final int MONDAY = 2;
    private static final int TUESDAY = 3;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 5;
    private static final int FRIDAY = 6;

    private static final int JANUARY = 1;
    private static final int FEBRUARY = 2;

    private static final int DAYS_IN_WEEK = 7;

    private static final int LEAP_YEAR_DIVISOR_4 = 4;
    private static final int LEAP_YEAR_DIVISOR_100 = 100;
    private static final int LEAP_YEAR_DIVISOR_400 = 400;

    private static final int LAST_TWO_DIGITS_DIVISOR = 100;
    private static final int MONTH_INDEX_OFFSET = 1;

    private static final String[] MONTH_NAMES =
            {"January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December"};

    /**
     * Constructs a Date object with the specified year, month, and day.
     *
     * @param year  the year, must be between 1600 and the current year
     * @param month the month, must be between 1 and 12
     * @param day   the day, must be valid for the given month and year
     * @throws IllegalArgumentException if any argument is invalid
     */
    public Date
    {
        int currentYear = java.time.Year.now().getValue();
        if(year < MIN_YEAR || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year must be between" + MIN_YEAR + " and " + currentYear);
        }
        if(month < MIN_MONTH || month > MAX_MONTH) {
            throw new IllegalArgumentException(
                    "Month must be between" + MIN_MONTH + " and " + MAX_MONTH);
        }
        if(day < MIN_DAY || day > daysInMonth(year, month)) {
            throw new IllegalArgumentException(
                    "Invalid day for the given month and year.");
        }
    }

    /**
     * Returns the day of the month.
     *
     * @return the day
     */
    @Override
    public int day()
    {
        return day;
    }

    /**
     * Returns the month.
     *
     * @return the month
     */
    @Override
    public int month()
    {
        return month;
    }

    /**
     * Returns the year.
     *
     * @return the year
     */
    @Override
    public int year()
    {
        return year;
    }

    /**
     * Returns the date in YYYY-MM-DD format.
     *
     * @return the formatted date
     */
    public String getYYYYMMDD()
    {
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    /**
     * Calculates and returns the day of the week.
     *
     * @return the day of the week as a string (e.g., "Monday")
     */
    public String getDayOfTheWeek()
    {
        int code = calculateDayOfWeek();
        return switch(code) {
            case SATURDAY -> "Saturday";
            case SUNDAY -> "Sunday";
            case MONDAY -> "Monday";
            case TUESDAY -> "Tuesday";
            case WEDNESDAY -> "Wednesday";
            case THURSDAY -> "Thursday";
            case FRIDAY -> "Friday";
            default -> "Unknown";
        };
    }

    /**
     * Displays the date in a formatted manner.
     */
    @Override
    public void display()
    {
        System.out.println(getYYYYMMDD());
    }

    /**
     * Helper method to determine the number of days in a given month.
     *
     * @param year  the year
     * @param month the month
     * @return the number of days in the month
     */
    private int daysInMonth(int year, int month)
    {
        return switch(month) {
            case 2 -> (isLeapYear(year)) ? DAYS_IN_FEB_LEAP : DAYS_IN_FEB_NON_LEAP;
            case 4, 6, 9, 11 -> DAYS_IN_30_MONTH;
            default -> DAYS_IN_31_MONTH;
        };
    }

    /**
     * Helper method to determine if a year is a leap year.
     *
     * @param year the year
     * @return true if leap year, false otherwise
     */
    private boolean isLeapYear(int year)
    {
        return (year % LEAP_YEAR_DIVISOR_4 == 0 &&
                (year % LEAP_YEAR_DIVISOR_100 != 0 || year % LEAP_YEAR_DIVISOR_400 == 0));
    }

    /**
     * Calculates the day of the week based on the specified algorithm.
     *
     * @return the day of the week code
     */
    private int calculateDayOfWeek()
    {
        int number = 0;
        // Step 0: Adjust based on the century
        if(year >= CENTURY_1600S && year < CENTURY_1700S) {
            number += CENTURY_1600S_CODE;
        } else if(year >= CENTURY_1700S && year < CENTURY_1800S) {
            number += CENTURY_1700S_CODE;
        } else if(year >= CENTURY_1800S && year < CENTURY_1900S) {
            number += CENTURY_1800S_CODE;
        } else if(year >= CENTURY_1900S && year < CENTURY_2000S) {
            number += CENTURY_1900S_CODE;
        } else if(year >= CENTURY_2000S) {
            number += CENTURY_2000S_CODE;
        }

        int yy = year % LAST_TWO_DIGITS_DIVISOR;
        // Step 1: Calculate the number of twelves in the last two digits
        number += yy / 12;
        // Step 2: Remainder after removing twelves
        number += yy % 12;
        // Step 3: Number of fours in the remainder
        number += (yy % 12) / 4;
        // Step 4: Add the day of the month
        number += day;
        // Step 5: Add the month code
        number += getMonthCode(month, isLeapYear(year));
        // Step 6: Modulo 7
        number %= DAYS_IN_WEEK;
        return number;
    }

    /**
     * Returns the month code based on the month and leap year status.
     *
     * @param month  the month
     * @param isLeap whether it's a leap year
     * @return the month code
     */
    private int getMonthCode(int month, boolean isLeap)
    {
        // Month codes: jfmamjjasond: 1,4,4,0,2,5,0,3,6,1,4,6
        int code = MONTH_CODES[month - MONTH_INDEX_OFFSET];
        // Adjust for January and February in leap years
        if(isLeap && (month == JANUARY || month == FEBRUARY)) {
            code += LEAP_YEAR_JAN_FEB_ADJUSTMENT;
        }
        return code;
    }

    /**
     * Overrides equals to compare year, month, and day.
     *
     * @param obj the object to compare
     * @return true if dates are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Date other)) {
            return false;
        }
        return year == other.year && month == other.month && day == other.day;
    }

    /**
     * Returns a string representation of the date.
     *
     * @return the formatted date string
     */
    @Override
    public String toString()
    {
        return getDayOfTheWeek() + ", " + getMonthName() + " " + day + ", " + year;
    }

    /**
     * Returns the month name based on the month number.
     *
     * @return the month name
     */
    private String getMonthName()
    {
        return MONTH_NAMES[month - MONTH_INDEX_OFFSET];
    }
}
