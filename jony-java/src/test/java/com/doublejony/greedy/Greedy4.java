package com.doublejony.greedy;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 구명보트
 * <p>
 * 무인도에 갇힌 사람들을 구명보트를 이용하여 구출하려고 합니다. 구명보트는 작아서 한 번에 최대 2명씩 밖에 탈 수 없고, 무게 제한도 있습니다.
 *
 * 예를 들어, 사람들의 몸무게가 [70kg, 50kg, 80kg, 50kg]이고 구명보트의 무게 제한이 100kg이라면 2번째 사람과 4번째 사람은 같이 탈 수 있지만 1번째 사람과 3번째 사람의 무게의 합은 150kg이므로 구명보트의 무게 제한을 초과하여 같이 탈 수 없습니다.
 *
 * 구명보트를 최대한 적게 사용하여 모든 사람을 구출하려고 합니다.
 *
 * 사람들의 몸무게를 담은 배열 people과 구명보트의 무게 제한 limit가 매개변수로 주어질 때, 모든 사람을 구출하기 위해 필요한 구명보트 개수의 최솟값을 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 무인도에 갇힌 사람은 1명 이상 50,000명 이하입니다.
 * 각 사람의 몸무게는 40kg 이상 240kg 이하입니다.
 * 구명보트의 무게 제한은 40kg 이상 240kg 이하입니다.
 * 구명보트의 무게 제한은 항상 사람들의 몸무게 중 최댓값보다 크게 주어지므로 사람들을 구출할 수 없는 경우는 없습니다.
 * 입출력 예
 * people	        limit	return
 * [70, 50, 80, 50]	100	    3
 * [70, 80, 50]	    100	    3
 */
@RunWith(DataProviderRunner.class)
public class Greedy4 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{70, 50, 80, 50},
                        100,
                        3
                },
                {
                        new int[]{70, 80, 50},
                        100,
                        3
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (4.46ms, 78.5MB)
    테스트 2 〉	통과 (3.55ms, 72MB)
    테스트 3 〉	통과 (5.86ms, 81.9MB)
    테스트 4 〉	통과 (3.61ms, 90.1MB)
    테스트 5 〉	통과 (2.21ms, 77MB)
    테스트 6 〉	통과 (1.49ms, 74.3MB)
    테스트 7 〉	통과 (2.00ms, 73.1MB)
    테스트 8 〉	통과 (0.63ms, 77.3MB)
    테스트 9 〉	통과 (0.75ms, 79.2MB)
    테스트 10 〉	통과 (3.88ms, 78.7MB)
    테스트 11 〉	통과 (3.43ms, 77.4MB)
    테스트 12 〉	통과 (3.07ms, 73.9MB)
    테스트 13 〉	통과 (3.50ms, 79.1MB)
    테스트 14 〉	통과 (3.88ms, 78.3MB)
    테스트 15 〉	통과 (0.84ms, 73.4MB)
    효율성  테스트
    테스트 1 〉	통과 (35.62ms, 56.7MB)
    테스트 2 〉	통과 (34.18ms, 56.3MB)
    테스트 3 〉	통과 (34.00ms, 55.6MB)
    테스트 4 〉	통과 (30.71ms, 54.9MB)
    테스트 5 〉	통과 (30.19ms, 55.4MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int[] people, int limit, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(people, limit), timer.stop());
    }

    class Solution {
        public int solution(int[] people, int limit) {
            int answer = 0;

            List<Integer> p = new ArrayList<>();
            for (int person : people) {
                Integer integer = person;
                p.add(integer);
            }
            p.sort(null);

            int leftIndex = 0;
            int rightIndex = p.size() - 1;

            while(leftIndex <= rightIndex) {
                if (p.get(leftIndex) + p.get(rightIndex) <= limit) {
                    leftIndex++;
                }
                rightIndex--;
                answer++;
            }

            return answer;
        }
    }

    /*
    테스트 1 〉	통과 (9.76ms, 80.3MB)
    테스트 2 〉	통과 (7.14ms, 72.9MB)
    테스트 3 〉	통과 (7.98ms, 75.4MB)
    테스트 4 〉	통과 (7.92ms, 84.6MB)
    테스트 5 〉	통과 (4.89ms, 88.7MB)
    테스트 6 〉	통과 (4.68ms, 76.7MB)
    테스트 7 〉	통과 (6.14ms, 77.8MB)
    테스트 8 〉	통과 (2.98ms, 74.5MB)
    테스트 9 〉	통과 (3.29ms, 75.1MB)
    테스트 10 〉	통과 (8.47ms, 76.8MB)
    테스트 11 〉	통과 (5.97ms, 76.3MB)
    테스트 12 〉	통과 (5.35ms, 75.9MB)
    테스트 13 〉	통과 (6.10ms, 75.4MB)
    테스트 14 〉	통과 (6.70ms, 84.6MB)
    테스트 15 〉	통과 (4.20ms, 80.2MB)
    효율성  테스트
    테스트 1 〉	실패 (시간 초과)
    테스트 2 〉	통과 (46.12ms, 56.5MB)
    테스트 3 〉	통과 (47.47ms, 56MB)
    테스트 4 〉	실패 (시간 초과)
    테스트 5 〉	통과 (41.90ms, 55.2MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useLambda(int[] people, int limit, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(people, limit), timer.stop());
    }

    class Solution2 {
        public int solution(int[] people, int limit) {
            int answer = 0;

            List<Integer> p = Arrays.stream(people).boxed().sorted().collect(Collectors.toList());

            int leftIndex = 0;
            int rightIndex = p.size() - 1;

            while(leftIndex <= rightIndex) {
                if (p.get(leftIndex) + p.get(rightIndex) <= limit) {
                    leftIndex++;
                }
                rightIndex--;
                answer++;
            }

            return answer;
        }
    }
}
