package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 마법사 상어
 * https://www.acmicpc.net/problem/20056
 */
@RunWith(DataProviderRunner.class)
public class BJ20057 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "5",
                                "0 0 0 0 0",
                                "0 0 0 0 0",
                                "0 10 0 0 0",
                                "0 0 0 0 0",
                                "0 0 0 0 0"
                        },
                        "10"
                },
                {
                        new String[]{
                                "5",
                                "0 0 0 0 0",
                                "0 0 0 0 0",
                                "0 100 0 0 0",
                                "0 0 0 0 0",
                                "0 0 0 0 0"
                        },
                        "85"
                },
                {
                        new String[]{
                                "7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 0 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7",
                                "1 2 3 4 5 6 7"
                        },
                        "139"
                },
                {
                        new String[]{
                                "5",
                                "100 200 300 400 200",
                                "300 243 432 334 555",
                                "999 111 0 999 333",
                                "888 777 222 333 900",
                                "100 200 300 400 500"
                        },
                        "7501"
                },
                {
                        new String[]{
                                "5",
                                "0 0 100 0 0",
                                "0 0 100 0 0",
                                "0 0 0 0 0",
                                "0 0 100 0 0",
                                "0 0 100 0 0"
                        },
                        "283"
                },
                {
                        new String[]{
                                "9",
                                "193 483 223 482 858 274 847 283 748",
                                "484 273 585 868 271 444 584 293 858",
                                "828 384 382 818 347 858 293 999 727",
                                "818 384 727 373 636 141 234 589 991",
                                "913 564 555 827 0 999 123 123 123",
                                "321 321 321 983 982 981 983 980 990",
                                "908 105 270 173 147 148 850 992 113",
                                "943 923 982 981 223 131 222 913 562",
                                "752 572 719 590 551 179 141 137 731"
                        },
                        "22961"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ20057.Main().solution(input),
                timer.stop());
    }

    public class Main {

        final int LEFT = 0;
        final int DOWN = 1;
        final int RIGHT = 2;
        final int UP = 3;

        int[] dh = {0, 1, 0, -1};
        int[] dw = {-1, 0, 1, 0};

        int[][] spreadMapLeft = new int[][]{
                {0, 0, 2, 0, 0},
                {0, 10, 7, 1, 0},
                {5, -3, -2, -1, 0},
                {0, 10, 7, 1, 0},
                {0, 0, 2, 0, 0}
        };

        int[][] spreadMapRight = new int[][]{
                {0, 0, 2, 0, 0},
                {0, 1, 7, 10, 0},
                {0, -1, -2, -3, 5},
                {0, 1, 7, 10, 0},
                {0, 0, 2, 0, 0}
        };

        int[][] spreadMapUp = new int[][]{
                {0, 0, 5, 0, 0},
                {0, 10, -3, 10, 0},
                {2, 7, -2, 7, 2},
                {0, 1, -1, 1, 0},
                {0, 0, 0, 0, 0}
        };

        int[][] spreadMapDown = new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, -1, 1, 0},
                {2, 7, -2, 7, 2},
                {0, 10, -3, 10, 0},
                {0, 0, 5, 0, 0}
        };

        int[][] map;

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);

            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(input[i+1].split(" ")[j]);
                }
            }

            move((int) Math.ceil((double) N / 2.0), (int) Math.ceil((double) N / 2.0), LEFT,1, 1);

            return null;
        }

        private void move(int h, int w, int direction, int i, int step) {

            int[][] spreadMap;

            switch (direction) {
                case LEFT:
                    spreadMap = spreadMapLeft;
                    break;
                case RIGHT:
                    spreadMap = spreadMapRight;
                    break;
                case UP:
                    spreadMap = spreadMapUp;
                    break;
                case DOWN:
                    spreadMap = spreadMapDown;
                    break;
            }

            int originSand = map[h+dh[direction]][w+dw[direction]];

            

            if (step == 0) {
                step++;
            } else if (step == 1) {
                i++;
                step = 0;
            }


        }
    }
}

