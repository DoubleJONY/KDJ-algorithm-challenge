package com.doublejony.backjun.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 구슬 탈출 2
 * 문제
 * 스타트링크에서 판매하는 어린이용 장난감 중에서 가장 인기가 많은 제품은 구슬 탈출이다. 구슬 탈출은 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임이다.
 *
 * 보드의 세로 크기는 N, 가로 크기는 M이고, 편의상 1×1크기의 칸으로 나누어져 있다. 가장 바깥 행과 열은 모두 막혀져 있고, 보드에는 구멍이 하나 있다. 빨간 구슬과 파란 구슬의 크기는 보드에서 1×1크기의 칸을 가득 채우는 사이즈이고, 각각 하나씩 들어가 있다. 게임의 목표는 빨간 구슬을 구멍을 통해서 빼내는 것이다. 이때, 파란 구슬이 구멍에 들어가면 안 된다.
 *
 * 이때, 구슬을 손으로 건드릴 수는 없고, 중력을 이용해서 이리 저리 굴려야 한다. 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기와 같은 네 가지 동작이 가능하다.
 *
 * 각각의 동작에서 공은 동시에 움직인다. 빨간 구슬이 구멍에 빠지면 성공이지만, 파란 구슬이 구멍에 빠지면 실패이다. 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패이다. 빨간 구슬과 파란 구슬은 동시에 같은 칸에 있을 수 없다. 또, 빨간 구슬과 파란 구슬의 크기는 한 칸을 모두 차지한다. 기울이는 동작을 그만하는 것은 더 이상 구슬이 움직이지 않을 때 까지이다.
 *
 * 보드의 상태가 주어졌을 때, 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫 번째 줄에는 보드의 세로, 가로 크기를 의미하는 두 정수 N, M (3 ≤ N, M ≤ 10)이 주어진다. 다음 N개의 줄에 보드의 모양을 나타내는 길이 M의 문자열이 주어진다. 이 문자열은 '.', '#', 'O', 'R', 'B' 로 이루어져 있다. '.'은 빈 칸을 의미하고, '#'은 공이 이동할 수 없는 장애물 또는 벽을 의미하며, 'O'는 구멍의 위치를 의미한다. 'R'은 빨간 구슬의 위치, 'B'는 파란 구슬의 위치이다.
 *
 * 입력되는 모든 보드의 가장자리에는 모두 '#'이 있다. 구멍의 개수는 한 개 이며, 빨간 구슬과 파란 구슬은 항상 1개가 주어진다.
 *
 * 출력
 * 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 출력한다. 만약, 10번 이하로 움직여서 빨간 구슬을 구멍을 통해 빼낼 수 없으면 -1을 출력한다.
 */
@RunWith(DataProviderRunner.class)
public class BJ13460 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "5 5",
                                "#####",
                                "#..B#",
                                "#.#.#",
                                "#RO.#",
                                "#####"
                        },
                        "1"
                },
                {
                        new String[]{
                                "7 7",
                                "#######",
                                "#...RB#",
                                "#.#####",
                                "#.....#",
                                "#####.#",
                                "#O....#",
                                "#######"
                        },
                        "5"
                },
                {
                        new String[]{
                                "7 7",
                                "#######",
                                "#..R#B#",
                                "#.#####",
                                "#.....#",
                                "#####.#",
                                "#O....#",
                                "#######"
                        },
                        "5"
                },
                {
                        new String[]{
                                "10 10",
                                "##########",
                                "#R#...##B#",
                                "#...#.##.#",
                                "#####.##.#",
                                "#......#.#",
                                "#.######.#",
                                "#.#....#.#",
                                "#.#.#.#..#",
                                "#...#.O#.#",
                                "##########"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "3 7",
                                "#######",
                                "#R.O.B#",
                                "#######"
                        },
                        "1"
                },
                {
                        new String[]{
                                "10 10",
                                "##########",
                                "#R#...##B#",
                                "#...#.##.#",
                                "#####.##.#",
                                "#......#.#",
                                "#.######.#",
                                "#.#....#.#",
                                "#.#.##...#",
                                "#O..#....#",
                                "##########"
                        },
                        "7"
                },
                {
                        new String[]{
                                "3 10",
                                "##########",
                                "#.O....RB#",
                                "##########"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "3 6",
                                "######",
                                "#.ORB#",
                                "######"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "8 8",
                                "########",
                                "#.####.#",
                                "#...#B##",
                                "#.##.R.#",
                                "######.#",
                                "##.##O.#",
                                "###.##.#",
                                "########"
                        },
                        "7"
                },
                {
                        new String[]{
                                "4 6",
                                "######",
                                "#R####",
                                "#B..O#",
                                "######"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "4 6",
                                "######",
                                "#R#O##",
                                "#B...#",
                                "######"
                        },
                        "4"
                },
                {
                        new String[]{
                                "11 13",
                                "#############",
                                "#RB##########",
                                "#.#.........#",
                                "#.#.#######.#",
                                "#.#.#.....#.#",
                                "#.#.#..O#.#.#",
                                "#.#.#####.#.#",
                                "#.#.......#.#",
                                "#.#########.#",
                                "#...........#",
                                "#############"
                        },
                        "10"
                },
                {
                        new String[]{
                                "11 13",
                                "#############",
                                "#R###########",
                                "#B#.........#",
                                "#.#.#######.#",
                                "#.#.#.....#.#",
                                "#.#.#..O#.#.#",
                                "#.#.#####.#.#",
                                "#.#.......#.#",
                                "#.#########.#",
                                "#...........#",
                                "#############"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "10 9",
                                "#########",
                                "#R...B#.#",
                                "#.....#.#",
                                "#.......#",
                                "##.#....#",
                                "#....#..#",
                                "#.#....O#",
                                "##.....##",
                                "#...##..#",
                                "#########"
                        },
                        "4"
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

        Point red;
        Point blue;
        Point goal;
        String[] map;
        int minDepth = 11;

        public String solution(String[] input) {

            String[] size = input[0].split(" ");
            map = new String[Integer.parseInt(size[0])];

            for (int i = 0; i < Integer.parseInt(size[0]); i++) {
                map[i] = input[i + 1];
            }

            initPoints(map);
            moveOrb("", 0, this.red, this.blue);

            return Integer.toString(minDepth == 11 ? -1 : minDepth);
        }

        private void moveOrb(String direction, int depth, Point r, Point b) {

            Point red = new Point(r.height, r.width);
            Point blue = new Point(b.height, b.width);

            if (!direction.equals("")) {
                depth++;

                if (depth > 10) {
                    return;
                }

                Point orbA;
                Point orbB;

                switch (direction) {
                    case "down":
                        if (red.height > blue.height) {
                            orbA = red;
                            orbB = blue;
                        } else {
                            orbA = blue;
                            orbB = red;
                        }
                        while (true) {
                            if (move(orbA, orbB, orbA.height + 1, orbA.width)) {
                                break;
                            }
                        }
                        while (true) {
                            if (move(orbB, orbA, orbB.height + 1, orbB.width)) {
                                break;
                            }
                        }
                        break;
                    case "up":
                        if (red.height < blue.height) {
                            orbA = red;
                            orbB = blue;
                        } else {
                            orbA = blue;
                            orbB = red;
                        }
                        while (true) {
                            if (move(orbA, orbB, orbA.height - 1, orbA.width)) {
                                break;
                            }
                        }
                        while (true) {
                            if (move(orbB, orbA, orbB.height - 1, orbB.width)) {
                                break;
                            }
                        }
                        break;
                    case "left":
                        if (red.width < blue.width) {
                            orbA = red;
                            orbB = blue;
                        } else {
                            orbA = blue;
                            orbB = red;
                        }
                        while (true) {
                            if (move(orbA, orbB, orbA.height, orbA.width - 1)) {
                                break;
                            }
                        }
                        while (true) {
                            if (move(orbB, orbA, orbB.height, orbB.width - 1)) {
                                break;
                            }
                        }
                        break;
                    case "right":
                        if (red.width > blue.width) {
                            orbA = red;
                            orbB = blue;
                        } else {
                            orbA = blue;
                            orbB = red;
                        }
                        while (true) {
                            if (move(orbA, orbB, orbA.height, orbA.width + 1)) {
                                break;
                            }
                        }
                        while (true) {
                            if (move(orbB, orbA, orbB.height, orbB.width + 1)) {
                                break;
                            }
                        }
                        break;
                }
            }

            if (isGoal(red)) {
                if (!isGoal(blue) && depth < minDepth) {
                    this.minDepth = depth;
                }
                return;
            }

            if ((map[red.height + 1].charAt(red.width) != '#' || map[blue.height + 1].charAt(blue.width) != '#')
                    && !direction.equals("up")) {
                moveOrb("down", depth, red, blue);
            }
            if ((map[red.height - 1].charAt(red.width) != '#' || map[blue.height - 1].charAt(blue.width) != '#')
                    && !direction.equals("down")) {
                moveOrb("up", depth, red, blue);
            }
            if ((map[red.height].charAt(red.width - 1) != '#' || map[blue.height].charAt(blue.width - 1) != '#')
                    && !direction.equals("right")) {
                moveOrb("left", depth, red, blue);
            }
            if ((map[red.height].charAt(red.width + 1) != '#' || map[blue.height].charAt(blue.width + 1) != '#')
                    && !direction.equals("left")) {
                moveOrb("right", depth, red, blue);
            }

        }

        private boolean move(Point orb, Point oppOrb, int tHeight, int tWidth) {
            if (isStuck(tHeight, tWidth, oppOrb)) {
                return true;
            }
            if (map[tHeight].charAt(tWidth) != '#') {
                orb.setPoint(tHeight, tWidth);
                return isGoal(orb);
            } else {
                return true;
            }
        }

        private boolean isGoal(Point orb) {
            return orb.height == this.goal.height && orb.width == this.goal.width;
        }

        private boolean isStuck(int tHeight, int tWidth, Point oppOrb) {
            return tHeight == oppOrb.height && tWidth == oppOrb.width && !isGoal(oppOrb);
        }

        private void initPoints(String[] map) {
            for (int i = 0; i < map.length; i++) {
                if (map[i].contains("R")) {
                    this.red = new Point(i, map[i].indexOf("R"));
                }
                if (map[i].contains("B")) {
                    this.blue = new Point(i, map[i].indexOf("B"));
                }
                if (map[i].contains("O")) {
                    this.goal = new Point(i, map[i].indexOf("O"));
                }
            }
        }

        class Point {
            int height;
            int width;

            public Point(int height, int width) {
                this.height = height;
                this.width = width;
            }

            public void setPoint(int height, int width) {
                this.height = height;
                this.width = width;
            }
        }
    }
}
