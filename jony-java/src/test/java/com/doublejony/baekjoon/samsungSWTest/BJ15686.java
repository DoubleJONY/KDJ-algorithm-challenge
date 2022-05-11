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
 * 치킨 배달
 * https://www.acmicpc.net/problem/15686
 */
@RunWith(DataProviderRunner.class)
public class BJ15686 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "5 3",
                                "0 0 1 0 0",
                                "0 0 2 0 1",
                                "0 1 2 0 0",
                                "0 0 1 0 0",
                                "0 0 0 0 2"
                        },
                        "5"
                },
                {
                        new String[]{
                                "5 2",
                                "0 2 0 1 0",
                                "1 0 1 0 0",
                                "0 0 0 0 0",
                                "2 0 0 1 1",
                                "2 2 0 1 2"
                        },
                        "10"
                },
                {
                        new String[]{
                                "5 1",
                                "1 2 0 0 0",
                                "1 2 0 0 0",
                                "1 2 0 0 0",
                                "1 2 0 0 0",
                                "1 2 0 0 0"
                        },
                        "11"
                },
                {
                        new String[]{
                                "5 1",
                                "1 2 0 2 1",
                                "1 2 0 2 1",
                                "1 2 0 2 1",
                                "1 2 0 2 1",
                                "1 2 0 2 1"
                        },
                        "32"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ15686.Main().solution(input), timer.stop());
    }


    public class Main {

        int N;
        int M;

        List<Point> residents;
        List<Point> chickens;

        boolean[] chickenToggle;
        int answer;

        public String solution(String[] input) {

            N = Integer.parseInt(input[0].split(" ")[0]);
            M = Integer.parseInt(input[0].split(" ")[1]);

            residents = new ArrayList<>();
            chickens = new ArrayList<>();

            answer = Integer.MAX_VALUE;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int a = Integer.parseInt(input[i + 1].split(" ")[j]);

                    if (a == 1) {
                        residents.add(new Point(i, j));
                    } else if (a == 2) {
                        chickens.add(new Point(i, j));
                    }
                }
            }

            chickenToggle = new boolean[chickens.size()];

            dfs(0, 0);

            return String.valueOf(answer);
        }

        public void dfs(int start, int depth) {
            if (depth == M) {
                int res = 0;

                for (Point resident : residents) {
                    int temp = Integer.MAX_VALUE;

                    for (int j = 0; j < chickens.size(); j++) {
                        if (chickenToggle[j]) {
                            temp = Math.min(temp, Math.abs(resident.x - chickens.get(j).x) + Math.abs(resident.y - chickens.get(j).y));
                        }
                    }
                    res += temp;
                }
                answer = Math.min(answer, res);
                return;
            }

            for (int i = start; i < chickens.size(); i++) {
                chickenToggle[i] = true;
                dfs(i + 1, depth + 1);
                chickenToggle[i] = false;
            }
        }

        class Point {
            int x;
            int y;

            Point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

    }
}

