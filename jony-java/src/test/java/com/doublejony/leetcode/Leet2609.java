package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet2609 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        "001",
                        2
                },
                {
                        "01000111",
                        6
                },
                {
                        "00111",
                        4
                },
                {
                        "111",
                        0
                },
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String s, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected,
                new Solution().findTheLongestBalancedSubstring(s), timer.stop());
    }

    class Solution {

        public int findTheLongestBalancedSubstring(String s) {
            int zCount = 0;
            int oCount = 0;
            boolean isOne = false;

            int answer = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '0':
                        if (isOne) {
                            zCount = 0;
                            oCount = 0;
                            isOne = false;
                        }
                        zCount++;
                        break;
                    case '1':
                        oCount++;
                        isOne = true;
                        break;
                }

                if (zCount >= oCount) {
                    answer = Math.max(answer, oCount * 2);
                }
            }

            return answer;
        }
    }

}
