package com.doublejony.programmers.practice.sort;

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
 * H-Index
 * <p>
 * H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다. 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다. 위키백과에 따르면, H-Index는 다음과 같이 구합니다.
 * <p>
 * 어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최댓값이 이 과학자의 H-Index입니다.
 * <p>
 * 어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때, 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.
 * <p>
 * 제한사항
 * 과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
 * 논문별 인용 횟수는 0회 이상 10,000회 이하입니다.
 * 입출력 예
 * citations	    return
 * [3, 0, 6, 1, 5]	3
 * 입출력 예 설명
 * 이 과학자가 발표한 논문의 수는 5편이고, 그중 3편의 논문은 3회 이상 인용되었습니다. 그리고 나머지 2편의 논문은 3회 이하 인용되었기 때문에 이 과학자의 H-Index는 3입니다.
 */
@RunWith(DataProviderRunner.class)
public class Sort3 {

    @DataProvider
    public static Object[][] dataProviderAdd() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{3, 0, 6, 1, 5},
                        3
                },
                {
                        new int[]{6, 5, 4, 1, 0},
                        3
                },
                {
                        new int[]{4, 4, 4, 2, 2, 2, 2},
                        1
                },
                {
                        new int[]{4, 4, 4, 4, 4, 4, 2},
                        3
                },
                {
                        new int[]{5, 5, 5, 5},
                        4
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (17.75ms, 72.6MB)
    테스트 2 〉	통과 (32.80ms, 59.7MB)
    테스트 3 〉	통과 (24.71ms, 59.1MB)
    테스트 4 〉	통과 (22.88ms, 72.8MB)
    테스트 5 〉	통과 (15.63ms, 77.1MB)
    테스트 6 〉	통과 (18.10ms, 75.3MB)
    테스트 7 〉	통과 (18.87ms, 75MB)
    테스트 8 〉	통과 (2.40ms, 69.8MB)
    테스트 9 〉	통과 (8.70ms, 72.2MB)
    테스트 10 〉	통과 (15.81ms, 70MB)
    테스트 11 〉	통과 (19.96ms, 73.6MB)
    테스트 12 〉	통과 (13.49ms, 59.3MB)
    테스트 13 〉	통과 (27.53ms, 75.5MB)
    테스트 14 〉	통과 (43.92ms, 74.5MB)
    테스트 15 〉	통과 (18.21ms, 73.7MB)
    테스트 16 〉	통과 (1.66ms, 72.2MB)
     */
    @Test
    @UseDataProvider("dataProviderAdd")
    public void useList(int[] citations, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(citations), timer.stop());
    }

    class Solution {
        public int solution(int[] citations) {
            int answer = 0;

            List<Integer> l = Arrays.stream(citations).boxed().collect(Collectors.toList());

            for (int i = 1; i <= l.size(); i++) {
                int above = 0;
                int below = 0;
                for (int j : l) {
                    if(i <= j) {
                        above++;
                    }
                    if(i >= j) {
                        below++;
                    }
                }
                if(above >= i && below <= i) {
                    answer = i;
                }
            }

            return answer;
        }
    }
}
