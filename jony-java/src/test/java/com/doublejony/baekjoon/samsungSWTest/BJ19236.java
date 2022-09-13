package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 청소년 상어
 * https://www.acmicpc.net/problem/19236
 */
@RunWith(DataProviderRunner.class)
public class BJ19236 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "7 6 2 3 15 6 9 8",
                                "3 1 1 8 14 7 10 1",
                                "6 1 13 6 4 3 11 4",
                                "16 1 8 7 5 2 12 2"
                        },
                        "33"
                },
                {
                        new String[] {
                                "16 7 1 4 4 3 12 8",
                                "14 7 7 6 3 4 10 2",
                                "5 2 15 2 8 3 6 4",
                                "11 8 2 4 13 5 9 4"
                        },
                        "43"
                },
                {
                        new String[] {
                                "12 6 14 5 4 5 6 7",
                                "15 1 11 7 3 7 7 5",
                                "10 3 8 3 16 6 1 1",
                                "5 8 2 7 13 6 9 2"
                        },
                        "76"
                },
                {
                        new String[] {
                                "2 6 10 8 6 7 9 4",
                                "1 7 16 6 4 2 5 8",
                                "3 7 8 6 7 6 14 8",
                                "12 7 15 4 11 3 13 3"
                        },
                        "39"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ19236.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int answer = 0;
        int SHARK  = 99;

        Queue<SharkMap> queue;

        int[][] directions = new int[][] { { -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 },
                { -1, 1 } };

        public String solution(String[] input) {

            int[][] map = new int[4][4];
            int[][] directionMap = new int[4][4];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    map[i][j] = Integer.parseInt(input[i].split(" ")[j * 2]);
                    directionMap[i][j] = Integer.parseInt(input[i].split(" ")[(j * 2) + 1]) - 1;
                }
            }

            map[0][0] = SHARK;

            queue = new LinkedList<SharkMap>();
            queue.add(new SharkMap(0, map, directionMap));

            while (!queue.isEmpty()) {
                bfs(queue.poll());
            }

            return String.valueOf(answer);
        }

        private void bfs(SharkMap sharkMap) {

            moveFishes(sharkMap);
            moveShark(sharkMap);

            answer = Math.max(answer, sharkMap.moveCount);
        }

        private void moveShark(SharkMap sharkMap) {

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (sharkMap.map[i][j] == SHARK) {
                        int dx = directions[sharkMap.directionMap[i][j]][0];
                        int dy = directions[sharkMap.directionMap[i][j]][1];
                        while (true) {
                            try {
                                if (sharkMap.map[i + dx][j + dy] > 0 && sharkMap.map[i + dx][j + dy] <= 16) {

                                    int[][] newMap = new int[4][4];
                                    for (int k = 0; k < 4; k++) {
                                        System.arraycopy(sharkMap.map[k], 0, newMap[k], 0, 4);
                                    }
                                    int[][] newDirectionMap = new int[4][4];
                                    for (int k = 0; k < 4; k++) {
                                        System.arraycopy(sharkMap.directionMap[k], 0, newDirectionMap[k], 0, 4);
                                    }

                                    SharkMap newSharkMap = new SharkMap(sharkMap.moveCount, newMap, newDirectionMap);
                                    newSharkMap.map[i + dx][j + dy] = SHARK;
                                    newSharkMap.map[i][j] = -1;
                                    newSharkMap.directionMap[i][j] = -1;
                                    newSharkMap.moveCount++;
                                    queue.add(newSharkMap);
                                }
                                if (dx != 0) {
                                    dx = dx + (dx / Math.abs(dx));
                                }
                                if (dy != 0) {
                                    dy = dy + (dy / Math.abs(dy));
                                }
                            } catch (IndexOutOfBoundsException e) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        private void moveFishes(SharkMap sharkMap) {

            for (int i = 1; i <= 16; i++) {
                boolean doNext = false;
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (sharkMap.map[j][k] == i) {
                            doNext = true;
                            int origin = sharkMap.directionMap[j][k];
                            while (true) {
                                int dx = directions[sharkMap.directionMap[j][k]][0];
                                int dy = directions[sharkMap.directionMap[j][k]][1];
                                try {
                                    if (sharkMap.map[j + dx][k + dy] != SHARK) {
                                        int temp = sharkMap.map[j + dx][k + dy];
                                        sharkMap.map[j + dx][k + dy] = sharkMap.map[j][k];
                                        sharkMap.map[j][k] = temp;

                                        temp = sharkMap.directionMap[j + dx][k + dy];
                                        sharkMap.directionMap[j + dx][k + dy] = sharkMap.directionMap[j][k];
                                        sharkMap.directionMap[j][k] = temp;

                                        break;
                                    }
                                } catch (IndexOutOfBoundsException ignored) {

                                }
                                sharkMap.directionMap[j][k] += 1;
                                if (sharkMap.directionMap[j][k] >= 8) {
                                    sharkMap.directionMap[j][k] = 0;
                                }
                                if (sharkMap.directionMap[j][k] == origin) {
                                    break;
                                }
                            }
                        }
                        if (doNext) {
                            break;
                        }
                    }
                    if (doNext) {
                        break;
                    }
                }
            }
        }

        class SharkMap {

            int     moveCount;
            int[][] map;
            int[][] directionMap;

            public SharkMap(int moveCount, int[][] map, int[][] directionMap) {

                this.moveCount = moveCount;
                this.map = map;
                this.directionMap = directionMap;
            }
        }
    }
}

