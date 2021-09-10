package com.doublejony.devMatching2021;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 행렬 테두리 회전하기
 * <p>
 * https://programmers.co.kr/learn/courses/30/lessons/77485
 */
@RunWith(DataProviderRunner.class)
public class RotateMatrix {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][]{
                {
                        6,
                        6,
                        new int[][]{{2, 2, 5, 4}, {3, 3, 6, 6}, {5, 1, 6, 3}},
                        new int[]{8, 10, 25}
                },
                {
                        3,
                        3,
                        new int[][]{{1, 1, 2, 2}, {1, 2, 2, 3}, {2, 1, 3, 2}, {2, 2, 3, 3}},
                        new int[]{1, 1, 5, 3}
                },
                {
                        100,
                        97,
                        new int[][]{{1, 1, 100, 97}},
                        new int[]{1}
                },
                {
                        100,
                        97,
                        new int[][]{{1, 1, 100, 97},{2, 2, 99, 96}},
                        new int[]{1, 99}
                },
                {
                        10000,
                        10000,
                        new int[][]{{1, 1, 10000, 10000},{2, 2, 9999, 9999},{4, 3, 5, 10000}},
                        new int[]{1, 10002, 29999}
                },
                {
                        1,
                        1,
                        new int[][]{{1, 1, 1, 1}},
                        new int[]{1}
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	실패 (4.08ms, 59.4MB)
    테스트 2 〉	통과 (18.15ms, 70.7MB)
    테스트 3 〉	실패 (89.14ms, 82MB)
    테스트 4 〉	실패 (139.77ms, 100MB)
    테스트 5 〉	실패 (87.94ms, 99MB)
    테스트 6 〉	실패 (98.14ms, 108MB)
    테스트 7 〉	실패 (런타임 에러)
    테스트 8 〉	실패 (61.35ms, 83.5MB)
    테스트 9 〉	실패 (런타임 에러)
    테스트 10 〉	실패 (런타임 에러)
    테스트 11 〉	실패 (런타임 에러)
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void useForEach(int rows, int columns, int[][] queries, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(rows, columns, queries), timer.stop());
    }

    class Solution {
        public int[] solution(int rows, int columns, int[][] queries) {
            List<Integer> answer = new ArrayList<>();

            long[][] matrix = new long[rows][columns];

            long a = 1;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    matrix[i][j] = a++;
                }
            }

            for (int[] query : queries) {
                List<Long> cache = new ArrayList<>();
                cache.add(matrix[query[0]-1][query[1]-1]);
                for (int i = query[1]-1; i < query[3]-1; i++) {
                    cache.add(matrix[query[0]-1][i+1]);
                    matrix[query[0]-1][i+1] = cache.get(cache.size()-2);
                }
                for (int i = query[0]-1; i < query[2]-1; i++) {
                    cache.add(matrix[i+1][query[3]-1]);
                    matrix[i+1][query[3]-1] = cache.get(cache.size()-2);
                }
                for (int i = query[3]-1; i > query[1]-1; i--) {
                    cache.add(matrix[query[2]-1][i-1]);
                    matrix[query[2]-1][i-1] = cache.get(cache.size()-2);
                }
                for (int i = query[2]-1; i > query[0]-1; i--) {
                    cache.add(matrix[i-1][query[0]-1]);
                    matrix[i-1][query[0]-1] = cache.get(cache.size()-2);
                }
                answer.add((int) cache.stream().mapToLong(x -> x).min().orElse(0));
            }

            return answer.stream().mapToInt(integer -> integer).toArray();
        }
    }
}
