package com.doublejony.practice.bruteForce;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 모의고사
 * <p>
 * 수포자는 수학을 포기한 사람의 준말입니다. 수포자 삼인방은 모의고사에 수학 문제를 전부 찍으려 합니다. 수포자는 1번 문제부터 마지막 문제까지 다음과 같이 찍습니다.
 *
 * 1번 수포자가 찍는 방식: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
 * 2번 수포자가 찍는 방식: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
 * 3번 수포자가 찍는 방식: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...
 *
 * 1번 문제부터 마지막 문제까지의 정답이 순서대로 들은 배열 answers가 주어졌을 때, 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한 조건
 * 시험은 최대 10,000 문제로 구성되어있습니다.
 * 문제의 정답은 1, 2, 3, 4, 5중 하나입니다.
 * 가장 높은 점수를 받은 사람이 여럿일 경우, return하는 값을 오름차순 정렬해주세요.
 * 입출력 예
 * answers	return
 * [1,2,3,4,5]	[1]
 * [1,3,2,4,2]	[1,2,3]
 * 입출력 예 설명
 * 입출력 예 #1
 *
 * 수포자 1은 모든 문제를 맞혔습니다.
 * 수포자 2는 모든 문제를 틀렸습니다.
 * 수포자 3은 모든 문제를 틀렸습니다.
 * 따라서 가장 문제를 많이 맞힌 사람은 수포자 1입니다.
 *
 * 입출력 예 #2
 *
 * 모든 사람이 2문제씩을 맞췄습니다.
 */
@RunWith(DataProviderRunner.class)
public class BruteForce1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{1, 2, 3, 4, 5},
                        new int[]{1}
                },
                {
                        new int[]{1, 3, 2, 4, 2},
                        new int[]{1, 2, 3}
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (4.43ms, 69.9MB)
    테스트 2 〉	통과 (4.55ms, 69.6MB)
    테스트 3 〉	통과 (4.67ms, 69.6MB)
    테스트 4 〉	통과 (10.80ms, 71MB)
    테스트 5 〉	통과 (2.94ms, 70.9MB)
    테스트 6 〉	통과 (4.85ms, 71.9MB)
    테스트 7 〉	통과 (5.75ms, 59.1MB)
    테스트 8 〉	통과 (4.04ms, 59.3MB)
    테스트 9 〉	통과 (8.57ms, 76.5MB)
    테스트 10 〉	통과 (4.39ms, 70MB)
    테스트 11 〉	통과 (5.15ms, 59.3MB)
    테스트 12 〉	통과 (8.14ms, 74.6MB)
    테스트 13 〉	통과 (3.40ms, 69.8MB)
    테스트 14 〉	통과 (5.51ms, 71.6MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int[] answers, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(answers), timer.stop());
    }

    class Solution {
        public int[] solution(int[] answers) {
            int[][] pattern = new int[][]{
                    {1, 2, 3, 4, 5},
                    {2, 1, 2, 3, 2, 4, 2, 5},
                    {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
            };

            List<Integer> l = IntStream.range(0, pattern.length).mapToObj(i -> 0).collect(Collectors.toList());

            for (int i = 0; i < answers.length; i++) {
                for (int j = 0; j < pattern.length; j++) {
                    if (answers[i] == getGamblersNumber(pattern[j], i)) {
                        l.set(j, l.get(j) + 1);
                    }
                }
            }

            int max = 0;
            List<Integer> answerList = new ArrayList<>();

            for (int i = 0; i < l.size(); i++) {
                if (l.get(i) > max) {
                    max = l.get(i);
                    answerList.clear();
                    answerList.add(i + 1);
                } else if (l.get(i) == max) {
                    answerList.add(i + 1);
                }
            }

            return answerList.stream().mapToInt(integer -> integer).toArray();
        }

        private int getGamblersNumber(int[] pattern, int i) {
            return pattern[i % pattern.length];
        }
    }
}
