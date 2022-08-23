package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.IntStream;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 원판 돌리기
 * https://www.acmicpc.net/problem/17822
 */
@RunWith(DataProviderRunner.class)
public class BJ20061 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "1",
                                "1 1 1"
                        },
                        new String[] {
                                "0",
                                "2"
                        }
                },
                {
                        new String[] {
                                "2",
                                "1 1 1",
                                "2 3 0"
                        },
                        new String[] {
                                "0",
                                "6"
                        }
                },
                {
                        new String[] {
                                "4",
                                "1 1 1",
                                "2 3 0",
                                "3 2 2",
                                "3 2 3"
                        },
                        new String[] {
                                "1",
                                "10"
                        }
                },
                {
                        new String[] {
                                "5",
                                "1 1 1",
                                "2 3 0",
                                "3 2 2",
                                "3 2 3",
                                "3 1 3"
                        },
                        new String[] {
                                "1",
                                "16"
                        }
                },
                {
                        new String[] {
                                "7",
                                "1 1 1",
                                "2 3 0",
                                "3 2 2",
                                "3 2 3",
                                "3 1 3",
                                "2 0 0",
                                "3 2 0"
                        },
                        new String[] {
                                "1",
                                "18"
                        }
                },
                {
                        new String[] {
                                "8",
                                "1 1 1",
                                "2 3 0",
                                "3 2 2",
                                "3 2 3",
                                "3 1 3",
                                "2 0 0",
                                "3 2 0",
                                "3 1 2"
                        },
                        new String[] {
                                "2",
                                "15"
                        }
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ20061.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int answer = 0;

        int[][] greenMap = new int[6][4];
        int[][] blueMap = new int[6][4];

        public String[] solution(String[] input) {

            int n = Integer.parseInt(input[0]);

            Queue<Block> blockList = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                blockList.add(new Block(
                        Integer.parseInt(input[i + 1].split(" ")[0]),
                        Integer.parseInt(input[i + 1].split(" ")[1]),
                        Integer.parseInt(input[i + 1].split(" ")[2]))
                );
            }

            for (int i = 0; i < n; i++) {

                // drop block
                Block block = blockList.poll();
                dropGreen(block);
                dropBlue(block);

                // check rowClear
                rowClear(greenMap);
                rowClear(blueMap);

                // check overStacked
                pushOverStacked(greenMap);
                pushOverStacked(blueMap);

            }

            int sum = 0;
            sum += countBlock(greenMap);
            sum += countBlock(blueMap);

            return new String[]{String.valueOf(answer), String.valueOf(sum)};
        }

        private int countBlock(int[][] map) {

            return IntStream.range(2, 6).map(i -> Arrays.stream(map[i]).sum()).sum();
        }

        private void pushOverStacked(int[][] map) {
            while (Arrays.stream(map[1]).sum() != 0) {
                for (int i = 5; i >= 1; i--) {
                    System.arraycopy(map[i - 1], 0, map[i], 0, 4);
                }
            }

        }

        private void rowClear(int[][] map) {

            for (int i = 5; i >= 2; i--) {
                if (Arrays.stream(map[i]).sum() == 4) {
                    answer++;
                    for (int j = 0; j < 4; j++) {
                        map[i][j] = 0;
                    }
                    for (int j = i; j >= 3; j--) {
                        System.arraycopy(map[j - 1], 0, map[j], 0, 4);
                    }
                }
            }
        }

        private void dropGreen(Block block) {

            int[][] redMap = new int[4][4];

            redMap[block.x][block.y] = 1;
            if (block.t == 2) {
                redMap[block.x][block.y+1] = 1;
            } else if (block.t == 3) {
                redMap[block.x+1][block.y] = 1;
            }

            drop(redMap, greenMap);
        }

        private void dropBlue(Block block) {
            int[][] redMap = new int[4][4];

            redMap[block.y][block.x] = 1;
            if (block.t == 2) {
                redMap[block.y+1][block.x] = 1;
            } else if (block.t == 3) {
                redMap[block.y][block.x+1] = 1;
            }

            drop(redMap, blueMap);
        }


        private void drop(int[][] redMap, int[][] map) {

            while (Arrays.stream(redMap[3]).sum() == 0) {
                for (int i = 3; i > 0; i--) {
                    System.arraycopy(redMap[i - 1], 0, redMap[i], 0, 4);
                }
            }

            map[0] = redMap[2];
            map[1] = redMap[3];

            Map<Integer, Integer> blockColumns = new HashMap<>();

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    if (map[i][j] == 1) {
                        blockColumns.put(j, 1);
                    }
                }
            }

            int currentRow = 2;
            while (currentRow <= 5) {

                boolean isStuck = false;
                for (int c : blockColumns.keySet()) {
                    if (map[currentRow-1][c] == 1 && map[currentRow][c] == 1) {
                        isStuck = true;
                        break;
                    }
                }

                if (isStuck) {
                    break;
                }

                for (int i = currentRow; i >= currentRow - 1; i--) {
                    for (int c : blockColumns.keySet()) {
                        map[i][c] = map[i-1][c];
                    }
                }

                if (currentRow == 2) {
                    for (int i = 0; i < 4; i++) {
                        map[0][i] = 0;
                    }
                }

                currentRow++;
            }
        }

        private class Block {

            int t;
            int x;
            int y;

            public Block(int t, int x, int y) {
                this.t = t;
                this.x = x;
                this.y = y;
            }

        }
    }
}

