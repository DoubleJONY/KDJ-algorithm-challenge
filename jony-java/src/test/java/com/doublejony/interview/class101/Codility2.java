package com.doublejony.interview.class101;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * You are given an implementation of a function:
 * class Solution { public boolean solution(int[] A, int K); }
 * This function, given a non-empty array A of N integers (sorted in non-decreasing order) and integer K,
 * checks whether A contains numbers 1, 2,..., K (every number from 1 to K at least once) and no other
 * numbers.
 * For example, given the following array A, and K = 3:
 * A[0] = 1
 * A[1] = 1
 * A[2] = 2
 * A[3] = 3
 * A[4] = 3
 * The function should return true.
 * For the following array A, and K = 2:
 * A[0] = 1
 * A[1] = 1
 * A[2] = 3
 * the function should return false.
 * The attached code is still incorrect for some inputs. Despite the error(s), the code may produce a correct answer for the example test cases. The goal of the exercise is to find and fix the bug(s) in the implementation. You can modify at most two lines.
 * Assume that:
 * - N and K are integers within the range [1.300,000]:
 * - each element of array A is an integer within the range (0..1,000,000,000 ;
 * - array A is sorted in non-decreasing order.
 * In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.
 * Copyright 2009-2021 by Codility Limited. All Rights Reserved. Unauthorized copying, publication or disclosure prohibited.
 */
@RunWith(DataProviderRunner.class)
public class Codility2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {1, 1, 2, 3, 3},
                        3,
                        true
                },
                {
                        new int[] {1, 1, 3},
                        2,
                        false
                },
                {
                        new int[] {1, 1, 2, 3, 3, 4, 5, 6, 7},
                        7,
                        true
                },
                {
                        new int[] {1, 1, 2, 3, 3, 4, 5, 6},
                        7,
                        false
                },
                {
                        new int[] {2, 2, 3, 3, 4, 5, 6},
                        7,
                        false
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int[] A, int K, boolean expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(A, K), timer.stop());
    }

    //문제의 솔루션을 2줄 이내로 고쳐라
    class errorSolution {
        public boolean solution(int[] A, int K) {
            int n = A.length;
            for (int i = 0; i < n - 1; i++) {
                if (A[i] + 1 < A[i + 1])
                    return false;
            }
            if (A[0] != 1 && A[n - 1] != K)
                return false;
            else
                return true;
        }
    }

    class Solution {
        public boolean solution(int[] A, int K) {
            int n = A.length;
            for (int i = 0; i < n - 1; i++) {
                if (A[i] + 1 < A[i + 1])
                    return false;
            }
            if (A[0] != 1 || A[n - 1] != K)
                return false;
            else
                return true;
        }
    }
}
