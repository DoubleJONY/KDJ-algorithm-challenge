package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet2145 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new int[] {1, -3, 4},
                        1,
                        6,
                        2
                },
                {
                        new int[] {3, -4, 5, 1, -2},
                        -4,
                        5,
                        4
                },
                {
                        new int[] {4, -7, 2},
                        3,
                        6,
                        0
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] differences, int lower, int upper, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected,
                new Solution().numberOfArrays(differences, lower, upper), timer.stop());
    }

    class Solution {
        public int numberOfArrays(int[] differences, int lower, int upper) {
            long a = 0;
            long max = 0;
            long min = 0;
            for (int d : differences) {
                a += d;
                max = Math.max(max, a);
                min = Math.min(min, a);
            }
            return (int) Math.max((upper - lower) - (max - min) + 1, 0);
        }
    }

}
