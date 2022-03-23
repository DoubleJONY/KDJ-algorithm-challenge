package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet1388 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{1, 2, 3, 4, 5, 6},
                        10
                },
                {
                        new int[]{8, 9, 8, 6, 1, 1},
                        16
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] slices, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet1388.Solution().maxSizeSlices(slices), timer.stop());
    }

    class Solution {
        public int maxSizeSlices(int[] ss) {
            int m = ss.length, n = m / 3;
            int[] ss1 = Arrays.copyOfRange(ss, 0, m - 1), ss2 = Arrays.copyOfRange(ss, 1, m);
            return Math.max(getMax(ss1, n), getMax(ss2,n));
        }

        private int getMax(int[] arr, int n) {
            int m = arr.length, dp[][] = new int[m + 1][n + 1];// dp[i][j] is maximum sum which we pick `j` elements from linear array `i` elements
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[i][j] = Math.max(dp[i - 1][j],  //not pick ith
                            (i - 2 < 0 ? 0 : dp[i - 2][j - 1]) + arr[i - 1]); // pick ith;
                }
            }
            return dp[m][n];
        }
    }
}
