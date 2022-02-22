package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet873 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] { 1, 2, 3, 4, 5, 6, 7, 8 },
                        5
                },
                {
                        new int[] { 1, 3, 7, 11, 12, 14, 18 },
                        3
                },
                {
                        new int[] { 2, 4, 7, 8, 9, 10, 14, 15, 18, 23, 32, 50 },
                        5
                },
                {
                        new int[] { 1, 3, 5 },
                        0
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] arr, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet873.Solution().lenLongestFibSubseq(arr), timer.stop());
    }

    class Solution {
        public int lenLongestFibSubseq(int[] arr) {

            int maxLength = 0;

            Map<String, Integer> cache = new HashMap<>();

            for (int i = 0; i < arr.length - 1; i++) {
                int length;
                for (int j = i + 1; j < arr.length; j++) {
                    length = 2;
                    int right = arr[j];
                    int sum = arr[i] + right;
//                    String key = right + "," + sum;
//                    cache.put(key, length);
                    for (int k = i + 2; k < arr.length; k++) {
                        if (arr[k] > sum) {
                            break;
//                        } else if (cache.containsKey(key))
                        } else if (arr[k] == sum) {
                            sum = arr[k] + right;
                            right = arr[k];
                            length++;
                        }
                    }
//                    cache.put(key, length);
                    maxLength = Math.max(maxLength, length);
                }
            }

            return maxLength == 2 ? 0 : maxLength;
        }
    }
}
