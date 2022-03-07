package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet81 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] { 2, 5, 6, 0, 0, 1, 2 },
                        0,
                        true
                },
                {
                        new int[] { 2, 5, 6, 0, 0, 1, 2 },
                        3,
                        false
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] nums, int target, boolean expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet81.Solution().search(nums, target), timer.stop());
    }

    class Solution {
        public boolean search(int[] nums, int target) {

            return Arrays.stream(nums).boxed().collect(Collectors.toList()).contains(target);
        }
    }
}
