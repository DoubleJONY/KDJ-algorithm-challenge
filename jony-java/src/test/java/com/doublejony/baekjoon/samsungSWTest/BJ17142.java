package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 연구소 3
 * https://www.acmicpc.net/problem/17142
 */
@RunWith(DataProviderRunner.class)
public class BJ17142 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "7 3",
                                "2 0 0 0 1 1 0",
                                "0 0 1 0 1 2 0",
                                "0 1 1 0 1 0 0",
                                "0 1 0 0 0 0 0",
                                "0 0 0 2 0 1 1",
                                "0 1 0 0 0 0 0",
                                "2 1 0 0 0 0 2"
                        },
                        "4"
                },
                {
                        new String[]{
                                "7 3",
                                "2 0 2 0 1 1 0",
                                "0 0 1 0 1 2 0",
                                "0 1 1 2 1 0 0",
                                "2 1 0 0 0 0 2",
                                "0 0 0 2 0 1 1",
                                "0 1 0 0 0 0 0",
                                "2 1 0 0 2 0 2"
                        },
                        "4"
                },
                {
                        new String[]{
                                "7 4",
                                "2 0 2 0 1 1 0",
                                "0 0 1 0 1 2 0",
                                "0 1 1 2 1 0 0",
                                "2 1 0 0 0 0 2",
                                "0 0 0 2 0 1 1",
                                "0 1 0 0 0 0 0",
                                "2 1 0 0 2 0 2"
                        },
                        "4"
                },
                {
                        new String[]{
                                "7 5",
                                "2 0 2 0 1 1 0",
                                "0 0 1 0 1 2 0",
                                "0 1 1 2 1 0 0",
                                "2 1 0 0 0 0 2",
                                "0 0 0 2 0 1 1",
                                "0 1 0 0 0 0 0",
                                "2 1 0 0 2 0 2"
                        },
                        "3"
                },
                {
                        new String[]{
                                "7 3",
                                "2 0 2 0 1 1 0",
                                "0 0 1 0 1 0 0",
                                "0 1 1 1 1 0 0",
                                "2 1 0 0 0 0 2",
                                "1 0 0 0 0 1 1",
                                "0 1 0 0 0 0 0",
                                "2 1 0 0 2 0 2"
                        },
                        "7"
                },
                {
                        new String[]{
                                "7 2",
                                "2 0 2 0 1 1 0",
                                "0 0 1 0 1 0 0",
                                "0 1 1 1 1 0 0",
                                "2 1 0 0 0 0 2",
                                "1 0 0 0 0 1 1",
                                "0 1 0 0 0 0 0",
                                "2 1 0 0 2 0 2"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "5 1",
                                "2 2 2 1 1",
                                "2 1 1 1 1",
                                "2 1 1 1 1",
                                "2 1 1 1 1",
                                "2 2 2 1 1"
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
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ17142.Main().solution(input), timer.stop());
    }


    public class Main {

        Queue<VirusStatus> virusStatusQueue = new LinkedList<>();

        int BLANK = -8;
        int WALL = -1;
        int VIRUS = -2;

        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        public String solution(String[] input) {

            int n = Integer.parseInt(input[0].split(" ")[0]);
            int m = Integer.parseInt(input[0].split(" ")[1]);

            int[][] map = new int[n][n];
            List<int[]> virusList = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    map[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                    if (map[i][j] == 0) {
                        map[i][j] = BLANK;
                    }
                    if (map[i][j] == 1) {
                        map[i][j] = WALL;
                    }
                    if (map[i][j] == 2) {
                        virusList.add(new int[]{i, j});
                        map[i][j] = VIRUS;
                    }
                }
            }

            initVirusQueue(n, m, map, virusList, null, 0);

            while (!virusStatusQueue.isEmpty()) {
                VirusStatus virusStatus = virusStatusQueue.poll();
                if (virusStatus.isFinish()) {
                    return String.valueOf(virusStatus.step);
                }

                spreadVirus(virusStatus);
            }

            return String.valueOf(-1);
        }

        private void initVirusQueue(int n, int m, int[][] map, List<int[]> virusList, List<int[]> pickedVirusList, int mm) {
            if (m == mm) {
                int[][] newMap = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        newMap[i][j] = map[i][j];
                        if (newMap[i][j] == VIRUS) {
                            newMap[i][j] = BLANK;
                        }
                    }
                }

                for (int[] v : pickedVirusList) {
                    newMap[v[0]][v[1]] = 0;
                }

                virusStatusQueue.add(new VirusStatus(n, newMap, 0));
            } else {
                for (int i = 0; i < virusList.size(); i++) {
                    int[] p = virusList.get(i);
                    List<int[]> newList = new ArrayList<>();
                    for (int j = 0; j < virusList.size(); j++) {
                        if (i != j) {
                            newList.add(virusList.get(j));
                        }
                    }

                    if (pickedVirusList == null) {
                        pickedVirusList = new ArrayList<>();
                    }
                    pickedVirusList.add(p);

                    initVirusQueue(n, m, map, newList, pickedVirusList, mm+1);
                }
            }
        }

        private void spreadVirus(VirusStatus virusStatus) {

            for (int i = 0; i < virusStatus.n; i++) {
                for (int j = 0; j < virusStatus.n; j++) {
                    if (virusStatus.map[i][j] == virusStatus.step) {
                        for (int k = 0; k < 4; k++) {
                            if (i + dx[k] >= 0
                                    && i + dx[k] < virusStatus.n
                                    && j + dy[k] >= 0
                                    && j + dy[k] < virusStatus.n
                                    && virusStatus.map[i + dx[k]][j + dy[k]] == BLANK) {
                                virusStatus.map[i+dx[k]][j+dy[k]] = virusStatus.step + 1;
                            }
                        }
                    }
                }
            }

            virusStatus.step += 1;

            virusStatusQueue.add(virusStatus);
        }

        private class VirusStatus {

            int n;

            int[][] map;

            int step;

            public VirusStatus(int n, int[][] map, int step) {
                this.n = n;

                this.map = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        this.map[i][j] = map[i][j];
                    }
                }

                this.step = step;
            }

            public boolean isFinish() {

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (map[i][j] == BLANK) {
                            return false;
                        }
                    }
                }
                return true;
            }


        }
    }
}