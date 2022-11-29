package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 주사위 굴리기 2
 * https://www.acmicpc.net/problem/23288
 */
@RunWith(DataProviderRunner.class)
public class BJ23288 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "4 5 1",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "4"
                },
                {
                        new String[] {
                                "4 5 2",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5",
                        },
                        "8"
                },
                {
                        new String[] {
                                "4 5 3",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "14"
                },
                {
                        new String[] {
                                "4 5 4",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "18"
                },
                {
                        new String[] {
                                "4 5 5",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "24"
                },
                {
                        new String[] {
                                "4 5 6",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "28"
                },
                {
                        new String[] {
                                "4 5 7",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "43"
                },
                {
                        new String[] {
                                "4 5 1000",
                                "4 1 2 3 3",
                                "6 1 1 3 3",
                                "5 6 1 3 2",
                                "5 5 6 5 5"
                        },
                        "3901"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ23288.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int N;
        int M;

        final int UP    = 0;
        final int RIGHT = 1;
        final int DOWN  = 2;
        final int LEFT  = 3;

        final int[] dw = { 0, 1, 0, -1 };
        final int[] dh = { -1, 0, 1, 0 };

        final int dArray[] = new int[] { UP, RIGHT, DOWN, LEFT };

        public String solution(String[] input) {

            N = Integer.parseInt(input[0].split(" ")[0]);
            M = Integer.parseInt(input[0].split(" ")[1]);
            int K = Integer.parseInt(input[0].split(" ")[2]);

            int[][] map = new int[N][M];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                }
            }

            Dice dice = new Dice();
            int answer = 0;

            for (int i = 0; i < K; i++) {
                dice.move();

                int[][] copyMap = new int[N][M];
                for (int j = 0; j < map.length; j++) {
                    System.arraycopy(map[j], 0, copyMap[j], 0, map[0].length);
                }
                answer += copyMap[dice.h][dice.w] * getScoreCount(copyMap, copyMap[dice.h][dice.w], dice.h, dice.w);

                if (dice.getBottomNum() > map[dice.h][dice.w]) {
                    dice.rotate(false);
                } else if (dice.getBottomNum() < map[dice.h][dice.w]) {
                    dice.rotate(true);
                }
            }

            return String.valueOf(answer);
        }

        private int getScoreCount(int[][] map, int num, int h, int w) {

            int count = 1;
            map[h][w] = 0;

            for (int i = 0; i < 4; i++) {
                try {
                    if (map[dh[i] + h][dw[i] + w] == num) {
                        count += getScoreCount(map, num, (dh[i] + h), (dw[i] + w));
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }

            return count;
        }

        private class Dice {

            int h;
            int w;
            int direction;

            LinkedList<Integer> vBelt;
            LinkedList<Integer> hBelt;

            public Dice() {

                this.h = 0;
                this.w = 0;
                this.direction = RIGHT;

                this.vBelt = new LinkedList<>();
                vBelt.add(2);
                vBelt.add(1);
                vBelt.add(5);
                vBelt.add(6);

                this.hBelt = new LinkedList<>();
                hBelt.add(4);
                hBelt.add(1);
                hBelt.add(3);
                hBelt.add(6);
            }

            public void rotate(boolean isCounterClockwise) {

                this.direction = isCounterClockwise ? (this.direction + 3) % 4 : (this.direction + 1) % 4;
            }

            public int getUpsideNum() {

                return this.vBelt.get(1);
            }

            public int getBottomNum() {

                return this.vBelt.get(3);
            }

            public void move() {

                movePoint();

                switch (this.direction) {
                    case UP:
                        moveVBelt(1, true);
                        break;
                    case RIGHT:
                        moveHBelt(1, false);
                        break;
                    case DOWN:
                        moveVBelt(1, false);
                        break;
                    case LEFT:
                        moveHBelt(1, true);
                        break;
                }
            }

            private void movePoint() {

                int th = this.h + dh[this.direction];
                int tw = this.w + dw[this.direction];

                if (th < 0 || th >= N) {
                    rotate(false);
                    rotate(false);
                    th = this.h + dh[this.direction];
                }
                if (tw < 0 || tw >= M) {
                    rotate(false);
                    rotate(false);
                    tw = this.w + dw[this.direction];
                }

                this.h = th;
                this.w = tw;
            }

            public void moveVBelt(int count, boolean reverse) {

                if (reverse) {
                    count = 4 - (count % 4);
                }

                for (int i = 0; i < count; i++) {
                    vBelt.addFirst(vBelt.removeLast());
                }

                hBelt.set(1, vBelt.get(1));
                hBelt.set(3, vBelt.get(3));
            }

            public void moveHBelt(int count, boolean reverse) {

                if (reverse) {
                    count = 4 - (count % 4);
                }

                for (int i = 0; i < count; i++) {
                    hBelt.addFirst(hBelt.removeLast());
                }

                vBelt.set(1, hBelt.get(1));
                vBelt.set(3, hBelt.get(3));
            }
        }
    }
}

