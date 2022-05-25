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
 * 인구 이동
 * https://www.acmicpc.net/problem/16234
 */
@RunWith(DataProviderRunner.class)
public class BJ16234 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "2 20 50",
                                "50 30",
                                "20 40"
                        },
                        "1"
                },
                {
                        new String[]{
                                "2 40 50",
                                "50 30",
                                "20 40"
                        },
                        "0"
                },
                {
                        new String[]{
                                "2 20 50",
                                "50 30",
                                "30 40"
                        },
                        "1"
                },
                {
                        new String[]{
                                "3 5 10",
                                "10 15 20",
                                "20 30 25",
                                "40 22 10"
                        },
                        "2"
                },
                {
                        new String[]{
                                "4 10 50",
                                "10 100 20 90",
                                "80 100 60 70",
                                "70 20 30 40",
                                "50 20 100 10"
                        },
                        "3"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ16234.Main().solution(input), timer.stop());
    }


    public class Main {

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split("")[0]);
            int L = Integer.parseInt(input[0].split("")[1]);
            int R = Integer.parseInt(input[0].split("")[2]);

            int[][] map = new int[N][N];
            boolean[][] borderMapHorizontal = new boolean[N][N];
            boolean[][] borderMapVertical = new boolean[N][N];

            resetBoarder(input, N, map, borderMapHorizontal, borderMapVertical);


            while (true) {
                boolean done = false;

                for (int i = 0; i < N-1; i++) {
                    for (int j = 0; j < N-1; j++) {
                        int h = Math.abs(map[i][j] - map[i][j+1]);
                        if (L < h && h < R) {
                            borderMapHorizontal[i][j] = true;
                            done = true;
                        }

                        int v = Math.abs(map[i][j] - map[i+1][j]);
                        if (L < v && v < R) {
                            borderMapVertical[i][j] = true;
                            done = true;
                        }
                    }
                }

                if (!done) {
                    break;
                } else {

                    for (int i = 0; i < N-1; i++) {
                        for (int j = 0; j < N-1; j++) {
                            if (borderMapHorizontal[i][j]) {

                            }
                            if (borderMapVertical[i][j]) {
                                map[i][j] = map[i+1][j];
                            }
                        }
                    }
                }

            }
        }

        private void resetBoarder(String[] input, int N, int[][] map, boolean[][] borderMapHorizontal, boolean[][] borderMapVertical) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                    borderMapHorizontal[i][j] = false;
                    borderMapVertical[i][j] = false;
                }
            }
        }
    }
}

