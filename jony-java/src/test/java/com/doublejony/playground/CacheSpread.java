package com.doublejony.playground;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class CacheSpread {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[][]{
                                {0, 0, 0},
                                {0, 0, 0},
                                {1, 0, 0}
                        },
                        4
                },
                {
                        new int[][]{
                                {0, 1},
                                {1, 0}
                        },
                        1
                },
                {
                        new int[][]{
                                {0, 1 ,0},
                                {1, 0, 1},
                                {0, 1, 0}
                        },
                        1
                },
                {
                        new int[][]{
                                {0, 1 ,0, 1, 0},
                                {1, 0, 1, 0 ,1},
                                {0, 0, 0, 0, 0},
                                {0, 0, 1, 0, 0}
                        },
                        2
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[][] input, int expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new CacheSpread.Main().solution(input), timer.stop());
    }

    public class Main {

        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};

        public int solution(int[][] input) {

            int depth = 1;

            while (true) {

                boolean isBlank = false;

                for (int i = 0; i < input.length; i++) {
                    for (int j = 0; j < input[i].length; j++) {
                        if (input[i][j] == depth) {
                            for (int k = 0; k < 4; k++) {
                                try {
                                    if (input[i + dx[k]][j + dy[k]] == 0) {
                                        input[i + dx[k]][j + dy[k]] = depth + 1;
                                        isBlank = true;
                                    }
                                } catch (ArrayIndexOutOfBoundsException ignored) {
                                    //ignored
                                }
                            }
                        }
                    }
                }

                if (!isBlank) {
                    break;
                }
                depth++;
            }

            return depth-1;
        }
    }
}
