package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 마법사 상어와 블리자드
 * https://www.acmicpc.net/problem/21611
 */
@RunWith(DataProviderRunner.class)
public class BJ21611 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "7 1",
                                "0 0 0 0 0 0 0",
                                "3 2 1 3 2 3 0",
                                "2 1 2 1 2 1 0",
                                "2 1 1 0 2 1 1",
                                "3 3 2 3 2 1 2",
                                "3 3 3 1 3 3 2",
                                "2 3 2 2 3 2 3",
                                "2 2"
                        },
                        "28"
                },
                {
                        new String[] {
                                "7 4",
                                "0 0 0 2 3 2 3",
                                "1 2 3 1 2 3 1",
                                "2 3 1 2 3 1 2",
                                "1 2 3 0 2 3 1",
                                "2 3 1 2 3 1 2",
                                "3 1 2 3 1 2 3",
                                "1 2 3 1 2 3 1",
                                "1 3",
                                "2 2",
                                "3 1",
                                "4 3"
                        },
                        "0"
                },
                {
                        new String[] {
                                "7 4",
                                "1 1 1 2 2 2 3",
                                "1 2 2 1 2 2 3",
                                "1 3 3 2 3 1 2",
                                "1 2 2 0 3 2 2",
                                "3 1 2 2 3 2 2",
                                "3 1 2 1 1 2 1",
                                "3 1 2 2 2 1 1",
                                "1 3",
                                "2 2",
                                "3 1",
                                "4 3"
                        },
                        "39"
                },
                {
                        new String[] {
                                "7 7",
                                "1 1 1 2 2 2 3",
                                "1 2 2 1 2 2 3",
                                "1 3 3 2 3 1 2",
                                "1 2 2 0 3 2 2",
                                "3 1 2 2 3 2 2",
                                "3 1 2 1 1 2 1",
                                "3 1 2 2 2 1 1",
                                "1 3",
                                "2 2",
                                "3 1",
                                "4 3",
                                "1 3",
                                "1 1",
                                "1 3"
                        },
                        "62"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ21611.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int[] sum = new int[3];

        int[] dx = { 0, -1, 1, 0, 0 };
        int[] dy = { 0, 0, 0, -1, 1 };

        int[] nextDir = { 0, 3, 4, 2, 1 };

        int[][]       indexMap;
        List<Integer> marbleString;
        int           maxSize;

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);
            int M = Integer.parseInt(input[0].split(" ")[1]);

            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                }
            }

            Queue<Spell> spellQueue = new LinkedList<>();
            for (int i = 0; i < M; i++) {
                spellQueue.add(new Spell(
                        Integer.parseInt(input[N + 1 + i].split(" ")[0]),
                        Integer.parseInt(input[N + 1 + i].split(" ")[1])
                ));
            }

            prepare(N, map);
            int[][] blizzardPoints = getBlizzardPoints(N / 2);

            while (!spellQueue.isEmpty()) {
                blizzard(spellQueue.poll(), blizzardPoints);
                popMarble();
                if (!spellQueue.isEmpty()) {
                    groupMarble();
                }
            }

            return String.valueOf(IntStream.range(0, 3).map(i -> sum[i] * (i + 1)).sum());
        }

        private int[][] getBlizzardPoints(int d) {

            int[][] blizzardPoints = new int[4][d];
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= d; j++) {
                    blizzardPoints[i - 1][j - 1] = indexMap[d + (dx[i] * j)][d + (dy[i] * j)];
                }
            }
            return blizzardPoints;
        }

        private void popMarble() {

            boolean isPoped = false;

            int stack = 0;
            int stackNum = 0;
            for (int i = 0; i < marbleString.size(); i++) {

                if (marbleString.get(i).equals(stackNum)) {
                    stack++;
                } else {
                    if (stack >= 4) {
                        isPoped = true;
                        sum[stackNum - 1] += stack;
                        for (int j = 0; j < stack; j++) {
                            marbleString.set(i - (j + 1), 0);
                        }
                    }
                    stackNum = marbleString.get(i);
                    stack = 1;
                }
            }

            if (isPoped) {
                removeZero();
                popMarble();
            }
        }

        private void groupMarble() {

            List<Integer> groupString = new LinkedList<>();

            int stack = 1;
            int stackNum = marbleString.get(0);
            for (int i = 1; i < marbleString.size(); i++) {
                if (marbleString.get(i).equals(stackNum)) {
                    stack++;
                } else {
                    if (groupString.size() <= maxSize) {
                        groupString.add(stack);
                        groupString.add(stackNum);
                    } else {
                        break;
                    }
                    stackNum = marbleString.get(i);
                    stack = 1;
                }
            }

            marbleString = groupString;
        }

        private void blizzard(Spell spell, int[][] blizzardPoints) {

            for (int i = 0; i < spell.s; i++) {
                try {
                    marbleString.set(blizzardPoints[spell.d - 1][i], 0);
                } catch (IndexOutOfBoundsException ignored) {

                }
            }
            removeZero();
        }

        private void removeZero() {

            marbleString.removeIf(x -> x.equals(0));
        }

        private void prepare(int N, int[][] map) {

            int x = N / 2, y = N / 2;
            int nx;
            int ny;
            int curDir = 3;
            int d = 1;
            int num = 0;

            indexMap = new int[N][N];
            indexMap[x][y] = -1;

            marbleString = new LinkedList<>();

            while (true) {
                for (int k = 0; k < 2; k++) {
                    for (int i = 0; i < d; i++) {
                        if (x == 0 && y == 0) {
                            maxSize = (N * N) - 1;
                            removeZero();
                            return;
                        }
                        nx = x + dx[curDir];
                        ny = y + dy[curDir];
                        indexMap[nx][ny] = num;
                        marbleString.add(map[nx][ny]);
                        num++;

                        x = nx;
                        y = ny;
                    }
                    curDir = nextDir[curDir];
                }
                d++;
            }
        }

        private class Spell {

            int d;
            int s;

            public Spell(int d, int s) {

                this.d = d;
                this.s = s;
            }
        }
    }
}

