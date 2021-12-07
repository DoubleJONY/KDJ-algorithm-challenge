package com.doublejony.backjun.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class Game2048 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "3",
                                "2 2 2",
                                "4 4 4",
                                "8 8 8"
                        },
                        "16"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Main().solution(input), timer.stop());
    }

    public class Main {

        public String solution(String[] input) {

            int size = Integer.parseInt(input[0]);
            int[][] map = new int[size][size];

            for (int i = 0; i < size; i++) {
                String[] temp = input[i + 1].split(" ");
                for (int j = 0; j < temp.length; j++) {
                    map[i][j] = Integer.parseInt(temp[j]);
                }
            }

            int maxNumber = moveTile(map,"", 0);


            return Integer.toString(maxNumber);
        }

        private int moveTile(int[][] map, String direction, int depth) {
            int maxNumber = 0;

            if(!direction.equals("")) {
                depth++;
                int[][] lockMap = new int[map.length][map[0].length];
                switch (direction) {
                    case "up":
                        for (int i = 1; i < map.length; i++) {
                            for (int j = 0; j < map[i].length; j++) {
                                //checkMerge
                                for (int k = i-1; k >= 0; k--) {
                                    if(map[k][j] == map[i][j] && lockMap[k][j] != -1) {
                                        map[k][j] += map[i][j];
                                        map[i][j] = 0;

                                        while (k - 1 >= 0 && map[k - 1][j] == 0) {
                                            map[k - 1][j] = map[k][j];
                                            map[k][j] = 0;
                                            k--;
                                        }

                                        lockMap[k][j] = -1;
                                        break;
                                    }
                                }

                                //move
                                for (int k = i-1; k >= 0; k--) {
                                    if(map[k][j] != 0){
                                        break;
                                    }
                                    map[k][j] = map[k+1][j];
                                    map[k+1][j] = 0;
                                    k--;
                                }
                            }
                        }
                        break;
                    case "down":
                        for (int i = map.length - 2; i >= 0; i--) {
                            for (int j = 0; j < map[i].length; j++) {
                                //checkMerge
                                for (int k = i+1; k < map.length; k++) {
                                    if(map[k][j] == map[i][j] && lockMap[k][j] != -1) {
                                        map[k][j] += map[i][j];
                                        map[i][j] = 0;

                                        while (k + 1 < map.length && map[k + 1][j] == 0) {
                                            map[k + 1][j] = map[k][j];
                                            map[k][j] = 0;
                                            k++;
                                        }

                                        lockMap[k][j] = -1;
                                        break;
                                    }
                                }

                                //move
                                for (int k = i+1; k < map.length; k++) {
                                    if(map[k][j] != 0){
                                        break;
                                    }
                                    map[k][j] = map[k-1][j];
                                    map[k-1][j] = 0;
                                    k++;
                                }
                            }
                        }
                        break;
                    case "left":
                        for (int j = 1; j < map[0].length; j++) {
                            for (int i = 0; i < map.length; i++) {
                                //checkMerge
                                for (int k = j-1; k >= 0; k--) {
                                    if(map[i][k] == map[i][j] && lockMap[i][k] != -1) {
                                        map[i][k] += map[i][j];
                                        map[i][j] = 0;

                                        while (k - 1 >= 0 && map[i][k - 1] == 0) {
                                            map[i][k - 1] = map[i][k];
                                            map[i][k] = 0;
                                            k--;
                                        }

                                        lockMap[i][k] = -1;
                                        break;
                                    }
                                }

                                //move
                                for (int k = i-1; k >= 0; k--) {
                                    if(map[i][k] != 0){
                                        break;
                                    }
                                    map[i][k] = map[i][k+1];
                                    map[i][k+1] = 0;
                                    k--;
                                }
                            }
                        }
                        break;
                    case "right":
                        for (int j = map[0].length - 2; j >= 0; j--) {
                            for (int i = 0; i < map.length; i++) {
                                //checkMerge
                                for (int k = j+1; k < map.length; k++) {
                                    if(map[i][k] == map[i][j] && lockMap[i][k] != -1) {
                                        map[i][k] += map[i][j];
                                        map[i][j] = 0;

                                        while (k + 1 < map.length && map[i][k + 1] == 0) {
                                            map[i][k + 1] = map[i][k];
                                            map[i][k] = 0;
                                            k++;
                                        }

                                        lockMap[i][k] = -1;
                                        break;
                                    }
                                }

                                //move
                                for (int k = i+1; k < map.length; k++) {
                                    if(map[i][k] != 0){
                                        break;
                                    }
                                    map[i][k] = map[i][k-1];
                                    map[i][k-1] = 0;
                                    k++;
                                }
                            }
                        }
                        break;
                }

                if(depth >= 5) {
                    for (int i = 0; i < map.length; i++) {
                        for (int j = 0; j < map[i].length; j++) {
                            if(map[i][j] > maxNumber) {
                                maxNumber = map[i][j];
                            }
                        }
                    }
                    return maxNumber;
                }
            }

            int up = moveTile(map, "up", depth);
            int down = moveTile(map, "down", depth);
            int left = moveTile(map, "left", depth);
            int right = moveTile(map, "right", depth);

            if(maxNumber < up) {
                maxNumber = up;
            }
            if(maxNumber < down) {
                maxNumber = down;
            }
            if(maxNumber < left) {
                maxNumber = left;
            }
            if(maxNumber < right) {
                maxNumber = right;
            }

            return maxNumber;
        }
    }
}
