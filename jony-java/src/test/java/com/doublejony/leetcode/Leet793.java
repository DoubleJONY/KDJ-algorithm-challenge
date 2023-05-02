package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * Let f(x) be the number of zeroes at the end of x!. Recall that x! = 1 * 2 * 3 * ... * x and by convention, 0! = 1.
 *
 * For example, f(3) = 0 because 3! = 6 has no zeroes at the end, while f(11) = 2 because 11! = 39916800 has two zeroes at the end.
 * Given an integer k, return the number of non-negative integers x have the property that f(x) = k.
 */
@RunWith(DataProviderRunner.class)
public class Leet793 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        0,
                        5
                },
                {
                        5,
                        0
                },
                {
                        3,
                        5
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int k, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected,
                new Solution().preimageSizeFZF(k), timer.stop());
    }

//    class Solution {
//        public int preimageSizeFZF(int k) {
//            if (k == 0) {
//                return 5;
//            }
//            double x = 1;
//            int count = 0;
//            while (true) {
//                String factValue = String.valueOf(factorial(x));
//                int zeroLength = getEndZeroCount(factValue);
//                if (zeroLength == k) {
//                    count++;
//                } else if (zeroLength > k){
//                    return count;
//                }
//                x++;
//            }
//        }
//
//        private double factorial(double n) {
//            return n == 0 ? 1 :n * factorial(n - 1);
//        }
//
//        private int getEndZeroCount(String value) {
//            return value.endsWith("0") ? getEndZeroCount(value.substring(0, value.length() - 1)) + 1 : 0;
//        }
//    }

    class Solution {
        public int preimageSizeFZF(int k) {
            if (k == 0) {
                return 5;
            }
            BigDecimal x = BigDecimal.valueOf(1);
            int count = 0;
            while (true) {
                String factValue = String.valueOf(factorial(x));
                int zeroLength = getEndZeroCount(factValue);
                if (zeroLength == k) {
                    count++;
                } else if (zeroLength > k){
                    return count;
                }
                x = x.add(BigDecimal.ONE);
            }
        }

        private BigDecimal factorial(BigDecimal n) {
            return n.equals(BigDecimal.ZERO) ? BigDecimal.valueOf(1) : (n.multiply(factorial(n.subtract(BigDecimal.valueOf(1)))));
        }

        private int getEndZeroCount(String value) {
            return value.endsWith("0") ? getEndZeroCount(value.substring(0, value.length() - 1)) + 1 : 0;
        }
    }

}
