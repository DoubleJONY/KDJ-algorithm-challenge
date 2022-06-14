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
                },
                {
                        new String[] {
                                "3",
                                "0 0 1",
                                "0 0 0",
                                "0 9 0"
                        },
                        "3"
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

        int[] dx = { -1, 0, 0, 1 };
        int[] dy = { 0, -1, 1, 0 };

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
            queue.add(new StatusStep(0, grid, new int[N][N], babyShark));

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

            for (int i = 0; i < 4; i++) {
                int[][] grid = new int[N][N];
                for (int a = 0; a < N; a++) {
                    System.arraycopy(currentStep.grid[a], 0, grid[a], 0, N);
                }
                int[][] cGrid = new int[N][N];
                for (int a = 0; a < N; a++) {
                    System.arraycopy(currentStep.cacheGrid[a], 0, cGrid[a], 0, N);
                }
                BabyShark cBabyShark = (BabyShark) currentStep.babyShark.clone();

                if (cBabyShark.isMovable(grid, cGrid,cBabyShark.x + dx[i], cBabyShark.y + dy[i])) {
                    if (cBabyShark.isEdible(grid, cBabyShark.x + dx[i], cBabyShark.y + dy[i])) {
                        cBabyShark.position(cBabyShark.x + dx[i], cBabyShark.y + dy[i]);
                        cBabyShark.eat();
                        grid[cBabyShark.x][cBabyShark.y] = 0;

                        queue.clear();
                        queue.add(new StatusStep(currentStep.step+1, grid, new int[N][N], (BabyShark) cBabyShark.clone()));
                        return;
                    } else {
                        cBabyShark.position(cBabyShark.x + dx[i], cBabyShark.y + dy[i]);
                        cGrid[cBabyShark.x][cBabyShark.y] = -1;
                        queue.add(new StatusStep(currentStep.step+1, grid, cGrid, cBabyShark));
                    }
                }
            }
        }

        class StatusStep {

            int step;

            int[][]   grid;
            int[][]   cacheGrid;
            BabyShark babyShark;

            public StatusStep(int step, int[][] grid, int[][] cacheGrid, BabyShark babyShark) {

                this.step = step;
                this.grid = grid;
                this.cacheGrid = cacheGrid;
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

            public boolean isEdible(int[][] grid, int x, int y) {

                return grid[x][y] < size && grid[x][y] > 0;
            }

            public boolean isMovable(int[][] grid, int[][] cGrid, int x, int y) {

                return x >= 0 && x < N && y >= 0 && y < N && grid[x][y] <= size && cGrid[x][y] != -1;
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
                hunger = 0;
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {

                return super.clone();
            }
        }
    }
}

