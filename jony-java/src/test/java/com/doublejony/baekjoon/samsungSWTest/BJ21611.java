package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

        int answer1 = 0;
        int answer2 = 0;
        int answer3 = 0;

        int dx[] = { 0, -1, 1, 0, 0 }; // 1~4 상하좌우
        int dy[] = { 0, 0, 0, -1, 1 };

        int nextDir[] = { 0, 3, 4, 2, 1 };

        int[][] indexMap;
        List<Integer> stringList;

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
            int center = N / 2;

            int[][] blizzardPoints = new int[4][center];
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= center; j++) {
                    blizzardPoints[i-1][j-1] = indexMap[center + (dx[i] * j)][center + (dy[i] * j)];
                }
            }


            //loop M {
            removeZero();

            for (int i = 0; i < M; i++) {
                blizzard(spellQueue.poll(), blizzardPoints);
                popMarble();
            }

            //use blizzard

            //pull list

            //is available pop {

            //pop 4 combo and calculate point 1, 2, 3

            //pull list

            //}

            //}

            //return 1*(no1)+2*(no2)+3*(no3)

            return String.valueOf(answer1 + answer2 * 2 + answer3 * 3);
        }

        private void popMarble() {

            boolean isPoped = false;

            

            removeZero();

            if (isPoped) {
                popMarble();
            }
        }

        private void blizzard(Spell spell, int[][] blizzardPoints) {

            for (int i = 0; i < spell.s; i++) {
                stringList.set(blizzardPoints[spell.d-1][i] , 0);
            }
            removeZero();
        }

        private void removeZero() {
            stringList.removeIf(x -> x.equals(0));
        }

        private void prepare(int N, int[][] map) {
            int x = N / 2, y = N / 2;
            int nx = 0, ny = 0;
            int curDir = 3;
            int d = 1; // 이동해야하는 양
            int num = 0;

            indexMap = new int[N][N];
            indexMap[x][y] = -1;

            stringList = new LinkedList<>();

            while (true) {
                for (int k = 0; k < 2; k++) {
                    for (int i = 0; i < d; i++) {
                        if (x == 0 && y == 0)
                            return;
                        nx = x + dx[curDir];
                        ny = y + dy[curDir];
                        indexMap[nx][ny] = num;
                        stringList.add(map[nx][ny]);
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

