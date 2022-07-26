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
 * 새로운 게임 2
 * https://www.acmicpc.net/problem/17837
 */
@RunWith(DataProviderRunner.class)
public class BJ17837 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "4 4",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "1 1 1",
                                "1 2 1",
                                "1 3 1",
                                "3 3 3"
                        },
                        "2"
                },
                {
                        new String[]{
                                "6 10",
                                "0 1 2 0 1 1",
                                "1 2 0 1 1 0",
                                "2 1 0 1 1 0",
                                "1 0 1 1 0 2",
                                "2 0 1 2 0 1",
                                "0 2 1 0 2 1",
                                "1 1 1",
                                "2 2 2",
                                "3 3 4",
                                "4 4 1",
                                "5 5 3",
                                "6 6 2",
                                "1 6 3",
                                "6 1 2",
                                "2 4 3",
                                "4 2 1"
                        },
                        "7"
                },
                {
                        new String[]{
                                "4 4",
                                "0 0 2 0",
                                "0 0 1 0",
                                "0 0 1 2",
                                "0 2 0 0",
                                "2 1 1",
                                "3 2 3",
                                "2 2 1",
                                "4 1 2"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "4 4",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "1 1 1",
                                "1 2 1",
                                "1 3 1",
                                "1 4 1"
                        },
                        "1"
                },
                {
                        new String[]{
                                "4 4",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "1 1 1",
                                "1 2 1",
                                "1 3 1",
                                "2 4 3"
                        },
                        "1"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ17837.Main().solution(input), timer.stop());
    }


    public class Main {

        int N;
        int[][] map;

        int WHITE = 0;
        int RED = 1;
        int BLUE = 2;

        int RIGHT = 1;
        int LEFT = 2;
        int UP = 3;
        int DOWN = 4;

        public String solution(String[] input) {

            N = Integer.parseInt(input[0].split(" ")[0]);
            int K = Integer.parseInt(input[0].split(" ")[1]);

            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(input[i+1].split(" ")[j]);
                }
            }

            List<Unit> units = new ArrayList<>();

            for (int i = 0; i < K; i++) {
                int x = Integer.parseInt(input[i+N+1].split(" ")[0])-1;
                int y = Integer.parseInt(input[i+N+1].split(" ")[1])-1;
                int d = Integer.parseInt(input[i+N+1].split(" ")[2]);
                units.add(new Unit(i, x, y, d));
            }

            for (int i = 1; i <= 1000; i++) {
                for (Unit unit : units) {
                    unit.removeParent(units);
                    unit.move();
                    for (Unit other : units) {
                        if (unit.x == other.x && unit.y == other.y && unit.id != other.id && !unit.isChild(other) && !other.isChild(unit)) {
                            other.stackUnit(unit);
                            break;
                        }
                    }
                    //RED
                    if (map[unit.x][unit.y] == RED) {
                        unit.reverseStack();
                    }
                }

                for (Unit unit : units) {
                    if (unit.countChilds(1) >= 4) {
                        return String.valueOf(i);
                    }
                }
            }

            return "-1";
        }

        private class Unit {

            int id;
            int x;
            int y;
            int direction;
            Unit childUnit;

            public Unit(int id, int x, int y, int direction) {
                this.id = id;
                this.x = x;
                this.y = y;
                this.direction = direction;
            }

            public void stackUnit(Unit unit) {
                if (this.childUnit != null) {
                    this.childUnit.stackUnit(unit);
                } else {
                    this.childUnit = unit;
                }
            }

            public void move() {
                if (this.direction == RIGHT) {
                    this.y++;
                } else if (this.direction == LEFT) {
                    this.y--;
                } else if (this.direction == UP) {
                    this.x--;
                } else if (this.direction == DOWN) {
                    this.x++;
                }

                //BLUE or outBorder
                if (this.x < 0 || this.x >= N || this.y < 0 || this.y >= N || map[this.x][this.y] == BLUE) {
                    reverseDirection();
                    if (this.direction == RIGHT && map[this.x][this.y + 1] != BLUE) {
                        this.y++;
                    } else if (this.direction == LEFT && map[this.x][this.y - 1] != BLUE) {
                        this.y--;
                    } else if (this.direction == UP && map[this.x - 1][this.y] != BLUE) {
                        this.x--;
                    } else if (this.direction == DOWN && map[this.x + 1][this.y] != BLUE) {
                        this.x++;
                    }
                }

                //MOVE CHILDs
                if (this.childUnit != null) {
                    this.childUnit.moveChild(this);
                }
            }

            public void reverseStack() {
                List<Unit> stack = new ArrayList<>();
                getReverseStack(stack);
                Queue<Unit> queue = new LinkedList<>(stack);;
                setReverseStack(queue);
            }

            private void setReverseStack(Queue<Unit> queue) {
                Unit unit = queue.poll();
                while (!queue.isEmpty()) {
                    unit.childUnit = queue.poll();
                    unit = unit.childUnit;
                }
            }

            private void getReverseStack(List<Unit> stack) {
                if (this.childUnit != null) {
                    this.childUnit.getReverseStack(stack);
                }
                this.childUnit = null;
                stack.add(this);
            }

            private void moveChild(Unit parent) {
                this.x = parent.x;
                this.y = parent.y;
                if (this.childUnit != null) {
                    this.childUnit.moveChild(this);
                }
            }

            private void reverseDirection() {
                if (this.direction == RIGHT) {
                    this.direction = LEFT;
                } else if (this.direction == LEFT) {
                    this.direction = RIGHT;
                } else if (this.direction == UP) {
                    this.direction = DOWN;
                } else if (this.direction == DOWN) {
                    this.direction = UP;
                }
            }

            public int countChilds(int count) {
                if (this.childUnit != null) {
                    return this.childUnit.countChilds(count + 1);
                }
                return count;
            }

            public void removeParent(List<Unit> units) {
                for (Unit parent : units) {
                    if (parent.childUnit != null && parent.childUnit.id == this.id) {
                        parent.childUnit = null;
                        break;
                    }
                }
            }

            public boolean isChild(Unit other) {
                if (this.childUnit != null) {
                    if (this.childUnit.id == other.id) {
                        return true;
                    }
                    return this.childUnit.isChild(other);
                }
                return false;
            }
        }
    }
}

