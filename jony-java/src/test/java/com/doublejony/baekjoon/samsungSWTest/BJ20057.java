package com.doublejony.baekjoon.samsungSWTest;

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
 * 마법사 상어
 * https://www.acmicpc.net/problem/20056
 */
@RunWith(DataProviderRunner.class)
public class BJ20057 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "5",
                                "0 0 0 0 0",
                                "0 0 0 0 0",
                                "0 10 0 0 0",
                                "0 0 0 0 0",
                                "0 0 0 0 0"
                        },
                        "10"
                },
                {
                        new String[]{
                                "5",
                                "0 0 0 0 0",
                                "0 0 0 0 0",
                                "0 100 0 0 0",
                                "0 0 0 0 0",
                                "0 0 0 0 0"
                        },
                        "85"
                },
                {
                        new String[]{
                                "7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 0 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7"
                        },
                        "139"
                },
                {
                        new String[]{
                                "5",
                                "100 200 300 400 200",
                                "300 243 432 334 555",
                                "999 111 0 999 333",
                                "888 777 222 333 900",
                                "100 200 300 400 500"
                        },
                        "7501"
                },
                {
                        new String[]{
                                "5",
                                "0 0 100 0 0",
                                "0 0 100 0 0",
                                "0 0 0 0 0",
                                "0 0 100 0 0",
                                "0 0 100 0 0"
                        },
                        "283"
                },
                {
                        new String[]{
                                "9",
                                "193 483 223 482 858 274 847 283 748",
                                "484 273 585 868 271 444 584 293 858",
                                "828 384 382 818 347 858 293 999 727",
                                "818 384 727 373 636 141 234 589 991",
                                "913 564 555 827 0 999 123 123 123",
                                "321 321 321 983 982 981 983 980 990",
                                "908 105 270 173 147 148 850 992 113",
                                "943 923 982 981 223 131 222 913 562",
                                "752 572 719 590 551 179 141 137 731"
                        },
                        "22961"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ20057.Main().solution(input),
                timer.stop());
    }

    public class Main {

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);

            int[][] spreadMap = new int[][]{
                    {0, 0, 2, 0, 0},
                    {0, 10, 7, 1, 0},
                    {5, 0, 0, 0, 0},
                    {0, 10, 7, 1, 0},
                    {0, 0, 2, 0, 0}
            };


            int[][] map = new int[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(input[i+1].split(" ")[j]);
                }
            }

            move(N / 2, N / 2);

        }

        private void move(int i, int j) {


        }
    }
}

