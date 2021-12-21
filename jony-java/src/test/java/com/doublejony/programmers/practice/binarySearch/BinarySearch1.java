package com.doublejony.programmers.practice.binarySearch;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 입국심사
 * n명이 입국심사를 위해 줄을 서서 기다리고 있습니다. 각 입국심사대에 있는 심사관마다 심사하는데 걸리는 시간은 다릅니다.
 *
 * 처음에 모든 심사대는 비어있습니다. 한 심사대에서는 동시에 한 명만 심사를 할 수 있습니다. 가장 앞에 서 있는 사람은 비어 있는 심사대로 가서 심사를 받을 수 있습니다. 하지만 더 빨리 끝나는 심사대가 있으면 기다렸다가 그곳으로 가서 심사를 받을 수도 있습니다.
 *
 * 모든 사람이 심사를 받는데 걸리는 시간을 최소로 하고 싶습니다.
 *
 * 입국심사를 기다리는 사람 수 n, 각 심사관이 한 명을 심사하는데 걸리는 시간이 담긴 배열 times가 매개변수로 주어질 때, 모든 사람이 심사를 받는데 걸리는 시간의 최솟값을 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 입국심사를 기다리는 사람은 1명 이상 1,000,000,000명 이하입니다.
 * 각 심사관이 한 명을 심사하는데 걸리는 시간은 1분 이상 1,000,000,000분 이하입니다.
 * 심사관은 1명 이상 100,000명 이하입니다.
 * 입출력 예
 * n	times	return
 * 6	[7, 10]	28
 * 입출력 예 설명
 * 가장 첫 두 사람은 바로 심사를 받으러 갑니다.
 *
 * 7분이 되었을 때, 첫 번째 심사대가 비고 3번째 사람이 심사를 받습니다.
 *
 * 10분이 되었을 때, 두 번째 심사대가 비고 4번째 사람이 심사를 받습니다.
 *
 * 14분이 되었을 때, 첫 번째 심사대가 비고 5번째 사람이 심사를 받습니다.
 *
 * 20분이 되었을 때, 두 번째 심사대가 비지만 6번째 사람이 그곳에서 심사를 받지 않고 1분을 더 기다린 후에 첫 번째 심사대에서 심사를 받으면 28분에 모든 사람의 심사가 끝납니다.
 */
@RunWith(DataProviderRunner.class)
public class BinarySearch1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        6,
                        new int[] {7, 10},
                        28
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int n, int[] times, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(n, times), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (0.37ms, 78MB)
     * 테스트 2 〉	통과 (0.39ms, 72.5MB)
     * 테스트 3 〉	통과 (1.81ms, 79.5MB)
     * 테스트 4 〉	통과 (57.07ms, 86.3MB)
     * 테스트 5 〉	통과 (81.28ms, 87.5MB)
     * 테스트 6 〉	통과 (71.18ms, 101MB)
     * 테스트 7 〉	통과 (81.97ms, 86.5MB)
     * 테스트 8 〉	통과 (83.92ms, 88MB)
     * 테스트 9 〉	통과 (0.33ms, 73.8MB)
     */
    class Solution {
        public long solution(int n, int[] times) {
            long answer = Long.MAX_VALUE;
            Arrays.sort(times);

            long start = 0;
            long end = Long.MAX_VALUE;

            long sum;

            while(start <= end){
                long mid = (start + end) / 2;
                sum = 0;
                for (int time : times) {
                    sum += mid / time;
                    if (sum >= n) {
                        break;
                    }
                }

                if(n > sum){
                    start = mid + 1;
                } else {
                    end = mid - 1;
                    answer = Math.min(answer, mid);
                }
            }

            return answer;
        }
    }

    @Test
    @UseDataProvider("testCase")
    public void solution2(int n, int[] times, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(n, times), timer.stop());
    }

    class Solution2 {
        public long solution(int n, int[] times) {
            long answer = 0;

            int passenger = n;

            List<Integer> l = new ArrayList<>();
            for (int time : times) {
                l.add(time);
                passenger--;
            }

            while(passenger != 0) {

                int min = l.stream().mapToInt(x -> x).min().orElse(0);


                if(passenger >= l.size()) {
                    for (int i = 0; i < l.size(); i++) {
                        l.set(i, l.get(i) - min);
                        if (l.get(i) <= 0) {
                            l.set(i, times[i]);
                        }
                    }
                    answer += min;
                } else {
                    int min2 = Integer.MAX_VALUE;
                    int minIndex = 0;
                    for (int i = 0; i < l.size(); i++) {
                        if (min - l.get(i) + times[i] < min2) {
                            min2 = min - l.get(i) + times[i];
                            minIndex = i;
                        }
                    }
                    answer += times[minIndex] + (l.get(minIndex) - min);
                    l.set(minIndex, Integer.MAX_VALUE);
                }

                passenger--;
            }

            for (int i : l) {
                if (i != Integer.MAX_VALUE) {
                    answer += i;
                }
            }

            return answer;
        }
    }
}
