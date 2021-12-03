package com.doublejony.programmers.practice.graph;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 순위
 * n명의 권투선수가 권투 대회에 참여했고 각각 1번부터 n번까지 번호를 받았습니다. 권투 경기는 1대1 방식으로 진행이 되고, 만약 A 선수가 B 선수보다 실력이 좋다면 A 선수는 B 선수를 항상 이깁니다. 심판은 주어진 경기 결과를 가지고 선수들의 순위를 매기려 합니다. 하지만 몇몇 경기 결과를 분실하여 정확하게 순위를 매길 수 없습니다.
 *
 * 선수의 수 n, 경기 결과를 담은 2차원 배열 results가 매개변수로 주어질 때 정확하게 순위를 매길 수 있는 선수의 수를 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 선수의 수는 1명 이상 100명 이하입니다.
 * 경기 결과는 1개 이상 4,500개 이하입니다.
 * results 배열 각 행 [A, B]는 A 선수가 B 선수를 이겼다는 의미입니다.
 * 모든 경기 결과에는 모순이 없습니다.
 * 입출력 예
 * n	results	return
 * 5	[[4, 3], [4, 2], [3, 2], [1, 2], [2, 5]]	2
 */
@RunWith(DataProviderRunner.class)
public class Graph2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        5,
                        new int[][]{{4, 3}, {4, 2}, {3, 2}, {1, 2}, {2, 5}},
                        2
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int n, int[][] results, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(n, results), timer.stop());
    }

    class Solution {
        public int solution(int n, int[][] results) {
            int answer = 0;

            int[] recordCount = new int[n+1];
            LinkedList<Integer> records = new LinkedList<>();

            for (int[] result : results) {

                if(!records.contains(result[0])) {
                    records.add(result[0]);
                }


                recordCount[result[0]] += 1;
                recordCount[result[1]] += 1;
            }

            return answer;
        }
    }

    @Test
    @UseDataProvider("testCase")
    public void floydWarshall(int n, int[][] results, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(n, results), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (0.03ms, 76.7MB)
     * 테스트 2 〉	통과 (0.05ms, 71.8MB)
     * 테스트 3 〉	통과 (0.06ms, 77.4MB)
     * 테스트 4 〉	통과 (0.42ms, 68MB)
     * 테스트 5 〉	통과 (0.83ms, 70.8MB)
     * 테스트 6 〉	통과 (2.60ms, 77.4MB)
     * 테스트 7 〉	통과 (5.25ms, 76.7MB)
     * 테스트 8 〉	통과 (11.52ms, 79.5MB)
     * 테스트 9 〉	통과 (9.61ms, 70.6MB)
     * 테스트 10 〉	통과 (10.01ms, 78.8MB)
     */
    class Solution2 {
        public int solution(int n, int[][] results) {
            int answer = 0;
            int[][] map = new int[n][n];

            for (int[] result : results) {
                int win = result[0] - 1;
                int lose = result[1] - 1;
                map[win][lose] = 1;
                map[lose][win] = -1;
            }

            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i == j || map[i][j] != 0) {
                            continue;
                        }
                        if (map[i][k] == map[k][j]) {
                            map[i][j] = map[i][k];
                        }
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == 0 && i != j) {
                        answer--;
                        break;
                    }
                }
                answer++;
            }
            return answer;
        }
    }
}
