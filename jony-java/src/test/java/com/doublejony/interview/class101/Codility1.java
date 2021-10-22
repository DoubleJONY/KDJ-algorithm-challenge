package com.doublejony.interview.class101;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * Write a loop, that prints all three-digits numbers in decreasing order (999, 998, 997, ..., 101, 100)
 */
@RunWith(DataProviderRunner.class)
public class Codility1 {

    @Test
    public void useFullScanLoop() {

        Stopwatch timer = Stopwatch.createStarted();
        new Solution().solution(null);
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), timer.stop());
    }

    class Solution {
        public void solution(String[] args) {
            for (int i = 999; i >= 100; i--) {
                System.out.println(i);
            }
        }
    }
}
