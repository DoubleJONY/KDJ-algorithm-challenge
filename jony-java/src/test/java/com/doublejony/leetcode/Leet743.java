package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.doublejony.common.AssertResolve.resolve;


@RunWith(DataProviderRunner.class)
public class Leet743 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[][]{
                                {2, 1, 1},
                                {2, 3, 1},
                                {3, 4, 1}
                        },
                        4,
                        2,
                        2
                },
                {
                        new int[][]{
                                {1, 2, 1}
                        },
                        2,
                        1,
                        1
                },
                {
                        new int[][]{
                                {1, 2, 1}
                        },
                        2,
                        2,
                        -1
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[][] times, int n, int k, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet743.Solution().networkDelayTime(times, n, k), timer.stop());
    }

    class Solution {
        public int networkDelayTime(int[][] times, int n, int k) {

            Map<Integer, List<Integer>> nodeMap = new HashMap<>();

            for (int[] time : times) {
                nodeMap.put(time[0], new ArrayList<>());
                nodeMap.get(time[0]).add(time[1]);
                nodeMap.get(time[0]).add(time[2]);
            }

            return 1;
        }
    }
}
