package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 컨베이어 벨트 위의 로봇
 * https://www.acmicpc.net/problem/20055
 */
@RunWith(DataProviderRunner.class)
public class BJ20055 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][] {
                {
                        new String[] {
                                "3 2",
                                "1 2 1 2 1 2"
                        },
                        "2"
                },
                {
                        new String[] {
                                "3 6",
                                "10 10 10 10 10 10"
                        },
                        "31"
                },
                {
                        new String[] {
                                "4 5",
                                "10 1 10 6 3 4 8 2"
                        },
                        "24"
                },
                {
                        new String[] {
                                "5 8",
                                "100 99 60 80 30 20 10 89 99 100"
                        },
                        "472"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ20055.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int brokenCount = 0;

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);
            int K = Integer.parseInt(input[0].split(" ")[1]);

            LinkedList<BeltSpace> belt = new LinkedList<>();
            LinkedList<BeltSpace> underBelt = new LinkedList<>();
            for (int i = 0; i < 2 * N; i++) {
                int hp = Integer.parseInt(input[1].split(" ")[i]);
                if (i < N) {
                    belt.add(new BeltSpace(false, hp));
                } else {
                    underBelt.add(new BeltSpace(false, hp));
                }
                if (hp == 0) {
                    brokenCount++;
                }
            }

            int step = 0;
            while (brokenCount < K) {
                step++;
                rotate(belt, underBelt);
                move(belt);
                if (belt.getFirst().hp > 0) {
                    belt.getFirst().load();
                }
            }
            return String.valueOf(step);
        }

        private void move(LinkedList<BeltSpace> belt) {

            for (int i = belt.size() - 2; i >= 0; i--) {
                BeltSpace beltSpace = belt.get(i);
                if (beltSpace.hasRobot) {
                    BeltSpace nextBeltSpace = belt.get(i + 1);
                    if (nextBeltSpace.hp > 0 && !nextBeltSpace.hasRobot) {
                        beltSpace.land();
                        nextBeltSpace.load();
                        belt.getLast().land();
                    }
                }
            }
        }

        private void rotate(LinkedList<BeltSpace> belt, LinkedList<BeltSpace> underBelt) {

            underBelt.addFirst(belt.pollLast());
            belt.addFirst(underBelt.pollLast());
            belt.getLast().land();
        }

        class BeltSpace {

            boolean hasRobot;
            int     hp;

            public BeltSpace(boolean hasRobot, int hp) {

                this.hasRobot = hasRobot;
                this.hp = hp;
            }

            public void load() {

                this.hasRobot = true;
                this.hp--;

                if (this.hp == 0) {
                    brokenCount++;
                }
            }

            public void land() {

                this.hasRobot = false;
            }
        }
    }
}

