package com.doublejony.programmers.practice.dynamic;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * N으로 연결
 * <p>
 * 아래와 같이 5와 사칙연산만으로 12를 표현할 수 있습니다.
 *
 * 12 = 5 + 5 + (5 / 5) + (5 / 5)
 * 12 = 55 / 5 + 5 / 5
 * 12 = (55 + 5) / 5
 *
 * 5를 사용한 횟수는 각각 6,5,4 입니다. 그리고 이중 가장 작은 경우는 4입니다.
 * 이처럼 숫자 N과 number가 주어질 때, N과 사칙연산만 사용해서 표현 할 수 있는 방법 중 N 사용횟수의 최솟값을 return 하도록 solution 함수를 작성하세요.
 *
 * 제한사항
 * N은 1 이상 9 이하입니다.
 * number는 1 이상 32,000 이하입니다.
 * 수식에는 괄호와 사칙연산만 가능하며 나누기 연산에서 나머지는 무시합니다.
 * 최솟값이 8보다 크면 -1을 return 합니다.
 * 입출력 예
 * N	number	return
 * 5	12	4
 * 2	11	3
 * 입출력 예 설명
 * 예제 #1
 * 문제에 나온 예와 같습니다.
 *
 * 예제 #2
 * 11 = 22 / 2와 같이 2를 3번만 사용하여 표현할 수 있습니다.
 */
@RunWith(DataProviderRunner.class)
public class Dynamic1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        5,
                        12,
                        4
                },
                {
                        2,
                        11,
                        3
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useRecurrence(int N, int number, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(N, number), timer.stop());
    }

    /**
     * https://velog.io/@jwkim/DFS-n-expression
     *
     * 여태까지 연산한 횟수인 count와 이전까지의 연산 결괏값 prev을 파라미터로 넘기며 DFS를 수행한다.
     * 종료 조건은 2가지이다.
     * count가 8을 초과한 경우 answer는 -1이다.
     * count가 8 이하일 때 target 수가 만들어진 경우 answer를 최솟값으로 갱신한다.
     * 각 dfs()마다 한 자리씩 늘려가며 네 개의 사칙 연산을 추가로 하도록 재귀 호출한다.
     */
    class Solution {
        private int n;
        private int target;
        private int answer = Integer.MAX_VALUE;

        public int solution(int N, int number) {
            n = N;
            target = number;
            dfs(0, 0);
            return answer == Integer.MAX_VALUE ? -1 : answer;
        }

        private void dfs(int count, int prev) {
            if (count > 8) {
                answer = -1;
                return;
            }

            if (prev == target) {
                answer = Math.min(answer, count);
                return;
            }

            int tempN = n;
            for (int i = 0; i < 8 - count; i++) {
                int newCount = count + i + 1;
                dfs(newCount, prev + tempN);
                dfs(newCount, prev - tempN);
                dfs(newCount, prev / tempN);
                dfs(newCount, prev * tempN);

                tempN = tempN * 10 + n;
            }
        }
    }
}
