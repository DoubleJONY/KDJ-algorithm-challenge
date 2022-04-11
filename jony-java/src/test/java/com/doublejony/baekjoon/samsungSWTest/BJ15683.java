package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 감시
 * https://www.acmicpc.net/problem/15683
 */
@RunWith(DataProviderRunner.class)
public class BJ15683 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "4 6",
                                "0 0 0 0 0 0",
                                "0 0 0 0 0 0",
                                "0 0 1 0 6 0",
                                "0 0 0 0 0 0"
                        },
                        "20"
                },
                {
                        new String[]{
                                "6 6",
                                "0 0 0 0 0 0",
                                "0 2 0 0 0 0",
                                "0 0 0 0 6 0",
                                "0 6 0 0 2 0",
                                "0 0 0 0 0 0",
                                "0 0 0 0 0 5"
                        },
                        "15"
                },
                {
                        new String[]{
                                "6 6",
                                "1 0 0 0 0 0",
                                "0 1 0 0 0 0",
                                "0 0 1 0 0 0",
                                "0 0 0 1 0 0",
                                "0 0 0 0 1 0",
                                "0 0 0 0 0 1"
                        },
                        "6"
                },
                {
                        new String[]{
                                "6 6",
                                "1 0 0 0 0 0",
                                "0 1 0 0 0 0",
                                "0 0 1 5 0 0",
                                "0 0 5 1 0 0",
                                "0 0 0 0 1 0",
                                "0 0 0 0 0 1"
                        },
                        "2"
                },
                {
                        new String[]{
                                "1 7",
                                "0 1 2 3 4 5 6"
                        },
                        "0"
                },
                {
                        new String[]{
                                "3 7",
                                "4 0 0 0 0 0 0",
                                "0 0 0 2 0 0 0",
                                "0 0 0 0 0 0 4"
                        },
                        "0"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ15683.Main().solution(input), timer.stop());
    }


    public class Main {

        public String solution(String[] input) {
            int N = Integer.parseInt(input[0].split(" ")[0]);
            int M = Integer.parseInt(input[0].split(" ")[1]);

            Map<Integer, int[]> cameras = new HashMap<>();

            int[][] map = new int[N][M];
            for (int i = 0; i < N; i++) {
                String[] row = input[i + 1].split(" ");
                for (int j = 0; j < M; j++) {
                    if(Integer.parseInt(row[j]) > 0 && Integer.parseInt(row[j]) < 6) {
                        cameras.put(Integer.parseInt(row[j]), new int[]{i, j});
                    }
                    map[i][j] = Integer.parseInt(row[j]);
                }
            }

            dfs(map, cameras, 0);

            return null;
        }


        private void dfs(int[][] map, Map<Integer, int[]> cameras, int depth) {
            if(cameras.containsKey(5)) {

            }
            if(cameras.containsKey(4)) {

            }
            if(cameras.containsKey(3)) {

            }
            if(cameras.containsKey(2)) {

            }
            if(cameras.containsKey(1)) {

            }

        }
    }
}

