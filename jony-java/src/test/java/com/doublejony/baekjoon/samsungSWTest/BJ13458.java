package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 시험 감독
 * 문제
 * 총 N개의 시험장이 있고, 각각의 시험장마다 응시자들이 있다. i번 시험장에 있는 응시자의 수는 Ai명이다.
 *
 * 감독관은 총감독관과 부감독관으로 두 종류가 있다. 총감독관은 한 시험장에서 감시할 수 있는 응시자의 수가 B명이고, 부감독관은 한 시험장에서 감시할 수 있는 응시자의 수가 C명이다.
 *
 * 각각의 시험장에 총감독관은 오직 1명만 있어야 하고, 부감독관은 여러 명 있어도 된다.
 *
 * 각 시험장마다 응시생들을 모두 감시해야 한다. 이때, 필요한 감독관 수의 최솟값을 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 시험장의 개수 N(1 ≤ N ≤ 1,000,000)이 주어진다.
 *
 * 둘째 줄에는 각 시험장에 있는 응시자의 수 Ai (1 ≤ Ai ≤ 1,000,000)가 주어진다.
 *
 * 셋째 줄에는 B와 C가 주어진다. (1 ≤ B, C ≤ 1,000,000)
 *
 * 출력
 * 각 시험장마다 응시생을 모두 감독하기 위해 필요한 감독관의 최소 수를 출력한다.
 */
@RunWith(DataProviderRunner.class)
public class BJ13458 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "1",
                                "1",
                                "1 1"
                        },
                        "1"
                },
                {
                        new String[]{
                                "3",
                                "3 4 5",
                                "2 2"
                        },
                        "7"
                },
                {
                        new String[]{
                                "5",
                                "1000000 1000000 1000000 1000000 1000000",
                                "5 7"
                        },
                        "714290"
                },
                {
                        new String[]{
                                "5",
                                "10 9 10 9 10",
                                "7 20"
                        },
                        "10"
                },
                {
                        new String[]{
                                "5",
                                "10 9 10 9 10",
                                "7 2"
                        },
                        "13"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ13458.Main().solution(input), timer.stop());
    }

    public class Main {

        public String solution(String[] input) {

            String[] admin = input[2].split(" ");

            long major = Long.parseLong(admin[0]);
            long minor = Long.parseLong(admin[1]);

            long answer = 0;

            String[] amount = input[1].split(" ");

            for (String s : amount) {
                long a = Long.parseLong(s) - major;
                answer += a > 0 ? a / minor + ((a % minor == 0) ? 0 : 1) + 1 : 1;
            }

            return Long.toString(answer);
        }
    }
}
