package com.doublejony.practice.greedy;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.PriorityQueue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 단속카메라
 * <p>
 * 고속도로를 이동하는 모든 차량이 고속도로를 이용하면서 단속용 카메라를 한 번은 만나도록 카메라를 설치하려고 합니다.
 *
 * 고속도로를 이동하는 차량의 경로 routes가 매개변수로 주어질 때, 모든 차량이 한 번은 단속용 카메라를 만나도록 하려면 최소 몇 대의 카메라를 설치해야 하는지를 return 하도록 solution 함수를 완성하세요.
 *
 * 제한사항
 *
 * 차량의 대수는 1대 이상 10,000대 이하입니다.
 * routes에는 차량의 이동 경로가 포함되어 있으며 routes[i][0]에는 i번째 차량이 고속도로에 진입한 지점, routes[i][1]에는 i번째 차량이 고속도로에서 나간 지점이 적혀 있습니다.
 * 차량의 진입/진출 지점에 카메라가 설치되어 있어도 카메라를 만난것으로 간주합니다.
 * 차량의 진입 지점, 진출 지점은 -30,000 이상 30,000 이하입니다.
 * 입출력 예
 *
 * routes	return
 * [[-20,15], [-14,-5], [-18,-13], [-5,-3]]	2
 * 입출력 예 설명
 *
 * -5 지점에 카메라를 설치하면 두 번째, 네 번째 차량이 카메라를 만납니다.
 *
 * -15 지점에 카메라를 설치하면 첫 번째, 세 번째 차량이 카메라를 만납니다.
 */
@RunWith(DataProviderRunner.class)
public class Greedy6 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
//                {
//                        new int[][]{{-20,15}, {-14,-5}, {-18,-13}, {-5,-3}},
//                        2
//                },
//                {
//                        new int[][]{{0,2},{2,3},{3,4},{4,6}} ,
//                        2
//                },
                {
                        new int[][]{ {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 8}, {8, 9}, {9, 10}, {10, 11}, {11, 12}, {12, 13}, {13, 14}, {14, 15} } ,
                        8
                }
        };
        // @formatter:on
    }

    /*
    테스트 1 〉	통과 (0.79ms, 82MB)
    테스트 2 〉	통과 (0.97ms, 83.1MB)
    테스트 3 〉	실패 (0.75ms, 74.8MB)
    테스트 4 〉	통과 (0.64ms, 74.9MB)
    테스트 5 〉	통과 (0.68ms, 74.9MB)
    효율성  테스트
    테스트 1 〉	통과 (15.47ms, 52.9MB)
    테스트 2 〉	통과 (5.26ms, 52.3MB)
    테스트 3 〉	통과 (9.37ms, 56.3MB)
    테스트 4 〉	통과 (1.10ms, 52.7MB)
    테스트 5 〉	통과 (15.77ms, 58.2MB)
     */
    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(int[][] routes, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(routes), timer.stop());
    }

    class RouteMap implements Comparable<RouteMap> {
        int start;
        int end;

        public RouteMap(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(RouteMap o) {

            return this.start > o.start ? 1 : -1;
        }
    }

    class Solution {
        public int solution(int[][] routes) {
            int answer = 0;

            PriorityQueue<RouteMap> r = new PriorityQueue<>();

            for (int i = 0; i < routes.length; i++) {
                r.add(new RouteMap(routes[i][0], routes[i][1]));
            }

            int standard = r.peek().start;

            while (!r.isEmpty()) {
                RouteMap car = r.poll();
                if (standard >= car.start) {
                    standard = Math.min(car.end, standard);
                }
                else {
                    answer++;
                    standard = car.end;
                }
            }

            return answer;
        }
    }
}
