package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet1866 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        3,
                        2,
                        3
                },
                {
                        5,
                        5,
                        1
                },
                {
                        5,
                        4,
                        10
                },
                {
                        5,
                        3,
                        35
                },
                {
                        5,
                        2,
                        50
                },
                {
                        5,
                        1,
                        24
                },
                {
                        20,
                        11,
                        647427950
                },
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int n, int k, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet1866.Solution().rearrangeSticks(n, k), timer.stop());
    }

    class Solution {
        public int rearrangeSticks(int n, int k) {

            return 0;
        }
    }
}
