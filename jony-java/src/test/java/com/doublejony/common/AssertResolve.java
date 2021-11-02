package com.doublejony.common;

import com.google.common.base.Stopwatch;
import org.junit.Assert;

import java.util.Arrays;

public class AssertResolve {

    public static void resolve(String testType, Stopwatch timer) {

        System.out.println(testType
                + " || " + timer
        );
    }

    public static void resolve(String testType, boolean expected, boolean answer, Stopwatch timer) {

        Assert.assertEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }

    public static void resolve(String testType, boolean[] expected, boolean[] answer, Stopwatch timer) {

        Assert.assertArrayEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }

    public static void resolve(String testType, int expected, int answer, Stopwatch timer) {

        Assert.assertEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }

    public static void resolve(String testType, long expected, long answer, Stopwatch timer) {

        Assert.assertEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }

    public static void resolve(String testType, double expected, double answer, Stopwatch timer) {

        Assert.assertEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }

    public static void resolve(String testType, String expected, String answer, Stopwatch timer) {

        Assert.assertEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + expected
                + " || answer : " + answer
                + " || " + timer
        );
    }

    public static void resolve(String testType, int[] expected, int[] answer, Stopwatch timer) {

        Assert.assertArrayEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + Arrays.toString(expected)
                + " || answer : " + Arrays.toString(answer)
                + " || " + timer
        );
    }

    public static void resolve(String testType, int[][] expected, int[][] answer, Stopwatch timer) {

        Assert.assertArrayEquals(expected, answer);
        System.out.println(testType
                + " || expected : " + Arrays.toString(expected)
                + " || answer : " + Arrays.toString(answer)
                + " || " + timer
        );
    }
}
