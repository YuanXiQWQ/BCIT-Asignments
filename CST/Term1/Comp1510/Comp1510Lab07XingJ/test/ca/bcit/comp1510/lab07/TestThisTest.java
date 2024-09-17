package ca.bcit.comp1510.lab07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestThisTest.
 *
 * @author XingJiarui
 * @version 2024.3.7
 */
class TestThisTest {
    /**
     * The TestThis object to test.
     */
    private TestThis test;

    @BeforeEach
    public void setUp() {
        test = new TestThis();
    }

    @Test
    void testLargestIntIntInt1() {
        int max = test.largest(1, 2, 3);
        assertEquals(3, max);
    }

    @Test
    void testLargestIntIntInt2() {
        int max = test.largest((int) Math.sqrt(1), (int) Math.sqrt(2), (int) Math.sqrt(3));
        assertEquals(1, max);
    }

    @Test
    void testLargestIntIntInt3() {
        int max = test.largest(-5, -2, -10);
        assertEquals(-2, max);
    }

    @Test
    void testLargestIntIntInt4() {
        int max = test.largest(1, 1, 1);
        assertEquals(1, max);
    }

    @Test
    void testLargestListOfInteger1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int max = test.largest(list);
        assertEquals(3, max);
    }

    @Test
    void testLargestListOfInteger2() {
        List<Integer> list = new ArrayList<>();
        list.add((int) Math.sqrt(1));
        list.add((int) Math.sqrt(2));
        list.add((int) Math.sqrt(3));
        int max = test.largest(list);
        assertEquals(1, max);
    }

    @Test
    void testLargestListOfInteger3() {
        List<Integer> list = new ArrayList<>();
        list.add(-5);
        list.add(-2);
        list.add(-10);
        int max = test.largest(list);
        assertEquals(-2, max);
    }

    @Test
    void testLargestListOfInteger4() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        int max = test.largest(list);
        assertEquals(1, max);
    }
}