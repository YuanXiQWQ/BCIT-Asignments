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
    private static final int SATURDAY = 0;
    private static final int SUNDAY = 1;
    private static final int MONDAY = 2;
    private static final int TUESDAY = 3;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 5;
    private static final int FRIDAY = 6;

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
        if(year < 1600 || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year must be between 1600 and " + currentYear);
        }
        if(month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        if(day < 1 || day > daysInMonth(year, month)) {
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
            case 2 -> (isLeapYear(year)) ? 29 : 28;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
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
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
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
        if(year >= 1600 && year < 1700) {
            number += 6;
        } else if(year >= 1700 && year < 1800) {
            number += 4;
        } else if(year >= 1800 && year < 1900) {
            number += 2;
        } else if(year >= 1900 && year < 2000) {
            number += 0;
        } else if(year >= 2000) {
            number += 6;
        }

        int yy = year % 100;
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
        number %= 7;
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
        int[] monthCodes = {1, 4, 4, 0, 2, 5, 0, 3, 6, 1, 4, 6};
        int code = monthCodes[month - 1];
        // Adjust for January and February in leap years
        if(isLeap && (month == 1 || month == 2)) {
            code += 6;
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
        String[] months =
                {"January", "February", "March", "April", "May", "June", "July", "August",
                        "September", "October", "November", "December"};
        return months[month - 1];
    }
}
