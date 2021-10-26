package com.doublejony.practice.dynamic;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 도둑질
 * <p>
 * 도둑이 어느 마을을 털 계획을 하고 있습니다.
 * 각 집들은 서로 인접한 집들과 방범장치가 연결되어 있기 때문에 인접한 두 집을 털면 경보가 울립니다.
 * 각 집에 있는 돈이 담긴 배열 money가 주어질 때, 도둑이 훔칠 수 있는 돈의 최댓값을 return 하도록 solution 함수를 작성하세요.
 * 제한사항
 * 이 마을에 있는 집은 3개 이상 1,000,000개 이하입니다.
 * money 배열의 각 원소는 0 이상 1,000 이하인 정수입니다.
 * 입출력 예
 * money	return
 * [1, 2, 3, 1]	4
 */
@RunWith(DataProviderRunner.class)
public class Dynamic4 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {1, 2, 3, 1},
                        4
                },
                {
                        new int[] {1, 1, 3, 4, 3},
                        6
                },
                {
                        new int[] {1, 2, 4, 5, 7, 6, 1},
                        13
                },
                {
                        new int[] {1, 5, 3, 5, 4},
                        10
                },
                {
                        new int[] {1, 5, 1, 1, 5, 1},
                        10
                },
                {
                        new int[] {1, 5, 1, 1, 5, 1, 1, 5, 1},
                        15
                },
                {
                        new int[] {5, 1, 1, 1, 2, 5},
                        8
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useForeach(int[] money, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(money), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (0.11ms, 75.7MB)
     * 테스트 2 〉	통과 (0.18ms, 76.9MB)
     * 테스트 3 〉	통과 (0.14ms, 71.5MB)
     * 테스트 4 〉	통과 (0.03ms, 71.8MB)
     * 테스트 5 〉	통과 (0.09ms, 76.1MB)
     * 테스트 6 〉	통과 (0.09ms, 77.6MB)
     * 테스트 7 〉	통과 (0.15ms, 87.9MB)
     * 테스트 8 〉	통과 (0.07ms, 75.8MB)
     * 테스트 9 〉	통과 (0.23ms, 87.9MB)
     * 테스트 10 〉	통과 (0.09ms, 72.5MB)
     * 효율성  테스트
     * 테스트 1 〉	통과 (32.32ms, 103MB)
     * 테스트 2 〉	통과 (21.92ms, 102MB)
     * 테스트 3 〉	통과 (22.61ms, 102MB)
     * 테스트 4 〉	통과 (22.02ms, 102MB)
     * 테스트 5 〉	통과 (20.91ms, 99.2MB)
     * 테스트 6 〉	통과 (22.08ms, 102MB)
     * 테스트 7 〉	통과 (19.34ms, 76.1MB)
     * 테스트 8 〉	통과 (19.73ms, 77MB)
     * 테스트 9 〉	통과 (21.75ms, 93.6MB)
     * 테스트 10 〉	통과 (22.04ms, 101MB)
     */
    class Solution {
        public int solution(int[] money) {
            int answer = 0;

            switch (money.length) {
                case 1:
                    return money[0];

                case 2:
                    return Math.max(money[0], money[1]);

                case 3:
                    for (int i = 0; i < 3; i++) {
                        if (answer < money[i]) {
                            answer = money[i];
                        }
                    }
                    return answer;

                default:
                    int[] oddArray = new int[money.length];
                    int[] evenArray = new int[money.length];

                    oddArray[0] = money[0];
                    oddArray[1] = Math.max(money[0], money[1]);

                    evenArray[0] = 0;
                    evenArray[1] = money[1];

                    for (int i = 2; i < money.length; i++) {
                        if (i != money.length - 1) {
                            oddArray[i] = Math.max(oddArray[i - 1], oddArray[i - 2] + money[i]);
                            answer = Math.max(answer, oddArray[i]);
                        }

                        evenArray[i] = Math.max(evenArray[i - 1], evenArray[i - 2] + money[i]);
                        answer = Math.max(answer, evenArray[i]);
                    }

                    return answer;
            }
        }
    }
}
