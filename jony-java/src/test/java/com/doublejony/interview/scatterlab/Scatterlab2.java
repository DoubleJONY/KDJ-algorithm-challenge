package com.doublejony.interview.scatterlab;

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
 * 은퇴 후 치킨집 사장님이 된 종윤이는 고된 연습 끝에 1분에 2마리의 치킨을 튀길 수 있게 되었다. 이 소식을 들은 성구는 치킨집의 번창을 위해 X 마리의 치킨을 주문하였다.
 *
 * 하지만 종윤이는 나이가 들어 혼자 모든 치킨을 튀길 수 없었고, 알바들을 고용할지 고민한다. 알바들은 한 명당 1분에 F 마리의 치킨을 튀길 수 있지만, 수당으로 치킨 C 마리를 미리 줘야 일을 하겠다고 한다. 성격이 급한 성구에게 혼나지 않으려면 가능한 빨리 치킨을 튀겨서 보내줘야 한다.
 *
 * 종윤이가 알바들을 적절히 고용해서 성구가 주문한 X 마리의 치킨을 튀기는데 걸리는 최소 시간을 반환(return)하도록 solution 함수를 작성하시오. (반올림하여 소수점 6번째 자리까지의 값을 반환)
 *
 * 입력
 *
 * 알바 당 인건비(치킨): 1 <= C <= 10000
 * 알바 당 치킨 생산량: 1 <= F <= 100
 * 목표 치킨량: 1 <= X <= 100000
 * 입출력 예
 *
 * C	    F	    X	        Return
 * 30.0	    1.0	    2.0	        1.0
 * 30.0	    2.0	    100.0	    39.166667
 * 30.5	    3.14159	1999.1999	63.968001
 * 500.0	4.0	    2000.0	    526.190476
 */
@RunWith(DataProviderRunner.class)
public class Scatterlab2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        30.0,
                        1.0,
                        2.0,
                        1.0
                },
                {
                        30.0,
                        2.0,
                        100.0,
                        39.166667
                },
                {
                        30.5,
                        3.14159,
                        1999.1999,
                        63.968001
                },
                {
                        500.0,
                        4.0,
                        2000.0,
                        526.190476
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void useFullScanLoop(double C, double F, double X, double expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(C, F, X), timer.stop());
    }

    class Solution {
        public double solution(double C, double F, double X) {
            double answer = 9999999.0;
            int partEmpCount = 0;

            while (true) {
                double chicken = 0 - (C * partEmpCount);
                double speed = (2 + (F * partEmpCount));

                double time = 0.0;
                while (X > chicken) {
                    time += 1;
                    chicken += speed;
                }

                if(answer <= time) {
                    break;
                } else {
                    answer = time;
                }

                partEmpCount++;
            }

            return answer;
        }
    }
}
