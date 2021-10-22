package com.doublejony.interview.class101;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Comparator;
import java.util.PriorityQueue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * A company has a list of expected revenues and payments for the upcoming year in chronological order. The problem is that at some moments in time the sum of previous payments can be larger than the total previous revenue, which would put the company in debt. To avoid this problem the company takes a very simple approach: it reschedules some expenses to the end of the year.
 * You are given an array of integers, where positive numbers represent revenues and negative numbers represent expenses, all in chronological order. In one move you can relocate any expense (negative number) to the end of the array. What is the minimum number of such relocations to make sure that the company never falls into debt (in other words: you need to ensure that there is no consecutive sequence of elements starting from the beginning of the array, that sums up to a negative number)?
 * You can assume that the sum of all elements in A is nonnegative.
 * Write a function:
 *
 * class Solution ( public int solution(int[] A):
 *
 * that, given an array A of N integers, returns the minimum number of relocations, so that company
 * never falls into debt.
 * Examples:
 * 1. Given A = [10, -10, -1, -1, 10], the function should return 1. It is enough to move -10 to the end of the array.
 * 2. Given A = [-1, -1, -1, 1, 1, 1, 1]. your function should return 3. The negative elements at the beginning must be moved to the end to avoid the debt at the start of the year.
 * 3. Given A = [5,-2, -3, 1], the answer is O. The company balance is always nonnegative.
 * Write an efficient algorithm for the following assumptions:
 * * N is an integer within the range [1.100,000];
 * * each element of array A is an integer within the range[-1,000,000,000..1,000,000,000];
 * * sum of all elements in A is greater than or equal to 0.
 */
@RunWith(DataProviderRunner.class)
public class Codility3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {10, -10, -1, -1, 10},
                        1
                },
                {
                        new int[] {-1, -1, -1, 1, 1, 1, 1},
                        3
                },
                {
                        new int[] {5, -2, -3, 1},
                        0
                },
                {
                        new int[] {5, -2, -3, -1, -2, -1, 1},
                        2
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int[] A, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(A), timer.stop());
    }

    class Solution {
        public int solution(int[] A) {
            int answer = 0;

            int revenues = 0;
            int payments = 0;

            PriorityQueue<Integer> paymentsQueue = new PriorityQueue<>(Comparator.reverseOrder());

            for (int current : A) {
                if (current > 0) {
                    revenues += current;
                } else if (current < 0) {
                    payments -= current;
                    paymentsQueue.add(Math.abs(current));
                }
                if (payments > revenues) {
                    payments -= paymentsQueue.poll();
                    answer++;
                }
            }

            return answer;
        }
    }
}
