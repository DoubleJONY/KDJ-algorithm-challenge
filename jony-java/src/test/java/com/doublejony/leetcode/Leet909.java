package com.doublejony.leetcode;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Leet909 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new int[][] {
                                { 1, 1, -1 },
                                { 1, 1, 1 },
                                { -1, 1, 1 }
                        },
                        -1
                },
                {
                        new int[][] {
                                { -1, -1, -1 },
                                { -1, 9, 8 },
                                { -1, 8, 9 }
                        },
                        1
                },
                {
                        new int[][] {
                                { -1, -1, -1, -1, -1, -1 },
                                { -1, -1, -1, -1, -1, -1 },
                                { -1, -1, -1, -1, -1, -1 },
                                { -1, 35, -1, -1, 13, -1 },
                                { -1, -1, -1, -1, -1, -1 },
                                { -1, 15, -1, -1, -1, -1 }
                        },
                        4
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[][] board, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Leet909.Solution().snakesAndLadders(board), timer.stop());
    }

    class Solution {

        Queue<Integer> queue     = new LinkedList<>();
        Queue<Integer> nextQueue = new LinkedList<>();
        int            depth     = 0;

        public int snakesAndLadders(int[][] board) {

            Map<Integer, Integer> map = new HashMap<>();
            int goal = buildMap(board, map);

            queue.add(1);
            return bfs(map, goal);
        }

        private int buildMap(int[][] board, Map<Integer, Integer> map) {

            int n = 1;
            for (int i = board.length - 1; i >= 0; i--) {
                for (int j = 0; j < board[i].length; j++) {
                    map.put(n, board[i][j]);
                    n++;
                }
                i--;

                if (i < 0) {
                    break;
                }

                for (int j = board[i].length - 1; j >= 0; j--) {
                    map.put(n, board[i][j]);
                    n++;
                }
            }
            return n - 1;
        }

        private int bfs(Map<Integer, Integer> map, int goal) {

            while (true) {
                if(depth > 100) {
                    return -1;
                }
                while (!queue.isEmpty()) {
                    int a = queue.poll();

                    if (a >= goal) {
                        return depth;
                    }

                    for (int i = 1; i <= 6; i++) {
                        Integer chance = map.get(a + i);
                        if (chance != null && chance != -1) {
                            nextQueue.add(chance);
                        } else {
                            nextQueue.add(a + i);
                        }
                    }
                }

                while (!nextQueue.isEmpty()) {
                    int c = nextQueue.poll();
                    if(!queue.contains(c)) {
                        queue.add(c);
                    }
                }

                depth++;
            }
        }
    }
}
