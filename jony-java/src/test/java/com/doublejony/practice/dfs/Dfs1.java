package com.doublejony.interview.trenbe;

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
 * 임의의 자연수 N이 주어질 때 각 자리의 숫자를 정렬하려고 합니다. 예를 들어 주어진 숫자 N = 2613인 경우 각 자릿수를 오름차순으로 정렬하면 1236이 되며, 내림차순으로 정렬하면 6321이 되고, 두 숫자의 합은 7557이 됩니다. 임의의 자연수 N이 매개변수로 주어질 때 N의 각 자릿수를 오름차순으로 정렬한 수와 내림차순으로 정렬한 수의 합을 return 하도록 solution 함수를 완성해 주세요.
 *
 * 제한사항
 * N은 1이상 10억 이하의 자연수입니다.
 * 입출력 예
 * N	result
 * 2613	7557
 * 33285	108690
 * 입출력 예 설명
 * 입출력 예 #1
 * 2613의 각 자릿수를 오름차순 정렬하면 1236이며, 내림차순 정렬하면 6321입니다. 1236 + 6321 = 7557입니다.
 *
 * 입출력 예 #2
 * 33285의 각 자릿수를 오름차순 정렬하면 23358이며, 내림차순 정렬하면 85332입니다. 23358 + 85332 = 108690입니다.
 */
@RunWith(DataProviderRunner.class)
public class Trenbe1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        2613,
                        7557
                },
                {
                        33285,
                        108690
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int N, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(N), timer.stop());
    }

    /**
     * 두개의 다른 정렬을 PriorityQueue를 사용하여 추출한다
     * 유지보수성을 고려하여 정렬 기능은 하나로 만들고
     * Comparator 를 인자로 받아 정렬 방향을 결정하도록 고려한다
     * 대신 정렬 수행을 두 번 하므로 시간 복잡도는 2N이 되겠지만
     * 두 개의 정렬을 한꺼번에 처리하면 추후에 관리가 어렵기 때문에 분리하는 형태로 구현
     */

    /**
     * 테스트 1 〉	통과 (0.74ms, 72.5MB)
     * 테스트 2 〉	통과 (0.46ms, 73.8MB)
     * 테스트 3 〉	통과 (0.63ms, 75.3MB)
     * 테스트 4 〉	통과 (0.52ms, 79MB)
     * 테스트 5 〉	통과 (0.47ms, 75.2MB)
     * 테스트 6 〉	통과 (0.70ms, 71.9MB)
     * 테스트 7 〉	통과 (0.48ms, 75.2MB)
     * 테스트 8 〉	통과 (0.76ms, 77.2MB)
     * 테스트 9 〉	통과 (0.52ms, 83.9MB)
     * 테스트 10 〉	통과 (0.60ms, 71.4MB)
     * 테스트 11 〉	통과 (0.75ms, 76.4MB)
     * 테스트 12 〉	통과 (0.64ms, 76.1MB)
     * 테스트 13 〉	통과 (0.81ms, 74.5MB)
     * 테스트 14 〉	통과 (0.80ms, 74.6MB)
     * 테스트 15 〉	통과 (0.54ms, 75.5MB)
     * 테스트 16 〉	통과 (0.73ms, 75.9MB)
     * 테스트 17 〉	통과 (0.56ms, 72.7MB)
     * 테스트 18 〉	통과 (0.57ms, 76MB)
     * 테스트 19 〉	통과 (0.75ms, 72.6MB)
     * 테스트 20 〉	통과 (0.60ms, 76MB)
     * 테스트 21 〉	통과 (0.53ms, 73.1MB)
     * 테스트 22 〉	통과 (0.55ms, 86.7MB)
     * 테스트 23 〉	통과 (0.62ms, 74.4MB)
     * 테스트 24 〉	통과 (0.60ms, 78.1MB)
     * 테스트 25 〉	통과 (0.56ms, 75.6MB)
     * 테스트 26 〉	통과 (0.81ms, 87.1MB)
     * 테스트 27 〉	통과 (0.54ms, 76.4MB)
     * 테스트 28 〉	통과 (0.54ms, 79MB)
     * 테스트 29 〉	통과 (0.75ms, 77.4MB)
     * 테스트 30 〉	통과 (0.55ms, 68.5MB)
     */
    class Solution {
        //비즈니스 로직 서비스
        public int solution(int N) {

            int regularNumber = getOrderNumber(N, Comparator.naturalOrder());
            int reverseNumber = getOrderNumber(N, Comparator.reverseOrder());

            return regularNumber + reverseNumber;
        }

        //정렬 기능
        private int getOrderNumber(int N , Comparator<Integer> comparator) {
            PriorityQueue<Integer> orderQueue = new PriorityQueue<>(comparator);

            String s = Integer.toString(N);

            for (int i = 0; i < s.length(); i++) {
                orderQueue.add(Integer.valueOf(s.substring(i, i+1)));
            }

            StringBuilder orderString = new StringBuilder();

            while(!orderQueue.isEmpty()) {
                orderString.append(orderQueue.poll());
            }

            return Integer.parseInt(orderString.toString());
        }
    }
}
