package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet329 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[][]{
                                {9, 9, 4},
                                {6, 6, 8},
                                {2, 1, 1},
                        },
                        4
                },
                {
                        new int[][]{
                                {3, 4, 5},
                                {3, 2, 6},
                                {2, 2, 1}
                        },
                        4
                },
                {
                        new int[][]{
                                {1}
                        },
                        1
                },
                {
                        new int[][]{
                                { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                                { 19, 18, 17, 16, 15, 14, 13, 12, 11, 10 },
                                { 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 },
                                { 39, 38, 37, 36, 35, 34, 33, 32, 31, 30 },
                                { 40, 41, 42, 43, 44, 45, 46, 47, 48, 49 },
                                { 59, 58, 57, 56, 55, 54, 53, 52, 51, 50 },
                                { 60, 61, 62, 63, 64, 65, 66, 67, 68, 69 },
                                { 79, 78, 77, 76, 75, 74, 73, 72, 71, 70 },
                                { 80, 81, 82, 83, 84, 85, 86, 87, 88, 89 },
                                { 99, 98, 97, 96, 95, 94, 93, 92, 91, 90 },
                                { 100, 101, 102, 103, 104, 105, 106, 107, 108, 109 },
                                { 119, 118, 117, 116, 115, 114, 113, 112, 111, 110 },
                                { 120, 121, 122, 123, 124, 125, 126, 127, 128, 129 },
                                { 139, 138, 137, 136, 135, 134, 133, 132, 131, 130 },
                                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                        },
                        140
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[][] matrix, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet329.Solution().longestIncreasingPath(matrix), timer.stop());
    }

    class Solution {

        int[][] MATRIX;
        public int longestIncreasingPath(int[][] matrix) {

            MATRIX = matrix;

            int maxDepth = 0;
            for (int i = 0; i < MATRIX.length; i++) {
                for (int j = 0; j < MATRIX[i].length; j++) {
                    maxDepth = Math.max(dfs(1, i, j), maxDepth);
                }
            }
            return maxDepth;
        }

        public int dfs(int depth, int x, int y) {

            int maxDepth = depth;
            if (x - 1 >= 0 && MATRIX[x - 1][y] > MATRIX[x][y]) {
                maxDepth = Math.max(dfs(depth + 1, x - 1, y), maxDepth);
            }
            if (x + 1 < MATRIX.length && MATRIX[x + 1][y] > MATRIX[x][y]) {
                maxDepth = Math.max(dfs(depth + 1, x + 1, y), maxDepth);
            }
            if (y - 1 >= 0 && MATRIX[x][y - 1] > MATRIX[x][y]) {
                maxDepth = Math.max(dfs(depth + 1, x, y - 1), maxDepth);
            }
            if (y + 1 < MATRIX[0].length && MATRIX[x][y + 1] > MATRIX[x][y]) {
                maxDepth = Math.max(dfs(depth + 1, x, y + 1), maxDepth);
            }
            return maxDepth;
        }
    }
}
