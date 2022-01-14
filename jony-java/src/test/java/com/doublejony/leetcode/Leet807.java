package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class Leet807 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[][]{
                                {3, 0, 8, 4},
                                {2, 4, 5, 7},
                                {9, 2, 6, 3},
                                {0, 3, 1, 0}
                        },
                        35
                },
                {
                        new int[][]{
                                {0, 0, 0},
                                {0, 0, 0},
                                {0, 0, 0}
                        },
                        0
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[][] grid, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet807.Solution().maxIncreaseKeepingSkyline(grid), timer.stop());
    }

    /**
     * https://leetcode.com/submissions/detail/619536408/
     *
     * 133 / 133 test cases passed.
     * Status: Accepted
     * Runtime: 1 ms
     * Memory Usage: 39.2 MB
     */
    class Solution {

        int[] southLine;
        int[] eastLine;

        public int maxIncreaseKeepingSkyline(int[][] grid) {
            southLine = new int[grid[0].length];
            eastLine = new int[grid.length];

            setSkyLine(grid);
            return getIncreaseCount(grid);
        }

        private int getIncreaseCount(int[][] grid) {
            int count = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    int value = grid[i][j];
                    int skyLine = Math.min(southLine[j], eastLine[i]);
                    if (value < skyLine) {
                        count += skyLine - value;
                    }
                }
            }
            return count;
        }

        private void setSkyLine(int[][] grid) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    int value = grid[i][j];
                    eastLine[i] = Math.max(eastLine[i], value);
                    southLine[j] = Math.max(southLine[j], value);
                }
            }
        }
    }
}
