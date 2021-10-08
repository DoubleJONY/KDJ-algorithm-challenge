package com.doublejony.practice.bruteForce;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 카펫
 * <p>
 * Leo는 카펫을 사러 갔다가 아래 그림과 같이 중앙에는 노란색으로 칠해져 있고 테두리 1줄은 갈색으로 칠해져 있는 격자 모양 카펫을 봤습니다.
 *
 * Leo는 집으로 돌아와서 아까 본 카펫의 노란색과 갈색으로 색칠된 격자의 개수는 기억했지만, 전체 카펫의 크기는 기억하지 못했습니다.
 *
 * Leo가 본 카펫에서 갈색 격자의 수 brown, 노란색 격자의 수 yellow가 매개변수로 주어질 때 카펫의 가로, 세로 크기를 순서대로 배열에 담아 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 갈색 격자의 수 brown은 8 이상 5,000 이하인 자연수입니다.
 * 노란색 격자의 수 yellow는 1 이상 2,000,000 이하인 자연수입니다.
 * 카펫의 가로 길이는 세로 길이와 같거나, 세로 길이보다 깁니다.
 * 입출력 예
 * brown	yellow	return
 * 10	2	[4, 3]
 * 8	1	[3, 3]
 * 24	24	[8, 6]
 */
@RunWith(DataProviderRunner.class)
public class BruteForce3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        10,
                        2,
                        new int[]{4, 3}
                },
                {
                        8,
                        1,
                        new int[]{3, 3}
                },
                {
                        24,
                        24,
                        new int[]{8, 6}
                },
                {
                        18,
                        12,
                        new int[]{6, 5}
                },
                {
                        22,
                        8,
                        new int[]{10, 3}
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (0.02ms, 72.7MB)
    테스트 2 〉	통과 (0.01ms, 59.4MB)
    테스트 3 〉	통과 (0.09ms, 58.5MB)
    테스트 4 〉	통과 (0.03ms, 76.2MB)
    테스트 5 〉	통과 (0.03ms, 73.9MB)
    테스트 6 〉	통과 (0.07ms, 70MB)
    테스트 7 〉	통과 (0.07ms, 59.9MB)
    테스트 8 〉	통과 (0.10ms, 59.8MB)
    테스트 9 〉	통과 (0.12ms, 67.8MB)
    테스트 10 〉	통과 (0.10ms, 58.5MB)
    테스트 11 〉	통과 (0.02ms, 70.7MB)
    테스트 12 〉	통과 (0.03ms, 71.7MB)
    테스트 13 〉	통과 (0.01ms, 60.3MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useMath(int brown, int yellow, int[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(brown, yellow), timer.stop());
    }

    class Solution {
        public int[] solution(int brown, int yellow) {
            double b = 3;
            while(true){
                double a = (brown + yellow) / b;
                if(a % 1 == 0 && ((a * 2) + (2 * (b - 2))) == brown) {
                    return new int[]{(int) a, (int) b};
                }
                b++;
            }
        }
    }
}
