package com.doublejony.practice.sort;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * K 번째 수
 * <p>
 * 배열 array의 i번째 숫자부터 j번째 숫자까지 자르고 정렬했을 때, k번째에 있는 수를 구하려 합니다.
 * <p>
 * 예를 들어 array가 [1, 5, 2, 6, 3, 7, 4], i = 2, j = 5, k = 3이라면
 * <p>
 * array의 2번째부터 5번째까지 자르면 [5, 2, 6, 3]입니다.
 * 1에서 나온 배열을 정렬하면 [2, 3, 5, 6]입니다.
 * 2에서 나온 배열의 3번째 숫자는 5입니다.
 * 배열 array, [i, j, k]를 원소로 가진 2차원 배열 commands가 매개변수로 주어질 때, commands의 모든 원소에 대해 앞서 설명한 연산을 적용했을 때 나온 결과를 배열에 담아 return 하도록 solution 함수를 작성해주세요.
 * <p>
 * 제한사항
 * array의 길이는 1 이상 100 이하입니다.
 * array의 각 원소는 1 이상 100 이하입니다.
 * commands의 길이는 1 이상 50 이하입니다.
 * commands의 각 원소는 길이가 3입니다.
 * 입출력 예
 * array	                commands	                        return
 * [1, 5, 2, 6, 3, 7, 4]	[[2, 5, 3], [4, 4, 1], [1, 7, 3]]	[5, 6, 3]
 * 입출력 예 설명
 * [1, 5, 2, 6, 3, 7, 4]를 2번째부터 5번째까지 자른 후 정렬합니다. [2, 3, 5, 6]의 세 번째 숫자는 5입니다.
 * [1, 5, 2, 6, 3, 7, 4]를 4번째부터 4번째까지 자른 후 정렬합니다. [6]의 첫 번째 숫자는 6입니다.
 * [1, 5, 2, 6, 3, 7, 4]를 1번째부터 7번째까지 자릅니다. [1, 2, 3, 4, 5, 6, 7]의 세 번째 숫자는 3입니다.
 */
@RunWith(DataProviderRunner.class)
public class Sort1 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{1, 5, 2, 6, 3, 7, 4},
                        new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}},
                        new int[]{5, 6, 3}
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (2.19ms, 70.4MB)
    테스트 2 〉	통과 (2.44ms, 60.1MB)
    테스트 3 〉	통과 (2.61ms, 60.4MB)
    테스트 4 〉	통과 (2.17ms, 58.8MB)
    테스트 5 〉	통과 (2.48ms, 71.6MB)
    테스트 6 〉	통과 (2.12ms, 71MB)
    테스트 7 〉	통과 (5.46ms, 60.7MB)
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void useForEach(int[] array, int[][] commands, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(array, commands), timer.stop());
    }

    class Solution {
        public int[] solution(int[] array, int[][] commands) {

            List<Integer> answerList = new ArrayList<>();

            for (int[] command : commands) {
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                for (int j = command[0] - 1; j <= command[1] - 1; j++) {
                    pq.add(array[j]);
                }
                for (int j = 0; j < command[2] - 1; j++) {
                    pq.poll();
                }
                answerList.add(pq.peek());
            }

            return answerList.stream().mapToInt(integer -> integer).toArray();
        }
    }

    /*
    테스트 1 〉	통과 (5.95ms, 59.4MB)
    테스트 2 〉	통과 (6.62ms, 72.5MB)
    테스트 3 〉	통과 (7.55ms, 61.8MB)
    테스트 4 〉	통과 (4.91ms, 71.7MB)
    테스트 5 〉	통과 (6.55ms, 72.7MB)
    테스트 6 〉	통과 (4.80ms, 76.4MB)
    테스트 7 〉	통과 (5.95ms, 71.4MB)
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void useLambda(int[] array, int[][] commands, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(array, commands), timer.stop());
    }

    class Solution2 {
        public int[] solution(int[] array, int[][] commands) {

            List<Integer> answerList = new ArrayList<>();

            for (int[] command : commands) {
                PriorityQueue<Integer> pq = IntStream.rangeClosed(command[0] - 1, command[1] - 1).mapToObj(j -> array[j]).collect(Collectors.toCollection(PriorityQueue::new));
                IntStream.range(0, command[2] - 1).forEach(j -> pq.poll());
                answerList.add(pq.peek());
            }

            return answerList.stream().mapToInt(integer -> integer).toArray();
        }
    }
}
