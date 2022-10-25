package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 마법사 상어와 파이어스톰
 * https://www.acmicpc.net/problem/20058
 */
@RunWith(DataProviderRunner.class)
public class BJ20058 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "3 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1"
                        },
                        new String[] {
                                "284",
                                "64"
                        }
                },
                {
                        new String[] {
                                "3 2",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2"
                        },
                        new String[] {
                                "280",
                                "64"
                        }
                },
                {
                        new String[] {
                                "3 5",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 0 3 2"
                        },
                        new String[] {
                                "268",
                                "64"
                        }
                },
                {
                        new String[] {
                                "3 10",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 0 3 2 1 2 3 2 3"
                        },
                        new String[] {
                                "248",
                                "62"
                        }
                },
                {
                        new String[] {
                                "3 10",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 5 6 7 8",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 1 2 3 1 2 3 1"
                        },
                        new String[] {
                                "246",
                                "60"
                        }
                },
                {
                        new String[] {
                                "3 10",
                                "1 0 3 4 5 6 7 0",
                                "8 0 6 5 4 3 2 1",
                                "1 2 0 4 5 6 7 0",
                                "8 7 6 5 4 3 2 1",
                                "1 2 3 4 0 6 7 0",
                                "8 7 0 5 4 3 2 1",
                                "1 2 3 4 5 6 7 0",
                                "0 7 0 5 4 3 2 1",
                                "1 2 3 1 2 3 1 2 3 1"
                        },
                        new String[] {
                                "37",
                                "9"
                        }
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ20058.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int[] dh = new int[] { -1, 0, 1, 0 };
        int[] dw = new int[] { 0, 1, 0, -1 };

        public String[] solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);
            int Q = Integer.parseInt(input[0].split(" ")[1]);

            int nn = (int) Math.pow(2, N);
            int[][] iceMap = new int[nn][nn];

            for (int i = 0; i < nn; i++) {
                for (int j = 0; j < nn; j++) {
                    iceMap[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                }
            }

            int[] lList = new int[Q];

            for (int i = 0; i < Q; i++) {
                lList[i] = Integer.parseInt(input[nn + 1].split(" ")[i]);
            }

            String[] result = new String[2];

            for (int i = 0; i < Q; i++) {
                spin(iceMap, lList[i]);
                melt(iceMap);
            }

            result[0] = String.valueOf(sumIces(iceMap));
            result[1] = String.valueOf(getBiggestIceSize(iceMap));

            return new String[] { result[0], result[1] };
        }

        private void spin(int[][] iceMap, int L) {

            int gridSize = (int) Math.pow(2, L);

            for (int i = 0; i < iceMap.length; i += gridSize) {
                for (int j = 0; j < iceMap.length; j += gridSize) {

                    int[][] localMap = new int[gridSize][gridSize];
                    for (int k = 0; k < gridSize; k++) {
                        for (int l = 0; l < gridSize; l++) {
                            localMap[k][l] = iceMap[i + k][j + l];
                        }
                    }

                    int[][] rotatedMap = rotate(localMap);

                    for (int k = 0; k < gridSize; k++) {
                        for (int l = 0; l < gridSize; l++) {
                            iceMap[i + k][j + l] = rotatedMap[k][l];
                        }
                    }
                }
            }
        }

        private int[][] rotate(int[][] arr) {

            int n = arr.length;
            int m = arr[0].length;
            int[][] rotate = new int[m][n];

            for (int i = 0; i < rotate.length; i++) {
                for (int j = 0; j < rotate[i].length; j++) {
                    rotate[i][j] = arr[n - 1 - j][i];
                }
            }

            return rotate;
        }

        private void melt(int[][] iceMap) {

            int[][] newIceMap = new int[iceMap.length][iceMap.length];

            for (int i = 0; i < iceMap.length; i++) {
                for (int j = 0; j < iceMap.length; j++) {
                    newIceMap[i][j] = iceMap[i][j];
                }
            }

            for (int i = 0; i < iceMap.length; i++) {
                for (int j = 0; j < iceMap.length; j++) {
                    if (iceMap[i][j] <= 0) {
                        continue;
                    }
                    int iced = 0;
                    for (int k = 0; k < 4; k++) {
                        try {
                            if (iceMap[i + dh[k]][j + dw[k]] > 0) {
                                iced++;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {

                        }
                    }
                    if (iced < 3) {
                        newIceMap[i][j]--;
                    }
                }
            }

            for (int i = 0; i < iceMap.length; i++) {
                for (int j = 0; j < iceMap.length; j++) {
                    iceMap[i][j] = newIceMap[i][j];
                }
            }
        }

        private int sumIces(int[][] iceMap) {

            return Arrays.stream(iceMap).mapToInt(ints -> Arrays.stream(ints, 0, iceMap.length).sum()).sum();
        }

        private int getBiggestIceSize(int[][] iceMap) {

            Queue<int[]> queue = new LinkedList<>();
            boolean[][] visitedMap = new boolean[iceMap.length][iceMap.length];

            int max = 0;
            for (int i = 0; i < iceMap.length; i++) {
                for (int j = 0; j < iceMap.length; j++) {
                    if (iceMap[i][j] > 0 && !visitedMap[i][j]) {
                        queue.add(new int[] { i, j });
                        visitedMap[i][j] = true;
                        int cnt = 1;

                        while (!queue.isEmpty()) {
                            int[] t = queue.poll();
                            int th = t[0];
                            int tw = t[1];

                            for (int k = 0; k < 4; k++) {
                                int nh = th + dh[k];
                                int nw = tw + dw[k];
                                try {
                                    if (iceMap[nh][nw] > 0 && !visitedMap[nh][nw]) {
                                        visitedMap[nh][nw] = true;
                                        queue.add(new int[] { nh, nw });
                                        cnt++;
                                    }
                                } catch (IndexOutOfBoundsException ignored) {

                                }
                            }
                        }
                        max = Math.max(max, cnt);
                    }
                }
            }
            return max;
        }
    }
}

