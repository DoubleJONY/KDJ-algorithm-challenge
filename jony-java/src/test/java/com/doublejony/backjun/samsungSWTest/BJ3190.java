package com.doublejony.backjun.samsungSWTest;

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
 * 뱀
 * 문제
 *  'Dummy' 라는 도스게임이 있다. 이 게임에는 뱀이 나와서 기어다니는데, 사과를 먹으면 뱀 길이가 늘어난다. 뱀이 이리저리 기어다니다가 벽 또는 자기자신의 몸과 부딪히면 게임이 끝난다.
 *
 * 게임은 NxN 정사각 보드위에서 진행되고, 몇몇 칸에는 사과가 놓여져 있다. 보드의 상하좌우 끝에 벽이 있다. 게임이 시작할때 뱀은 맨위 맨좌측에 위치하고 뱀의 길이는 1 이다. 뱀은 처음에 오른쪽을 향한다.
 *
 * 뱀은 매 초마다 이동을 하는데 다음과 같은 규칙을 따른다.
 *
 * 먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
 * 만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
 * 만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
 * 사과의 위치와 뱀의 이동경로가 주어질 때 이 게임이 몇 초에 끝나는지 계산하라.
 *
 * 입력
 * 첫째 줄에 보드의 크기 N이 주어진다. (2 ≤ N ≤ 100) 다음 줄에 사과의 개수 K가 주어진다. (0 ≤ K ≤ 100)
 *
 * 다음 K개의 줄에는 사과의 위치가 주어지는데, 첫 번째 정수는 행, 두 번째 정수는 열 위치를 의미한다. 사과의 위치는 모두 다르며, 맨 위 맨 좌측 (1행 1열) 에는 사과가 없다.
 *
 * 다음 줄에는 뱀의 방향 변환 횟수 L 이 주어진다. (1 ≤ L ≤ 100)
 *
 * 다음 L개의 줄에는 뱀의 방향 변환 정보가 주어지는데,  정수 X와 문자 C로 이루어져 있으며. 게임 시작 시간으로부터 X초가 끝난 뒤에 왼쪽(C가 'L') 또는 오른쪽(C가 'D')로 90도 방향을 회전시킨다는 뜻이다. X는 10,000 이하의 양의 정수이며, 방향 전환 정보는 X가 증가하는 순으로 주어진다.
 *
 * 출력
 * 첫째 줄에 게임이 몇 초에 끝나는지 출력한다.
 */
@RunWith(DataProviderRunner.class)
public class BJ3190 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "10",
                                "5",
                                "1 5",
                                "1 3",
                                "1 2",
                                "1 6",
                                "1 7",
                                "4",
                                "8 D",
                                "10 D",
                                "11 D",
                                "13 L"
                        },
                        "13"
                },
                {
                        new String[]{
                                "6",
                                "3",
                                "3 4",
                                "2 5",
                                "5 3",
                                "3",
                                "3 D",
                                "15 L",
                                "17 D"
                        },
                        "9"
                },
                {
                        new String[]{
                                "10",
                                "4",
                                "1 2",
                                "1 3",
                                "1 4",
                                "1 5",
                                "4",
                                "8 D",
                                "10 D",
                                "11 D",
                                "13 L"
                        },
                        "21"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ3190.Main().solution(input), timer.stop());
    }

    public class Main {

        final int LEFT = 0;
        final int UP = 1;
        final int RIGHT = 2;
        final int DOWN = 3;

        final int APPLE = 2;
        final int TAIL = 1;
        final int BLANK = 0;

        public String solution(String[] input) {

            int boardSize = Integer.parseInt(input[0]);

            int[][] board = new int[boardSize][boardSize];
            board[0][0] = TAIL;

            int apples = Integer.parseInt(input[1]);
            for (int i = 2; i < apples+2; i++) {
                board[Integer.parseInt(input[i].split(" ")[0])-1][Integer.parseInt(input[i].split(" ")[1])-1] = APPLE;
            }

            int records = Integer.parseInt(input[2+apples]);
            Queue<Vector> vectorQueue = new LinkedList<>();
            for (int i = 3+apples; i < records+3+apples; i++) {
                vectorQueue.add(new Vector(Integer.parseInt(input[i].split(" ")[0]), input[i].split(" ")[1]));
            }

            int answer = solve(board, vectorQueue);

            return Integer.toString(answer);
        }

        private int solve(int[][] board, Queue<Vector> vectorQueue) {
            int time = 0;
//            int snakeSize = 1;
            int direction = RIGHT;
            int x = 0;
            int y = 0;

            Queue<Point> pointQueue = new LinkedList<>();
            pointQueue.add(new Point(0,0));

            while(true) {

                time++;

                switch (direction) {
                    case RIGHT:
                        y++;
                        break;
                    case LEFT:
                        y--;
                        break;
                    case DOWN:
                        x++;
                        break;
                    case UP:
                        x--;
                        break;
                }

                if(isOutRange(x, y, board.length)) {
                    return time;
                }

                if(board[x][y] == TAIL) { // END
                    return time;
                }

                if(board[x][y] != APPLE) {
                    //remove tail
                    Point a = pointQueue.poll();
                    board[a.height][a.width] = BLANK;
                }

                //add head
                pointQueue.add(new Point(x, y));
                board[x][y] = TAIL;

                if (vectorQueue.peek() != null && vectorQueue.peek().time == time) {
                    if (vectorQueue.peek().rotate.equals("L")) {
                        direction = (direction + 3) % 4;
                    } else if (vectorQueue.peek().rotate.equals("D")) {
                        direction = (direction + 1) % 4;
                    }
                    vectorQueue.poll();
                }
            }

        }

        private boolean isOutRange(int x, int y, int boardSize) {
            return x < 0 || y < 0 || x >= boardSize || y >= boardSize;
        }

        class Point {
            int height;
            int width;

            public Point(int height, int width) {
                this.height = height;
                this.width = width;
            }
        }

        class Vector {
            int time;
            String rotate;

            public Vector(int time, String rotate) {
                this.time = time;
                this.rotate = rotate;
            }
        }
    }
}
