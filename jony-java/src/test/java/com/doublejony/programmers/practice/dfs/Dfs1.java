package com.doublejony.programmers.practice.dfs;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 타겟 넘버
 * n개의 음이 아닌 정수가 있습니다. 이 수를 적절히 더하거나 빼서 타겟 넘버를 만들려고 합니다. 예를 들어 [1, 1, 1, 1, 1]로 숫자 3을 만들려면 다음 다섯 방법을 쓸 수 있습니다.
 *
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 * 사용할 수 있는 숫자가 담긴 배열 numbers, 타겟 넘버 target이 매개변수로 주어질 때 숫자를 적절히 더하고 빼서 타겟 넘버를 만드는 방법의 수를 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 주어지는 숫자의 개수는 2개 이상 20개 이하입니다.
 * 각 숫자는 1 이상 50 이하인 자연수입니다.
 * 타겟 넘버는 1 이상 1000 이하인 자연수입니다.
 * 입출력 예
 * numbers	target	return
 * [1, 1, 1, 1, 1]	3	5
 */
@RunWith(DataProviderRunner.class)
public class Dfs1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[] {1, 1, 1, 1, 1},
                        3,
                        5
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] numbers, int target, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(numbers, target), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (8.00ms, 78.3MB)
     * 테스트 2 〉	통과 (5.38ms, 75.5MB)
     * 테스트 3 〉	통과 (0.25ms, 73.1MB)
     * 테스트 4 〉	통과 (0.32ms, 74MB)
     * 테스트 5 〉	통과 (0.53ms, 74.2MB)
     * 테스트 6 〉	통과 (0.24ms, 74.9MB)
     * 테스트 7 〉	통과 (0.17ms, 74.9MB)
     * 테스트 8 〉	통과 (0.45ms, 75.5MB)
     */
    class Solution {
        int answer;
        int[] numbers;
        int target;
        public int solution(int[] numbers, int target) {
            this.answer = 0;
            this.numbers = numbers;
            this.target = target;

            dfs(0, 0);

            return answer;
        }

        private void dfs(int index, int value){
            if(index == this.numbers.length) {
                if(value == this.target) {
                    this.answer++;
                }
                return;
            }

            dfs(index+1, value + this.numbers[index]);
            dfs(index+1, value - this.numbers[index]);
        }
    }
}
