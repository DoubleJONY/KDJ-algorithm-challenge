package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 로봇 청소기
 * 로봇 청소기가 주어졌을 때, 청소하는 영역의 개수를 구하는 프로그램을 작성하시오.
 *
 * 로봇 청소기가 있는 장소는 N×M 크기의 직사각형으로 나타낼 수 있으며, 1×1크기의 정사각형 칸으로 나누어져 있다. 각각의 칸은 벽 또는 빈 칸이다. 청소기는 바라보는 방향이 있으며, 이 방향은 동, 서, 남, 북중 하나이다. 지도의 각 칸은 (r, c)로 나타낼 수 있고, r은 북쪽으로부터 떨어진 칸의 개수, c는 서쪽으로 부터 떨어진 칸의 개수이다.
 *
 * 로봇 청소기는 다음과 같이 작동한다.
 *
 * 현재 위치를 청소한다.
 * 현재 위치에서 현재 방향을 기준으로 왼쪽 방향부터 차례대로 인접한 칸을 탐색한다.
 * 왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다.
 * 왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
 * 네 방향 모두 청소가 이미 되어있거나 벽인 경우에는, 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
 * 네 방향 모두 청소가 이미 되어있거나 벽이면서, 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.
 * 로봇 청소기는 이미 청소되어있는 칸을 또 청소하지 않으며, 벽을 통과할 수 없다.
 *
 * 입력
 * 첫째 줄에 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 50)
 *
 * 둘째 줄에 로봇 청소기가 있는 칸의 좌표 (r, c)와 바라보는 방향 d가 주어진다. d가 0인 경우에는 북쪽을, 1인 경우에는 동쪽을, 2인 경우에는 남쪽을, 3인 경우에는 서쪽을 바라보고 있는 것이다.
 *
 * 셋째 줄부터 N개의 줄에 장소의 상태가 북쪽부터 남쪽 순서대로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다. 빈 칸은 0, 벽은 1로 주어진다. 지도의 첫 행, 마지막 행, 첫 열, 마지막 열에 있는 모든 칸은 벽이다.
 *
 * 로봇 청소기가 있는 칸의 상태는 항상 빈 칸이다.
 *
 * 출력
 * 로봇 청소기가 청소하는 칸의 개수를 출력한다.
 */
@RunWith(DataProviderRunner.class)
public class BJ14503 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "3 3",
                                "1 1 0",
                                "1 1 1",
                                "1 0 1",
                                "1 1 1"
                        },
                        "1"
                },
                {
                        new String[]{
                                "11 10",
                                "7 4 0",
                                "1 1 1 1 1 1 1 1 1 1",
                                "1 0 0 0 0 0 0 0 0 1",
                                "1 0 0 0 1 1 1 1 0 1",
                                "1 0 0 1 1 0 0 0 0 1",
                                "1 0 1 1 0 0 0 0 0 1",
                                "1 0 0 0 0 0 0 0 0 1",
                                "1 0 0 0 0 0 0 1 0 1",
                                "1 0 0 0 0 0 1 1 0 1",
                                "1 0 0 0 0 0 1 1 0 1",
                                "1 0 0 0 0 0 0 0 0 1",
                                "1 1 1 1 1 1 1 1 1 1"
                        },
                        "57"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ14503.Main().solution(input), timer.stop());
    }


    public class Main {

        int[] H = new int[]{0, 1, 0, -1};
        int[] W = new int[]{-1, 0, 1, 0};

        int h;
        int w;
        int r;
        int c;
        int direction;

        int turnCount = 0;

        int[][] map;


        public String solution(String[] input) {

            h = Integer.parseInt(input[0].split(" ")[0]);
            w = Integer.parseInt(input[0].split(" ")[1]);
            r = Integer.parseInt(input[1].split(" ")[0]);
            c = Integer.parseInt(input[1].split(" ")[1]);
            direction = Integer.parseInt(input[1].split(" ")[2]);

            map = new int[h][w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    map[i][j] = Integer.parseInt(input[i+2].split(" ")[j]);
                }
            }

            return String.valueOf(startVacuum());
        }

        private int startVacuum() {

            int answer = 0;

            while (true) {
                if (map[r][c] == 0) {
                    map[r][c] = 2;
                    answer++;
                }

                if (turnCount >= 4) {
                    if (map[r - H[direction]][c - W[direction]] == 1) {
                        break;
                    } else {
                        r -= H[direction];
                        c -= W[direction];
                        turnCount = 0;
                    }
                } else if (map[r + H[direction]][c + W[direction]] == 0) {
                    r += H[direction];
                    c += W[direction];
                    turnLeft();
                    turnCount = 0;
                } else {
                    turnLeft();
                    turnCount++;
                }
            }

            return answer;
        }

        private void turnLeft() {
            direction--;
            if (direction < 0) {
                direction = 3;
            }
        }
    }
}
