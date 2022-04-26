package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 감시
 * https://www.acmicpc.net/problem/15683
 */
@RunWith(DataProviderRunner.class)
public class BJ15685 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "3",
                                "3 3 0 1",
                                "4 2 1 3",
                                "4 2 2 1"
                        },
                        "4"
                },
                {
                        new String[]{
                                "4",
                                "3 3 0 1",
                                "4 2 1 3",
                                "4 2 2 1",
                                "2 7 3 4"
                        },
                        "11"
                },
                {
                        new String[]{
                                "10",
                                "5 5 0 0",
                                "5 6 0 0",
                                "5 7 0 0",
                                "5 8 0 0",
                                "5 9 0 0",
                                "6 5 0 0",
                                "6 6 0 0",
                                "6 7 0 0",
                                "6 8 0 0",
                                "6 9 0 0"
                        },
                        "8"
                },
                {
                        new String[]{
                                "4",
                                "50 50 0 10",
                                "50 50 1 10",
                                "50 50 2 10",
                                "50 50 3 10"
                        },
                        "1992"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ15685.Main().solution(input), timer.stop());
    }


    public class Main {

        int[] dx = {1, 0, -1, 0, 1, 0, -1, 0};
        int[] dy = {0, -1, 0, 1, 0, -1, 0, 1};

        public String solution(String[] input) {
            int n = Integer.parseInt(input[0]);
            Queue<Dragon> queue = new LinkedList<>();
            for (int i = 1; i < input.length; i++) {
                String[] data = input[i].split(" ");
                queue.add(new Dragon(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            }

            int map[][] = new int[101][101]; // 100 * 100 이라면서 왜 0 <= x,y <= 100 ?

            while (!queue.isEmpty()) {
                Dragon dragon = queue.poll();
                draw(map, dragon);
            }

            return String.valueOf(countSquare(map));
        }

        private int countSquare(int[][] map) {

            int answer = 0;

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    if (map[i][j] == 1 && map[i][j+1] == 1 && map[i+1][j] == 1 && map[i+1][j+1] == 1) {
                        answer++;
                    }
                }
            }

            return answer;
        }

        private void draw(int[][] map, Dragon dragon) {
            int nx = dragon.x;
            int ny = dragon.y;

            List<Integer> list = new ArrayList<>();

            list.add(dragon.d);

            for (int i = 1; i <= dragon.gen; i++) {
                for (int j = list.size() - 1; j >= 0; j--) {
                    list.add((list.get(j) + 1) % 4);
                }
            }

            map[ny][nx] = 1;
            for (int a : list) {
                nx += dx[a];
                ny += dy[a];
                if (nx >= 0 && nx <= 100 && ny >= 0 && ny <= 100) {
                    if (map[ny][nx] == 0) {
                        map[ny][nx] = 1;
                    }
                }
            }

        }

        class Dragon {
            int x;
            int y;
            int d;
            int gen;

            public Dragon(int x, int y, int d, int gen) {
                this.x = x;
                this.y = y;
                this.d = d;
                this.gen = gen;
            }
        }
    }
}

