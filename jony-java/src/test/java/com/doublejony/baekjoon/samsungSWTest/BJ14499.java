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
 * 주사위 굴리기
 * 문제
 * 크기가 N×M인 지도가 존재한다. 지도의 오른쪽은 동쪽, 위쪽은 북쪽이다. 이 지도의 위에 주사위가 하나 놓여져 있으며, 주사위의 전개도는 아래와 같다. 지도의 좌표는 (r, c)로 나타내며, r는 북쪽으로부터 떨어진 칸의 개수, c는 서쪽으로부터 떨어진 칸의 개수이다.
 *
 * 2
 * 4 1 3
 * 5
 * 6
 * 주사위는 지도 위에 윗 면이 1이고, 동쪽을 바라보는 방향이 3인 상태로 놓여져 있으며, 놓여져 있는 곳의 좌표는 (x, y) 이다. 가장 처음에 주사위에는 모든 면에 0이 적혀져 있다.
 *
 * 지도의 각 칸에는 정수가 하나씩 쓰여져 있다. 주사위를 굴렸을 때, 이동한 칸에 쓰여 있는 수가 0이면, 주사위의 바닥면에 쓰여 있는 수가 칸에 복사된다. 0이 아닌 경우에는 칸에 쓰여 있는 수가 주사위의 바닥면으로 복사되며, 칸에 쓰여 있는 수는 0이 된다.
 *
 * 주사위를 놓은 곳의 좌표와 이동시키는 명령이 주어졌을 때, 주사위가 이동했을 때 마다 상단에 쓰여 있는 값을 구하는 프로그램을 작성하시오.
 *
 * 주사위는 지도의 바깥으로 이동시킬 수 없다. 만약 바깥으로 이동시키려고 하는 경우에는 해당 명령을 무시해야 하며, 출력도 하면 안 된다.
 *
 * 입력
 * 첫째 줄에 지도의 세로 크기 N, 가로 크기 M (1 ≤ N, M ≤ 20), 주사위를 놓은 곳의 좌표 x, y(0 ≤ x ≤ N-1, 0 ≤ y ≤ M-1), 그리고 명령의 개수 K (1 ≤ K ≤ 1,000)가 주어진다.
 *
 * 둘째 줄부터 N개의 줄에 지도에 쓰여 있는 수가 북쪽부터 남쪽으로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다. 주사위를 놓은 칸에 쓰여 있는 수는 항상 0이다. 지도의 각 칸에 쓰여 있는 수는 10 미만의 자연수 또는 0이다.
 *
 * 마지막 줄에는 이동하는 명령이 순서대로 주어진다. 동쪽은 1, 서쪽은 2, 북쪽은 3, 남쪽은 4로 주어진다.
 *
 * 출력
 * 이동할 때마다 주사위의 윗 면에 쓰여 있는 수를 출력한다. 만약 바깥으로 이동시키려고 하는 경우에는 해당 명령을 무시해야 하며, 출력도 하면 안 된다.
 */
@RunWith(DataProviderRunner.class)
public class BJ14499 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "4 2 0 0 8",
                                "0 2",
                                "3 4",
                                "5 6",
                                "7 8",
                                "4 4 4 1 3 3 3 2"
                        },
                        new String[]{
                                "0",
                                "0",
                                "3",
                                "0",
                                "0",
                                "8",
                                "6",
                                "3"
                        }
                },
                {
                        new String[]{
                                "3 3 1 1 9",
                                "1 2 3",
                                "4 0 5",
                                "6 7 8",
                                "1 3 2 2 4 4 1 1 3"
                        },
                        new String[]{
                                "0",
                                "0",
                                "0",
                                "3",
                                "0",
                                "1",
                                "0",
                                "6",
                                "0"
                        }
                },
                {
                        new String[]{
                                "2 2 0 0 16",
                                "0 2",
                                "3 4",
                                "4 4 4 4 1 1 1 1 3 3 3 3 2 2 2 2"
                        },
                        new String[]{
                                "0",
                                "0",
                                "0",
                                "0"
                        }
                },
                {
                        new String[]{
                                "3 3 0 0 16",
                                "0 1 2",
                                "3 4 5",
                                "6 7 8",
                                "4 4 1 1 3 3 2 2 4 4 1 1 3 3 2 2"
                        },
                        new String[]{
                                "0",
                                "0",
                                "0",
                                "6",
                                "0",
                                "8",
                                "0",
                                "2",
                                "0",
                                "8",
                                "0",
                                "2",
                                "0",
                                "8",
                                "0",
                                "2"
                        }
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String[] expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ14499.Main().solution(input), timer.stop());
    }

    public class Main {

        final int LEFT = 2;
        final int UP = 3;
        final int RIGHT = 1;
        final int DOWN = 4;

        Board board;

        public String[] solution(String[] input) {

            String[] buffer1 = input[0].split(" ");
            int boardX = Integer.parseInt(buffer1[0]);
            int boardY = Integer.parseInt(buffer1[1]);

            Dice dice = new Dice(Integer.parseInt(buffer1[2]), Integer.parseInt(buffer1[3]));

            this.board = new Board(boardX, boardY, input);

            String[] commandBuffer = input[boardX + 1].split(" ");
            Queue<Integer> commandQueue = new LinkedList<>();
            for (String s : commandBuffer) {
                commandQueue.add(Integer.parseInt(s));
            }

            List<String> answerList = new ArrayList<>();

            while(!commandQueue.isEmpty()) {
                int command = commandQueue.poll();

                boolean painted = false;

                switch (command) {
                    case RIGHT:
                        painted = dice.rollRight();
                        break;
                    case LEFT:
                        painted = dice.rollLeft();
                        break;
                    case DOWN:
                        painted = dice.rollDown();
                        break;
                    case UP:
                        painted = dice.rollUp();
                        break;
                }

                if (painted) {
                    paintNumber(dice);
                    answerList.add(Integer.toString(dice.getTop()));
                }
            }

            return answerList.toArray(new String[0]);
        }

        private void paintNumber(Dice dice) {
            int temp = this.board.map[dice.height][dice.width];
            if (temp == 0) {
                this.board.map[dice.height][dice.width] = dice.getBottom();
            } else {
                dice.setBottom(temp);
                this.board.map[dice.height][dice.width] = 0;
            }
        }

        private class Board {
            int boardHeight;
            int boardWidth;
            int[][] map;

            public Board(int boardHeight, int boardWidth, String[] input) {
                this.boardHeight = boardHeight;
                this.boardWidth = boardWidth;

                this.map = new int[boardHeight][boardWidth];

                for (int i = 1; i <= boardHeight; i++) {
                    String[] row = input[i].split(" ");
                    for (int j = 0; j < boardWidth; j++) {
                        map[i - 1][j] = Integer.parseInt(row[j]);
                    }
                }
            }

            public void setValue(int height, int width, int value) {
                this.map[height][width] = value;
            }
        }

        private class Dice {
            int height;
            int width;

            int top;
            int left;
            int right;
            int up;
            int down;
            int bottom;

            public Dice(int height, int width) {
                this.height = height;
                this.width = width;

                this.top = 0;
                this.left = 0;
                this.right = 0;
                this.up = 0;
                this.down = 0;
                this.bottom = 0;
            }

            public int getTop() {
                return this.top;
            }

            public int getBottom() {
                return this.bottom;
            }

            public boolean rollUp() {
                if (isRollable(height - 1, width)) {
                    int temp = this.up;
                    this.up = this.top;
                    this.top = this.down;
                    this.down = this.bottom;
                    this.bottom = temp;

                    this.height--;

                    return true;
                } else {
                    return false;
                }
            }

            public boolean rollDown() {
                if (isRollable(height + 1, width)) {
                    int temp = this.bottom;
                    this.bottom = this.down;
                    this.down = this.top;
                    this.top = this.up;
                    this.up = temp;

                    this.height++;

                    return true;
                } else {
                    return false;
                }
            }

            public boolean rollLeft() {
                if (isRollable(height, width - 1)) {
                    int temp = this.bottom;
                    this.bottom = this.left;
                    this.left = this.top;
                    this.top = this.right;
                    this.right = temp;

                    this.width--;

                    return true;
                } else {
                    return false;
                }
            }

            public boolean rollRight() {
                if (isRollable(height, width + 1)) {
                    int temp = this.bottom;
                    this.bottom = this.right;
                    this.right = this.top;
                    this.top = this.left;
                    this.left = temp;

                    this.width++;

                    return true;
                } else {
                    return false;
                }
            }

            private boolean isRollable(int height, int width) {
                return height < board.boardHeight && width < board.boardWidth && height >= 0 && width >= 0;
            }

            public void setBottom(int bottom) {
                this.bottom = bottom;
            }
        }
    }
}
