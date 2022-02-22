package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet956 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {1, 2, 3, 6},
                        6
                },
                {
                        new int[] {1, 2, 3, 4, 5, 6},
                        10
                },
                {
                        new int[] {1, 2},
                        0
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] rods, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet956.Solution().tallestBillboard(rods), timer.stop());
    }

    class Solution {
        public int tallestBillboard(int[] rods) {

            return 0;
        }
    }
}
