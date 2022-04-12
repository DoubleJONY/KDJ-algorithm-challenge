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

        int N;
        int M;

        int answer;

        public String solution(String[] input) {
            N = Integer.parseInt(input[0].split(" ")[0]);
            M = Integer.parseInt(input[0].split(" ")[1]);

            Queue<Camera> cameras = new LinkedList<>();

            answer = 9999999;

            int[][] map = new int[N][M];
            for (int i = 0; i < N; i++) {
                String[] row = input[i + 1].split(" ");
                for (int j = 0; j < M; j++) {
                    if (Integer.parseInt(row[j]) > 0 && Integer.parseInt(row[j]) < 6) {
                        cameras.add(new Camera(Integer.parseInt(row[j]), i, j));
                    }
                    map[i][j] = Integer.parseInt(row[j]);
                }
            }

            dfs(map, cameras);

            return answer == 9999999 ? "0" : String.valueOf(answer);
        }

        class Camera {
            int type;
            int n;
            int m;

            public Camera(int type, int n, int m) {
                this.type = type;
                this.n = n;
                this.m = m;
            }
        }

        private void dfs(int[][] map, Queue<Camera> cameras) {
            if (cameras.isEmpty()) {
                int sum = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        if (map[i][j] == 0) {
                            sum++;
                        }
                    }
                }

                answer = Math.min(answer, sum);
                return;
            }

            int[][] newMap = copyMap(map);

            Camera camera = cameras.poll();

            switch (camera.type) {
                case 5:
                    fillUp(newMap, camera.n, camera.m);
                    fillDown(newMap, camera.n, camera.m);
                    fillLeft(newMap, camera.n, camera.m);
                    fillRight(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));
                    return;
                case 4:
                    // case 1
                    fillLeft(newMap, camera.n, camera.m);
                    fillUp(newMap, camera.n, camera.m);
                    fillRight(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 2
                    newMap = copyMap(map);
                    fillUp(newMap, camera.n, camera.m);
                    fillRight(newMap, camera.n, camera.m);
                    fillDown(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 3
                    newMap = copyMap(map);
                    fillRight(newMap, camera.n, camera.m);
                    fillDown(newMap, camera.n, camera.m);
                    fillLeft(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 4
                    newMap = copyMap(map);
                    fillDown(newMap, camera.n, camera.m);
                    fillLeft(newMap, camera.n, camera.m);
                    fillUp(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    return;
                case 3:
                    // case 1
                    fillUp(newMap, camera.n, camera.m);
                    fillRight(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 2
                    newMap = copyMap(map);
                    fillRight(newMap, camera.n, camera.m);
                    fillDown(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 3
                    newMap = copyMap(map);
                    fillDown(newMap, camera.n, camera.m);
                    fillLeft(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 4
                    newMap = copyMap(map);
                    fillLeft(newMap, camera.n, camera.m);
                    fillUp(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    return;
                case 2:
                    // case 1
                    fillLeft(newMap, camera.n, camera.m);
                    fillRight(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 2
                    newMap = copyMap(map);
                    fillUp(newMap, camera.n, camera.m);
                    fillDown(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    return;
                case 1:
                    // case 1
                    fillRight(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 2
                    newMap = copyMap(map);
                    fillDown(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 3
                    newMap = copyMap(map);
                    fillLeft(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));

                    // case 4
                    newMap = copyMap(map);
                    fillUp(newMap, camera.n, camera.m);
                    dfs(newMap, cloneQueue(cameras));
            }
        }

        private Queue<Camera> cloneQueue(Queue<Camera> cameras) {
            return new LinkedList<>(cameras);
        }

        private int[][] copyMap(int[][] map) {
            int[][] newMap = new int[N][M];
            for (int i = 0; i < N; i++) {
                System.arraycopy(map[i], 0, newMap[i], 0, M);
            }
            return newMap;
        }

        private void fillUp(int[][] map, int n, int m) {

            for (int i = n - 1; i >= 0; i--) {
                if (map[i][m] == 6) {
                    break;
                } else if (map[i][m] == 0) {
                    map[i][m] = 7;
                }
            }
        }

        private void fillDown(int[][] map, int n, int m) {

            for (int i = n + 1; i < N; i++) {
                if (map[i][m] == 6) {
                    break;
                } else if (map[i][m] == 0) {
                    map[i][m] = 7;
                }
            }
        }

        private void fillLeft(int[][] map, int n, int m) {

            for (int i = m - 1; i >= 0; i--) {
                if (map[n][i] == 6) {
                    break;
                } else if (map[n][i] == 0) {
                    map[n][i] = 7;
                }
            }
        }

        private void fillRight(int[][] map, int n, int m) {

            for (int i = m + 1; i < M; i++) {
                if (map[n][i] == 6) {
                    break;
                } else if (map[n][i] == 0) {
                    map[n][i] = 7;
                }
            }
        }
    }
}

