package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 미세먼지 안녕!
 * https://www.acmicpc.net/problem/17144
 */
@RunWith(DataProviderRunner.class)
public class BJ17144 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "7 8 4",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "178"
                },
                {
                        new String[]{
                                "7 8 5",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "172"
                },
                {
                        new String[]{
                                "7 8 20",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "71"
                },
                {
                        new String[]{
                                "7 8 30",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "52"
                },
                {
                        new String[]{
                                "7 8 50",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "46"
                },
                {
                        new String[]{
                                "7 8 1",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "188"
                },
                {
                        new String[]{
                                "7 8 2",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "188"
                },
                {
                        new String[]{
                                "7 8 3",
                                "0 0 0 0 0 0 0 9",
                                "0 0 0 0 3 0 0 8",
                                "-1 0 5 0 0 0 22 0",
                                "-1 8 0 0 0 0 0 0",
                                "0 0 0 0 0 10 43 0",
                                "0 0 5 0 15 0 0 0",
                                "0 0 40 0 0 0 20 0"
                        },
                        "186"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ17144.Main().solution(input), timer.stop());
    }


    public class Main {

        int R;
        int C;
        int[][] map;

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        public String solution(String[] input) {

            int answer = 0;

            R = Integer.parseInt(input[0].split(" ")[0]);
            C = Integer.parseInt(input[0].split(" ")[1]);
            int T = Integer.parseInt(input[0].split(" ")[2]);

            List<AirPurifier> airPurifiers = new ArrayList<>();

            map = new int[R][C];
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    map[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                    if (map[i][j] == -1) {
                        airPurifiers.add(new AirPurifier(i, j));
                    }
                }
            }

            for (int i = 0; i < T; i++) {
                spreadDust();
                circulateUp(airPurifiers.get(0));
                circulateDown(airPurifiers.get(1));
            }

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if(map[i][j] > 0) {
                        answer += map[i][j];
                    }
                }
            }

            return String.valueOf(answer);
        }

        private void spreadDust() {

            int[][] tempMap = new int[R][C];

            //deep copy
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    tempMap[i][j] = map[i][j];
                }
            }

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (map[i][j] > 0) {
                        int spreaded = 0;

                        for (int k = 0; k < 4; k++) {
                            int nx = i + dx[k];
                            int ny = j + dy[k];
                            if (nx < 0 || nx >= R || ny < 0 || ny >= C || map[nx][ny] == -1) {
                                continue;
                            }
                            spreaded++;
                            tempMap[nx][ny] += map[i][j] / 5;
                        }

                        tempMap[i][j] -= (map[i][j] / 5) * spreaded;
                    }
                }
            }

            map = tempMap;
        }

        private void circulateUp(AirPurifier airPurifier) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);
            for (int i = airPurifier.y + 1; i < C; i++) {
                queue.add(map[airPurifier.x][i]);
                map[airPurifier.x][i] = queue.poll();
            }
            for (int i = airPurifier.x - 1; i >= 0; i--) {
                queue.add(map[i][C-1]);
                map[i][C-1] = queue.poll();
            }
            for (int i = C-2; i >= 0; i--) {
                queue.add(map[0][i]);
                map[0][i] = queue.poll();
            }
            for (int i = 1; i < airPurifier.x; i++) {
                queue.add(map[i][0]);
                map[i][0] = queue.poll();
            }
        }

        private void circulateDown(AirPurifier airPurifier) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);
            for (int i = airPurifier.y + 1; i < C; i++) {
                queue.add(map[airPurifier.x][i]);
                map[airPurifier.x][i] = queue.poll();
            }
            for (int i = airPurifier.x + 1; i < R; i++) {
                queue.add(map[i][C-1]);
                map[i][C-1] = queue.poll();
            }
            for (int i = C-2; i >= 0; i--) {
                queue.add(map[R-1][i]);
                map[R-1][i] = queue.poll();
            }
            for (int i = R-2; i > airPurifier.x; i--) {
                queue.add(map[i][0]);
                map[i][0] = queue.poll();
            }
        }

        private class AirPurifier {

            private int x;
            private int y;

            public AirPurifier(int i, int j) {
                this.x = i;
                this.y = j;
            }
        }
    }
}

