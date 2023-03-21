package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet1442 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{2, 3, 1, 6, 7},
                        4
                },
                {
                        new int[]{1, 1, 1, 1, 1},
                        10
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] arr, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet1442.Solution().countTriplets(arr), timer.stop());
    }

    class Solution {

        public int countTriplets(int[] arr) {
            int count = 0;
            for (int i = 0; i < arr.length - 1; i++) {
                for (int j = i+1; j < arr.length; j++) {
                    for (int k = j; k < arr.length; k++) {
                        if (calA(arr, i, j) == calB(arr, j, k)) {
                            count++;
                        }
                    }
                }
            }
            return count;
        }

        private int calA(int[] arr, int a, int b) {
            int result = arr[a];
            for (int i = a + 1; i < b; i++) {
                result = result ^ arr[i];
            }
            return result;
        }

        private int calB(int[] arr, int a, int b) {
            int result = arr[a];
            for (int i = a + 1; i <= b; i++) {
                result = result ^ arr[i];
            }
            return result;
        }
    }
}
