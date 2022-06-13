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
 * 아기 상어
 * https://www.acmicpc.net/problem/16236
 */
@RunWith(DataProviderRunner.class)
public class BJ16236 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "3",
                                "0 0 1",
                                "0 0 0",
                                "0 9 0"
                        },
                        "3"
                },
                {
                        new String[] {
                                "4",
                                "4 3 2 1",
                                "0 0 0 0",
                                "0 0 9 0",
                                "1 2 3 4"
                        },
                        "14"
                },
                {
                        new String[] {
                                "6",
                                "5 4 3 2 3 4",
                                "4 3 2 3 4 5",
                                "3 2 9 5 6 6",
                                "2 1 2 3 4 5",
                                "3 2 1 6 5 4",
                                "6 6 6 6 6 6"
                        },
                        "60"
                },
                {
                        new String[] {
                                "6",
                                "6 0 6 0 6 1",
                                "0 0 0 0 0 2",
                                "2 3 4 5 6 6",
                                "0 0 0 0 0 2",
                                "0 2 0 0 0 0",
                                "3 9 3 0 0 1"
                        },
                        "48"
                },
                {
                        new String[] {
                                "6",
                                "1 1 1 1 1 1",
                                "2 2 6 2 2 3",
                                "2 2 5 2 2 3",
                                "2 2 2 4 6 3",
                                "0 0 0 0 0 6",
                                "0 0 0 0 0 9"
                        },
                        "39"
                },
                {
                        new String[] {
                                "3",
                                "0 0 0",
                                "0 0 0",
                                "0 9 0"
                        },
                        "0"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) throws CloneNotSupportedException {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ16236.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int[][] grid;
        int     N;

        int[] dx = { 0, -1, 1, 0 };
        int[] dy = { -1, 0, 0, 1 };

        Queue<StatusStep> queue;

        public String solution(String[] input) throws CloneNotSupportedException {

            int answer = 0;

            N = Integer.parseInt(input[0]);
            grid = new int[N][N];

            BabyShark babyShark = null;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                    if (grid[i][j] == 9) {
                        babyShark = new BabyShark(2, i, j);
                        grid[i][j] = 0;
                    }
                }
            }

            queue = new LinkedList<>();
            queue.add(new StatusStep(0, grid, babyShark));

            while (!queue.isEmpty()) {
                if (queue.size() == 1) {
                    answer = queue.peek().getStep();
                    if (queue.peek().isFinished()) {
                        return String.valueOf(answer);
                    }
                }
                bfs(queue.poll());
            }

            return String.valueOf(answer);
        }

        private void bfs(StatusStep currentStep) throws CloneNotSupportedException {

            int[][] cGrid;
            //deep copy currentStep.grid to cGrid
            cGrid = new int[N][N];
            for (int i = 0; i < N; i++) {
                System.arraycopy(currentStep.grid[i], 0, cGrid[i], 0, N);
            }

            BabyShark cBabyShark = currentStep.babyShark;

            for (int i = 0; i < 4; i++) {
                if (cBabyShark.isMovable(cGrid, cBabyShark.x + dx[i], cBabyShark.y + dy[i])) {
                    if (cBabyShark.isEdible(cGrid, cBabyShark.x + dx[i], cBabyShark.y + dy[i])) {
                        cBabyShark.position(cBabyShark.x + dx[i], cBabyShark.y + dy[i]);
                        cBabyShark.eat();
                        cGrid[cBabyShark.x][cBabyShark.y] = 0;

                        for (int j = 0; j < N; j++) {
                            for (int k = 0; k < N; k++) {
                                if (cGrid[j][k] == -1) {
                                    cGrid[j][k] = 0;
                                }
                            }
                        }

                        queue.clear();
                        queue.add(new StatusStep(currentStep.step++, cGrid, (BabyShark) cBabyShark.clone()));
                        return;
                    } else {
                        cBabyShark.position(cBabyShark.x + dx[i], cBabyShark.y + dy[i]);
                        cGrid[cBabyShark.x][cBabyShark.y] = -1;
                        queue.add(new StatusStep(currentStep.step++, cGrid, (BabyShark) cBabyShark.clone()));
                    }
                }
            }
        }

        class StatusStep {

            int step;

            int[][]   grid;
            BabyShark babyShark;

            public StatusStep(int step, int[][] grid, BabyShark babyShark) {

                this.step = step;
                this.grid = grid;
                this.babyShark = babyShark;
            }

            public boolean isFinished() {

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (grid[i][j] > 0 && grid[i][j] < 7) {
                            return false;
                        }
                    }
                }
                return true;
            }

            public int getStep() {

                return step;
            }
        }


        class BabyShark implements Cloneable {

            int size;
            int hunger;
            int x;
            int y;

            public BabyShark(int size, int x, int y) {

                this.size = size;
                this.x = x;
                this.y = y;
                this.hunger = 0;
            }

            public void position(int x, int y) {

                this.x = x;
                this.y = y;
            }

            public boolean isEdible(int[][] cGrid, int x, int y) {

                return cGrid[x][y] < size && cGrid[x][y] > 0;
            }

            public boolean isMovable(int[][] cGrid, int x, int y) {

                return x >= 0 && x < N && y >= 0 && y < N && cGrid[x][y] <= size && cGrid[x][y] != -1;
            }

            public void eat() {

                hunger++;
                if (isFull()) {
                    grow();
                }
            }

            private boolean isFull() {

                return size == hunger;
            }

            private void grow() {

                size++;
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {

                return super.clone();
            }
        }
    }
}

