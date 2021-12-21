package com.doublejony.programmers.practice.dfs;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashSet;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 네트워크
 * 네트워크란 컴퓨터 상호 간에 정보를 교환할 수 있도록 연결된 형태를 의미합니다. 예를 들어, 컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고, 컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로 연결되어 정보를 교환할 수 있습니다. 따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.
 *
 * 컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때, 네트워크의 개수를 return 하도록 solution 함수를 작성하시오.
 *
 * 제한사항
 * 컴퓨터의 개수 n은 1 이상 200 이하인 자연수입니다.
 * 각 컴퓨터는 0부터 n-1인 정수로 표현합니다.
 * i번 컴퓨터와 j번 컴퓨터가 연결되어 있으면 computers[i][j]를 1로 표현합니다.
 * computer[i][i]는 항상 1입니다.
 * 입출력 예
 * n	computers	return
 * 3	[[1, 1, 0], [1, 1, 0], [0, 0, 1]]	2
 * 3	[[1, 1, 0], [1, 1, 1], [0, 1, 1]]	1
 */
@RunWith(DataProviderRunner.class)
public class Dfs2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        3,
                        new int[][] {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}},
                        2
                },
                {
                        3,
                        new int[][] {{1, 1, 0}, {1, 1, 1}, {0, 1, 1}},
                        1
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int n, int[][] computers, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(n, computers), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (0.08ms, 72.2MB)
     * 테스트 2 〉	통과 (0.08ms, 82.8MB)
     * 테스트 3 〉	통과 (0.57ms, 76.7MB)
     * 테스트 4 〉	통과 (0.46ms, 76.8MB)
     * 테스트 5 〉	통과 (0.07ms, 77.2MB)
     * 테스트 6 〉	통과 (1.48ms, 74.2MB)
     * 테스트 7 〉	통과 (0.10ms, 75.3MB)
     * 테스트 8 〉	통과 (1.12ms, 78.5MB)
     * 테스트 9 〉	통과 (0.80ms, 80.6MB)
     * 테스트 10 〉	통과 (0.94ms, 78.4MB)
     * 테스트 11 〉	통과 (8.23ms, 79.3MB)
     * 테스트 12 〉	통과 (3.91ms, 76.6MB)
     * 테스트 13 〉	통과 (1.72ms, 71.8MB)
     */
    class Solution {
        int[][] computers;
        int[][] duplicate;

        public int solution(int n, int[][] computers){
            this.computers = computers;

            duplicate = new int[n][n];
            for (int[] i : duplicate) {
                Arrays.fill(i, 0); //0으로 초기화
            }

            for (int i = 0; i < this.computers.length; i++) {
                int[] sign = this.computers[i];
                findAllRoute(i, sign);

            }

            HashSet<String> hashSet = removeDuplcate();

            return hashSet.size();
        }

        private HashSet<String> removeDuplcate() {
            HashSet<String> hashSet = new HashSet<>();
            for (int[] a : this.duplicate) {
                StringBuilder sb = new StringBuilder();
                for (int b : a) {
                    sb.append(b);
                }
                hashSet.add(sb.toString());
            }
            return hashSet;
        }

        private void findAllRoute(int index, int[] sign){

            for (int i = 0; i < sign.length; i++) {
                int s = sign[i];
                if(s == 1) {
                    if(duplicate[index][i] != 1) {
                        duplicate[index][i] = 1;
                        findAllRoute(index, computers[i]);
                    }
                }
            }
        }
    }
}
