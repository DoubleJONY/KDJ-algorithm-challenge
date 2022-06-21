package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 낚시왕
 * https://www.acmicpc.net/problem/17143
 */
@RunWith(DataProviderRunner.class)
public class BJ17143 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "4 6 8",
                                "4 1 3 3 8",
                                "1 3 5 2 9",
                                "2 4 8 4 1",
                                "4 5 0 1 4",
                                "3 3 1 2 7",
                                "1 5 8 4 3",
                                "3 6 2 1 2",
                                "2 2 2 3 5"
                        },
                        "22"
                },
                {
                        new String[]{
                                "100 100 0"
                        },
                        "0"
                },
                {
                        new String[]{
                                "4 5 4",
                                "4 1 3 3 8",
                                "1 3 5 2 9",
                                "2 4 8 4 1",
                                "4 5 0 1 4",
                        },
                        "22"
                },
                {
                        new String[]{
                                "2 2 4",
                                "1 1 1 1 1",
                                "2 2 2 2 2",
                                "1 2 1 2 3",
                                "2 1 2 1 4"
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
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ17143.Main().solution(input), timer.stop());
    }


    public class Main {

        public String solution(String[] input) {

            int R = Integer.parseInt(input[0].split(" ")[0]);
            int C = Integer.parseInt(input[0].split(" ")[1]);
            int M = Integer.parseInt(input[0].split(" ")[2]);

            FishingPool fishingPool = new FishingPool(R, C, M);

            for (int i = 0; i < M; i++) {
                Shark shark = new Shark();
                shark.setIndex(i);
                shark.setR(Integer.parseInt(input[i + 1].split(" ")[0]) - 1);
                shark.setC(Integer.parseInt(input[i + 1].split(" ")[1]) - 1);
                shark.setSpeed(Integer.parseInt(input[i + 1].split(" ")[2]));

                int direction = Integer.parseInt(input[i + 1].split(" ")[3]);

                if (direction == 1) {
                    direction = 0;
                } else if (direction == 4) {
                    direction = 1;
                }
                shark.setDirection(direction);
                shark.setSize(Integer.parseInt(input[i + 1].split(" ")[4]));

                fishingPool.add(shark);
            }

            int answer = 0;

            for (int i = 0; i < C; i++) {
                answer += fishingPool.fishing(i);
                fishingPool.move();
            }

            return String.valueOf(answer);
        }

        class FishingPool {

            int R;
            int C;
            int M;

            List<Shark> sharks;

            public FishingPool(int R, int C, int M) {
                this.R = R;
                this.C = C;
                this.M = M;
                sharks = new ArrayList<>();
            }

            public void add(Shark shark) {
                sharks.add(shark);
            }

            public int fishing(int c) {

                List<Shark> targetSharks = new ArrayList<>();

                for (Shark shark : sharks) {
                    if (shark.getC() == c) {
                        targetSharks.add(shark);
                    }
                }
                if (targetSharks.size() == 0) {
                    return 0;
                }
                Shark targetShark = targetSharks.get(0);
                for (Shark shark : targetSharks) {
                    if (targetShark.getR() > shark.getR()) {
                        targetShark = shark;
                    }
                }

                int size = targetShark.getSize();
                sharks.remove(targetShark);
                return size;
            }

            public void move() {

                Map<String, Shark> duplicate = new HashMap<>();
                List<Shark> deadSharks = new ArrayList<>();
                for (Shark shark : sharks) {
                    shark.move(R, C);
                    String s = shark.getR() + "," + shark.getC();
                    if (duplicate.containsKey(s)){
                        Shark duplicateShark = duplicate.get(s);
                        if (duplicateShark.getSize() < shark.getSize()) {
                            duplicate.put(s, shark);
                            deadSharks.add(duplicateShark);
                        } else {
                            deadSharks.add(shark);
                        }
                    } else {
                        duplicate.put(s, shark);
                    }
                }
                for (Shark shark : deadSharks) {
                    sharks.remove(shark);
                }
            }
        }

        class Shark {
            int index;
            
            int r;
            int c;
            int speed;
            /**
             * direction is 0: up, 1: left, 2: down, 3: right
             */
            int direction;
            int size;

            int[] dx = {-1, 0, 1, 0};
            int[] dy = {0, -1, 0, 1};

            public void move(int R, int C) {
                if(direction == 0 || direction == 2) {
                    speed %= (R - 1) * 2;
                } else {
                    speed %= (C - 1) * 2;
                }

                for (int s = 0; s < speed; s++) {

                    int newR = r + dx[direction];
                    int newC = c + dy[direction];

                    if(newR < 0 || newR >= R || newC < 0 || newC >= C) {
                        r -= dx[direction];
                        c -= dy[direction];
                        direction = (direction + 2) % 4;
                        continue;
                    }

                    r = newR;
                    c = newC;
                }
            }

            public Shark() {

            }


            // PLEASE ALLOW LOMBOK GETTER AND SETTER
            public int getIndex() {

                return index;
            }

            public void setIndex(int index) {

                this.index = index;
            }

            public int getR() {

                return r;
            }

            public void setR(int r) {

                this.r = r;
            }

            public int getC() {

                return c;
            }

            public void setC(int c) {

                this.c = c;
            }

            public int getSpeed() {

                return speed;
            }

            public void setSpeed(int speed) {

                this.speed = speed;
            }

            public int getDirection() {

                return direction;
            }

            public void setDirection(int direction) {

                this.direction = direction;
            }

            public int getSize() {

                return size;
            }

            public void setSize(int size) {

                this.size = size;
            }
        }
    }
}

