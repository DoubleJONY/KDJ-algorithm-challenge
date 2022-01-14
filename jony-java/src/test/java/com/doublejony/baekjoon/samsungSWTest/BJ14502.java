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
 * 연구소
 * 문제
 * 인체에 치명적인 바이러스를 연구하던 연구소에서 바이러스가 유출되었다. 다행히 바이러스는 아직 퍼지지 않았고, 바이러스의 확산을 막기 위해서 연구소에 벽을 세우려고 한다.
 *
 * 연구소는 크기가 N×M인 직사각형으로 나타낼 수 있으며, 직사각형은 1×1 크기의 정사각형으로 나누어져 있다. 연구소는 빈 칸, 벽으로 이루어져 있으며, 벽은 칸 하나를 가득 차지한다.
 *
 * 일부 칸은 바이러스가 존재하며, 이 바이러스는 상하좌우로 인접한 빈 칸으로 모두 퍼져나갈 수 있다. 새로 세울 수 있는 벽의 개수는 3개이며, 꼭 3개를 세워야 한다.
 *
 * 예를 들어, 아래와 같이 연구소가 생긴 경우를 살펴보자.
 *
 * 2 0 0 0 1 1 0
 * 0 0 1 0 1 2 0
 * 0 1 1 0 1 0 0
 * 0 1 0 0 0 0 0
 * 0 0 0 0 0 1 1
 * 0 1 0 0 0 0 0
 * 0 1 0 0 0 0 0
 * 이때, 0은 빈 칸, 1은 벽, 2는 바이러스가 있는 곳이다. 아무런 벽을 세우지 않는다면, 바이러스는 모든 빈 칸으로 퍼져나갈 수 있다.
 *
 * 2행 1열, 1행 2열, 4행 6열에 벽을 세운다면 지도의 모양은 아래와 같아지게 된다.
 *
 * 2 1 0 0 1 1 0
 * 1 0 1 0 1 2 0
 * 0 1 1 0 1 0 0
 * 0 1 0 0 0 1 0
 * 0 0 0 0 0 1 1
 * 0 1 0 0 0 0 0
 * 0 1 0 0 0 0 0
 * 바이러스가 퍼진 뒤의 모습은 아래와 같아진다.
 *
 * 2 1 0 0 1 1 2
 * 1 0 1 0 1 2 2
 * 0 1 1 0 1 2 2
 * 0 1 0 0 0 1 2
 * 0 0 0 0 0 1 1
 * 0 1 0 0 0 0 0
 * 0 1 0 0 0 0 0
 * 벽을 3개 세운 뒤, 바이러스가 퍼질 수 없는 곳을 안전 영역이라고 한다. 위의 지도에서 안전 영역의 크기는 27이다.
 *
 * 연구소의 지도가 주어졌을 때 얻을 수 있는 안전 영역 크기의 최댓값을 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 지도의 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 8)
 *
 * 둘째 줄부터 N개의 줄에 지도의 모양이 주어진다. 0은 빈 칸, 1은 벽, 2는 바이러스가 있는 위치이다. 2의 개수는 2보다 크거나 같고, 10보다 작거나 같은 자연수이다.
 *
 * 빈 칸의 개수는 3개 이상이다.
 *
 * 출력
 * 첫째 줄에 얻을 수 있는 안전 영역의 최대 크기를 출력한다.
 */
@RunWith(DataProviderRunner.class)
public class BJ14502 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "7 7",
                                "2 0 0 0 1 1 0",
                                "0 0 1 0 1 2 0",
                                "0 1 1 0 1 0 0",
                                "0 1 0 0 0 0 0",
                                "0 0 0 0 0 1 1",
                                "0 1 0 0 0 0 0",
                                "0 1 0 0 0 0 0"
                        },
                        "27"
                },
                {
                        new String[]{
                                "4 6",
                                "0 0 0 0 0 0",
                                "1 0 0 0 0 2",
                                "1 1 1 0 0 2",
                                "0 0 0 0 0 2"
                        },
                        "9"
                },
                {
                        new String[]{
                                "8 8",
                                "2 0 0 0 0 0 0 2",
                                "2 0 0 0 0 0 0 2",
                                "2 0 0 0 0 0 0 2",
                                "2 0 0 0 0 0 0 2",
                                "2 0 0 0 0 0 0 2",
                                "0 0 0 0 0 0 0 0",
                                "0 0 0 0 0 0 0 0",
                                "0 0 0 0 0 0 0 0"
                        },
                        "3"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ14502.Main().solution(input), timer.stop());
    }

    /**
     * dfs 를 하되 캐싱을 하여 현재 날짜에서 선택 가능한 하위 항목의 경우 중 최대값을 날짜/금액 을 저장
     * 후에 다시 그 날짜에 정확히 도착하면 저장한 금액을 바로 반환
     */
    public class Main {

        int m;
        int n;
        int[][] map;

        int maxValue;

        class Virus {
            int x, y;

            Virus(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        public String solution(String[] input) {

            String[] a = input[0].split(" ");

            m = Integer.parseInt(a[0]);
            n = Integer.parseInt(a[1]);

            map = new int[m][n];

            maxValue = 0;

            for (int i = 0; i < m; i++) {
                String[] line = input[i + 1].split(" ");
                for (int j = 0; j < n; j++) {
                    map[i][j] = Integer.parseInt(line[j]);
                }
            }

            buildWall(0);

            return Integer.toString(maxValue);
        }

        private void buildWall(int depth) {
            if (depth >= 3) {
                spreadVirus();
                return;
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (map[i][j] == 0) {
                        map[i][j] = 1;
                        buildWall(depth + 1);
                        map[i][j] = 0;
                    }
                }
            }
        }

        private void spreadVirus() {
            int[][] virusMap = new int[m][n];
            Queue<Virus> queue = new LinkedList<Virus>();

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    virusMap[i][j] = map[i][j];
                }
            }

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++)
                    if (virusMap[i][j] == 2) {
                        queue.add(new Virus(i, j));
                    }
            }

            while (!queue.isEmpty()) {
                Virus virus = queue.remove();

                if (virus.x + 1 < m && virusMap[virus.x + 1][virus.y] == 0) {
                    virusMap[virus.x + 1][virus.y] = 2;
                    queue.add(new Virus(virus.x + 1, virus.y));
                }

                if (virus.x - 1 >= 0 && virusMap[virus.x - 1][virus.y] == 0) {
                    virusMap[virus.x - 1][virus.y] = 2;
                    queue.add(new Virus(virus.x - 1, virus.y));
                }

                if (virus.y + 1 < n && virusMap[virus.x][virus.y + 1] == 0) {
                    virusMap[virus.x][virus.y + 1] = 2;
                    queue.add(new Virus(virus.x, virus.y + 1));
                }

                if (virus.y - 1 >= 0 && virusMap[virus.x][virus.y - 1] == 0) {
                    virusMap[virus.x][virus.y - 1] = 2;
                    queue.add(new Virus(virus.x, virus.y - 1));
                }

            }
            countSafeArea(virusMap);
        }

        private void countSafeArea(int[][] virusMap) {
            int count = 0;

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (virusMap[i][j] == 0) {
                        count++;
                    }
                }
            }

            maxValue = Math.max(count, maxValue);
        }
    }
}
