package com.doublejony.practice.greedy;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 체육복
 * <p>
 * 점심시간에 도둑이 들어, 일부 학생이 체육복을 도난당했습니다. 다행히 여벌 체육복이 있는 학생이 이들에게 체육복을 빌려주려 합니다. 학생들의 번호는 체격 순으로 매겨져 있어, 바로 앞번호의 학생이나 바로 뒷번호의 학생에게만 체육복을 빌려줄 수 있습니다. 예를 들어, 4번 학생은 3번 학생이나 5번 학생에게만 체육복을 빌려줄 수 있습니다. 체육복이 없으면 수업을 들을 수 없기 때문에 체육복을 적절히 빌려 최대한 많은 학생이 체육수업을 들어야 합니다.
 *
 * 전체 학생의 수 n, 체육복을 도난당한 학생들의 번호가 담긴 배열 lost, 여벌의 체육복을 가져온 학생들의 번호가 담긴 배열 reserve가 매개변수로 주어질 때, 체육수업을 들을 수 있는 학생의 최댓값을 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 전체 학생의 수는 2명 이상 30명 이하입니다.
 * 체육복을 도난당한 학생의 수는 1명 이상 n명 이하이고 중복되는 번호는 없습니다.
 * 여벌의 체육복을 가져온 학생의 수는 1명 이상 n명 이하이고 중복되는 번호는 없습니다.
 * 여벌 체육복이 있는 학생만 다른 학생에게 체육복을 빌려줄 수 있습니다.
 * 여벌 체육복을 가져온 학생이 체육복을 도난당했을 수 있습니다. 이때 이 학생은 체육복을 하나만 도난당했다고 가정하며, 남은 체육복이 하나이기에 다른 학생에게는 체육복을 빌려줄 수 없습니다.
 * 입출력 예
 * n	lost	reserve	return
 * 5	[2, 4]	[1, 3, 5]	5
 * 5	[2, 4]  [3]	        4
 * 3	[3]	    [1]	        2
 * 입출력 예 설명
 * 예제 #1
 * 1번 학생이 2번 학생에게 체육복을 빌려주고, 3번 학생이나 5번 학생이 4번 학생에게 체육복을 빌려주면 학생 5명이 체육수업을 들을 수 있습니다.
 *
 * 예제 #2
 * 3번 학생이 2번 학생이나 4번 학생에게 체육복을 빌려주면 학생 4명이 체육수업을 들을 수 있습니다.
 */
@RunWith(DataProviderRunner.class)
public class Greedy1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        5,
                        new int[]{2, 4},
                        new int[]{1, 3, 5},
                        5
                },
                {
                        5,
                        new int[]{2, 4},
                        new int[]{3},
                        4
                },
                {
                        3,
                        new int[]{3},
                        new int[]{1},
                        2
                },
                {
                        6,
                        new int[]{2, 3, 5},
                        new int[]{3, 4},
                        5
                },
                {
                        6,
                        new int[]{3},
                        new int[]{3, 4},
                        6
                },
                {
                        10,
                        new int[]{5,4,3,2,1},
                        new int[]{3,1,2,5,4},
                        10
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (2.15ms, 75MB)
    테스트 2 〉	통과 (2.76ms, 79MB)
    테스트 3 〉	통과 (3.37ms, 69.8MB)
    테스트 4 〉	통과 (2.65ms, 79.2MB)
    테스트 5 〉	통과 (3.11ms, 76.9MB)
    테스트 6 〉	통과 (2.07ms, 74.2MB)
    테스트 7 〉	통과 (3.06ms, 69.6MB)
    테스트 8 〉	통과 (2.21ms, 76.6MB)
    테스트 9 〉	통과 (2.94ms, 84.8MB)
    테스트 10 〉	통과 (2.22ms, 77.2MB)
    테스트 11 〉	통과 (2.27ms, 77.9MB)
    테스트 12 〉	통과 (2.05ms, 85.7MB)
    테스트 13 〉	통과 (2.25ms, 77MB)
    테스트 14 〉	통과 (2.11ms, 77.7MB)
    테스트 15 〉	통과 (1.96ms, 74.3MB)
    테스트 16 〉	통과 (2.08ms, 78.2MB)
    테스트 17 〉	통과 (2.90ms, 77.6MB)
    테스트 18 〉	통과 (2.30ms, 71.8MB)
    테스트 19 〉	통과 (2.66ms, 80.2MB)
    테스트 20 〉	통과 (2.04ms, 76MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int n, int[] lost, int[] reserve, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(n, lost, reserve), timer.stop());
    }

    class Solution {
        public int solution(int n, int[] lost, int[] reserve) {
            List<Integer> lostList = Arrays.stream(lost).boxed().sorted().collect(Collectors.toList());
            List<Integer> reserveList = Arrays.stream(reserve).boxed().sorted().collect(Collectors.toList());

            for (int l : lost) {
                for (int r : reserve) {
                    if(r == l) {
                        reserveList.remove((Integer) r);
                        lostList.remove((Integer) l);
                        break;
                    }
                }
            }

            int a = n - lostList.size();

            for (int l : lostList) {
                for (int r : reserveList) {
                    if(Math.abs(r - l) < 2) {
                        reserveList.remove((Integer) r);
                        a++;
                        break;
                    }
                }
            }

            return a;
        }
    }
}
