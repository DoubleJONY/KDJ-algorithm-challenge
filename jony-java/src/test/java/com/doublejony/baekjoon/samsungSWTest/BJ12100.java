package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 2048(Easy)
 * 문제
 * 2048 게임은 4×4 크기의 보드에서 혼자 즐기는 재미있는 게임이다. 이 링크를 누르면 게임을 해볼 수 있다.
 *
 * 이 게임에서 한 번의 이동은 보드 위에 있는 전체 블록을 상하좌우 네 방향 중 하나로 이동시키는 것이다. 이때, 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐지게 된다. 한 번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 다시 합쳐질 수 없다. (실제 게임에서는 이동을 한 번 할 때마다 블록이 추가되지만, 이 문제에서 블록이 추가되는 경우는 없다)
 *
 * 이 문제에서 다루는 2048 게임은 보드의 크기가 N×N 이다. 보드의 크기와 보드판의 블록 상태가 주어졌을 때, 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값을 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 보드의 크기 N (1 ≤ N ≤ 20)이 주어진다. 둘째 줄부터 N개의 줄에는 게임판의 초기 상태가 주어진다. 0은 빈 칸을 나타내며, 이외의 값은 모두 블록을 나타낸다. 블록에 쓰여 있는 수는 2보다 크거나 같고, 1024보다 작거나 같은 2의 제곱꼴이다. 블록은 적어도 하나 주어진다.
 *
 * 출력
 * 최대 5번 이동시켜서 얻을 수 있는 가장 큰 블록을 출력한다.
 */
@RunWith(DataProviderRunner.class)
public class BJ12100 {

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
                },
                {
                        new String[]{
                                "4",
                                "0 0 0 4",
                                "512 8 8 0",
                                "512 4 0 32",
                                "0 0 64 512"
                        },
                        "1024"
                },
                {
                        new String[]{
                                "4",
                                "64 2 256 0",
                                "0 512 0 64",
                                "0 0 256 256",
                                "2 128 256 0"
                        },
                        "1024"
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
