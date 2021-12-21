package com.doublejony.programmers.practice.dynamic;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 정수 삼각형
 * <p>
 * 위와 같은 삼각형의 꼭대기에서 바닥까지 이어지는 경로 중, 거쳐간 숫자의 합이 가장 큰 경우를 찾아보려고 합니다. 아래 칸으로 이동할 때는 대각선 방향으로 한 칸 오른쪽 또는 왼쪽으로만 이동 가능합니다. 예를 들어 3에서는 그 아래칸의 8 또는 1로만 이동이 가능합니다.
 *
 * 삼각형의 정보가 담긴 배열 triangle이 매개변수로 주어질 때, 거쳐간 숫자의 최댓값을 return 하도록 solution 함수를 완성하세요.
 *
 * 제한사항
 * 삼각형의 높이는 1 이상 500 이하입니다.
 * 삼각형을 이루고 있는 숫자는 0 이상 9,999 이하의 정수입니다.
 * 입출력 예
 * triangle	result
 * [[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]	30
 */
@RunWith(DataProviderRunner.class)
public class Dynamic2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[][] {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}},
                        30
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useRecursive_TopDown(int[][] triangle, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(triangle), timer.stop());
    }

    /**
     * 정확성  테스트
     * 테스트 1 〉	통과 (0.02ms, 88.6MB)
     * 테스트 2 〉	통과 (0.18ms, 72.1MB)
     * 테스트 3 〉	통과 (0.48ms, 69.1MB)
     * 테스트 4 〉	통과 (2459.45ms, 100MB)
     * 테스트 5 〉	실패 (시간 초과)
     * 테스트 6 〉	실패 (시간 초과)
     * 테스트 7 〉	실패 (시간 초과)
     * 테스트 8 〉	실패 (시간 초과)
     * 테스트 9 〉	통과 (0.03ms, 73.7MB)
     * 테스트 10 〉	통과 (1143.44ms, 75.8MB)
     * 효율성  테스트
     * 테스트 1 〉	실패 (시간 초과)
     * 테스트 2 〉	실패 (시간 초과)
     * 테스트 3 〉	실패 (시간 초과)
     * 테스트 4 〉	실패 (시간 초과)
     * 테스트 5 〉	실패 (시간 초과)
     * 테스트 6 〉	실패 (시간 초과)
     * 테스트 7 〉	실패 (시간 초과)
     * 테스트 8 〉	실패 (시간 초과)
     * 테스트 9 〉	실패 (시간 초과)
     * 테스트 10 〉	실패 (시간 초과)
     */
    class Solution {
        int[][] t;
        int max = 0;

        public int solution(int[][] triangle) {
            t = triangle;
            int answer = 0;

            tree(0, triangle[0][0], 0);
            return max;
        }

        private void tree(int depth, int sum, int directionMap) {
            if (depth == t.length - 1) {
                if (max < sum) {
                    max = sum;
                }
                return;
            }

            tree(depth + 1,  sum + t[depth+1][directionMap], directionMap);
            tree(depth + 1,  sum + t[depth+1][directionMap+1], directionMap+1);
        }
    }

    /**
     * 정확성  테스트
     * 테스트 1 〉	통과 (0.03ms, 83.7MB)
     * 테스트 2 〉	통과 (0.05ms, 71.3MB)
     * 테스트 3 〉	통과 (0.05ms, 74.8MB)
     * 테스트 4 〉	통과 (0.06ms, 80.1MB)
     * 테스트 5 〉	통과 (0.21ms, 76.6MB)
     * 테스트 6 〉	통과 (0.10ms, 75MB)
     * 테스트 7 〉	통과 (0.20ms, 78.1MB)
     * 테스트 8 〉	통과 (0.10ms, 83.8MB)
     * 테스트 9 〉	통과 (0.04ms, 73.3MB)
     * 테스트 10 〉	통과 (0.05ms, 69.6MB)
     * 효율성  테스트
     * 테스트 1 〉	통과 (7.41ms, 60.3MB)
     * 테스트 2 〉	통과 (5.42ms, 59.7MB)
     * 테스트 3 〉	통과 (6.46ms, 60.5MB)
     * 테스트 4 〉	통과 (5.94ms, 59.7MB)
     * 테스트 5 〉	통과 (7.69ms, 59.8MB)
     * 테스트 6 〉	통과 (8.04ms, 61.6MB)
     * 테스트 7 〉	통과 (7.68ms, 59.7MB)
     * 테스트 8 〉	통과 (6.23ms, 56.2MB)
     * 테스트 9 〉	통과 (6.81ms, 57MB)
     * 테스트 10 〉	통과 (5.98ms, 66.9MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useRecursive_BottomUp(int[][] triangle, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(triangle), timer.stop());
    }

    class Solution2 {

        public int solution(int[][] triangle) {
            for (int i = triangle.length - 1; i >= 0; i--) {
                for (int j = 0; j < triangle[i].length - 1; j++) {
                    triangle[i - 1][j] += Math.max(triangle[i][j], triangle[i][j + 1]);
                }
            }
            return triangle[0][0];
        }
    }
}
