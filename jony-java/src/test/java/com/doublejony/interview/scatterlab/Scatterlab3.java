package com.doublejony.interview.scatterlab;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 대학생이 된 루다는 교양 수학 과목을 들으면서 소수에 대해서 공부하게 되었습니다. 공부를 하던 루다는 N 이하의 소수를 차례대로 나열해 보았습니다. 아래는 N = 39 이하의 소수를 나열해 본 결과입니다.
 *
 *          2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37
 * 나열된 소수를 보고 있던 루다는 자연수 N 이하의 소수들을 활용하여 연속된 소수의 합이 M이 되는 경우의 수가 얼마나 있는지 궁금해 졌습니다.
 *
 * 가령 N = 20, M = 36이라면 20 이하의 소수들을 활용하여 36 = 5 + 7 + 11 + 13과 36 = 17 + 19로 표현 가능하기 때문에 두 가지의 경우의 수가 있습니다.
 *
 * N = 100, M = 83이라면 100 이하의 소수를 활용하여 83 = 11 + 13 + 17 + 19 + 23이나 83 = 23 + 29 + 31로 표현 할 수 있으며 83 자체로 소수이기 때문에 총 세 가지의 표현이 가능합니다.
 *
 * 여기서 중요한 점은 N = 12, M = 10 일 때 10 = 2 + 3 + 5로 표현할 수 있지만 10 = 3 + 7과 같이 연속된 소수로 이루어지지 않은 경우나 10 = 5 + 5과 같이 같은 숫자를 여러 번 사용하는 경우는 세지 않는다는 것입니다.
 *
 * 루다를 도와서 N 이하의 소수를 활용하여 연속된 소수의 합이 M이 되는 경우의 수를 찾아주세요.
 *
 * 입력
 *
 * 사용할 소수의 범위 2<= N <=3,000,000
 * 연속된 소수의 합으로 나타내고 싶은 2<=M<=1,000,000,000
 * 입출력 예
 *
 * N	M	Return
 * 20	36	2
 * 100	83	3
 * 12	10	1
 */
@RunWith(DataProviderRunner.class)
public class Scatterlab3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        20,
                        36,
                        2
                },
                {
                        100,
                        83,
                        3
                },
                {
                        12,
                        10,
                        1
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int N, int M, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(N, M),
                timer.stop());
    }

    class Solution {

        public int solution(int N, int M) {
            List<Integer> primeList = getPrimeList(N);

            return getSuccessionCount(primeList, M);
        }

        /**
         * 주어진 수의 소수 리스트 구하기
         * @param N
         * @return
         */
        private List<Integer> getPrimeList(int N) {

            //TODO:
            return IntStream.iterate(N, i -> i > 0, i -> i - 1).filter(this::isPrime).boxed()
                    .collect(Collectors.toList());
        }

        /**
         * 소수의 연속합 구하기
         * @param primeList
         * @param M
         * @return
         */
        private int getSuccessionCount(List<Integer> primeList, int M) {
            int start = 0;
            int end = 0;
            int sum = 0;
            int answer = 0;

            while (true) {
                if (sum >= M) {
                    sum -= primeList.get(start);
                    start++;
                } else if (end == primeList.size()) {
                    break;
                } else {
                    sum += primeList.get(end);
                    end++;
                }
                if (M == sum) {
                    answer++;
                }
            }

            return answer;
        }

        /**
         * 소수 판별하기
         * @param num
         * @return
         */
        private boolean isPrime(long num) {

            if(num < 2) {
                return false;
            }

            for (int i = 2; (long) i * i <= num; i++) {
                if(num % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }
}

