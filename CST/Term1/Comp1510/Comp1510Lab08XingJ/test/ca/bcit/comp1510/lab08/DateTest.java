package ca.bcit.comp1510.lab08;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * To test the Date class
 *
 * @author Xing Jiarui
 * @version 0.1.0
 */
public class DateTest {

    @Test
    void isMonthValid() {
        assertTrue(Date.isMonthValid(1));
        assertTrue(Date.isMonthValid(12));
        assertFalse(Date.isMonthValid(0));
        assertFalse(Date.isMonthValid(13));
    }

    @Test
    void isYearValid() {
        assertTrue(Date.isYearValid(1582));
        assertTrue(Date.isYearValid(2999));
        assertFalse(Date.isYearValid(1581));
        assertFalse(Date.isYearValid(3000));
    }

    @Test
    void isLeapYear() {
        assertTrue(Date.isLeapYear(1600));
        assertTrue(Date.isLeapYear(1592));
        assertFalse(Date.isLeapYear(1700));
        assertFalse(Date.isLeapYear(1594));
    }

    @Test
    void daysInMonth() {
        assertEquals(31, Date.daysInMonth(1, false));
        assertEquals(28, Date.daysInMonth(2, false));
        assertEquals(29, Date.daysInMonth(2, true));
        assertEquals(30, Date.daysInMonth(4, false));
        assertEquals(0, Date.daysInMonth(13, false));
    }

    @Test
    void isDateValid() {
        assertTrue(Date.isDateValid(15, 3, 2021));
        assertFalse(Date.isDateValid(29, 2, 2021));
        assertTrue(Date.isDateValid(29, 2, 2020));
        assertFalse(Date.isDateValid(31, 4, 2021));
    }
}
