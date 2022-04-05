package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 톱니바퀴
 * 총 8개의 톱니를 가지고 있는 톱니바퀴 4개가 아래 그림과 같이 일렬로 놓여져 있다. 또, 톱니는 N극 또는 S극 중 하나를 나타내고 있다. 톱니바퀴에는 번호가 매겨져 있는데, 가장 왼쪽 톱니바퀴가 1번, 그 오른쪽은 2번, 그 오른쪽은 3번, 가장 오른쪽 톱니바퀴는 4번이다.
 * <p>
 * 이때, 톱니바퀴를 총 K번 회전시키려고 한다. 톱니바퀴의 회전은 한 칸을 기준으로 한다. 회전은 시계 방향과 반시계 방향이 있고, 아래 그림과 같이 회전한다.
 * <p>
 * 톱니바퀴를 회전시키려면, 회전시킬 톱니바퀴와 회전시킬 방향을 결정해야 한다. 톱니바퀴가 회전할 때, 서로 맞닿은 극에 따라서 옆에 있는 톱니바퀴를 회전시킬 수도 있고, 회전시키지 않을 수도 있다. 톱니바퀴 A를 회전할 때, 그 옆에 있는 톱니바퀴 B와 서로 맞닿은 톱니의 극이 다르다면, B는 A가 회전한 방향과 반대방향으로 회전하게 된다. 예를 들어, 아래와 같은 경우를 살펴보자.
 * <p>
 * 두 톱니바퀴의 맞닿은 부분은 초록색 점선으로 묶여있는 부분이다. 여기서, 3번 톱니바퀴를 반시계 방향으로 회전했다면, 4번 톱니바퀴는 시계 방향으로 회전하게 된다. 2번 톱니바퀴는 맞닿은 부분이 S극으로 서로 같기 때문에, 회전하지 않게 되고, 1번 톱니바퀴는 2번이 회전하지 않았기 때문에, 회전하지 않게 된다. 따라서, 아래 그림과 같은 모양을 만들게 된다.
 * <p>
 * 위와 같은 상태에서 1번 톱니바퀴를 시계 방향으로 회전시키면, 2번 톱니바퀴가 반시계 방향으로 회전하게 되고, 2번이 회전하기 때문에, 3번도 동시에 시계 방향으로 회전하게 된다. 4번은 3번이 회전하지만, 맞닿은 극이 같기 때문에 회전하지 않는다. 따라서, 아래와 같은 상태가 된다.
 * <p>
 * 톱니바퀴의 초기 상태와 톱니바퀴를 회전시킨 방법이 주어졌을 때, 최종 톱니바퀴의 상태를 구하는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 첫째 줄에 1번 톱니바퀴의 상태, 둘째 줄에 2번 톱니바퀴의 상태, 셋째 줄에 3번 톱니바퀴의 상태, 넷째 줄에 4번 톱니바퀴의 상태가 주어진다. 상태는 8개의 정수로 이루어져 있고, 12시방향부터 시계방향 순서대로 주어진다. N극은 0, S극은 1로 나타나있다.
 * <p>
 * 다섯째 줄에는 회전 횟수 K(1 ≤ K ≤ 100)가 주어진다. 다음 K개 줄에는 회전시킨 방법이 순서대로 주어진다. 각 방법은 두 개의 정수로 이루어져 있고, 첫 번째 정수는 회전시킨 톱니바퀴의 번호, 두 번째 정수는 방향이다. 방향이 1인 경우는 시계 방향이고, -1인 경우는 반시계 방향이다.
 * <p>
 * 출력
 * 총 K번 회전시킨 이후에 네 톱니바퀴의 점수의 합을 출력한다. 점수란 다음과 같이 계산한다.
 * <p>
 * 1번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 1점
 * 2번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 2점
 * 3번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 4점
 * 4번 톱니바퀴의 12시방향이 N극이면 0점, S극이면 8점
 */
@RunWith(DataProviderRunner.class)
public class BJ14891 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "10101111",
                                "01111101",
                                "11001110",
                                "00000010",
                                "2",
                                "3 -1",
                                "1 1"
                        },
                        "7"
                },
                {
                        new String[]{
                                "11111111",
                                "11111111",
                                "11111111",
                                "11111111",
                                "3",
                                "1 1",
                                "2 1",
                                "3 1"
                        },
                        "15"
                },
                {
                        new String[]{
                                "10001011",
                                "10000011",
                                "01011011",
                                "00111101",
                                "5",
                                "1 1",
                                "2 1",
                                "3 1",
                                "4 1",
                                "1 -1"
                        },
                        "6"
                },
                {
                        new String[]{
                                "10010011",
                                "01010011",
                                "11100011",
                                "01010101",
                                "8",
                                "1 1",
                                "2 1",
                                "3 1",
                                "4 1",
                                "1 -1",
                                "2 -1",
                                "3 -1",
                                "4 -1"
                        },
                        "5"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ14891.Main().solution(input), timer.stop());
    }


    public class Main {

        List<List<Integer>> gears = new ArrayList<>();
        Queue<Command> mainQueue = new LinkedList<>();

        private class Command {
            int commandGears;
            int commandWises;

            public Command(int commandGears, int commandWises) {
                this.commandGears = commandGears;
                this.commandWises = commandWises;
            }

            public int getCommandGears() {
                return commandGears;
            }

            public int getCommandWises() {
                return commandWises;
            }
        }

        public String solution(String[] input) {

            for (int i = 0; i < 4; i++) {
                gears.add(new ArrayList<>());
            }

            for (int i = 0; i < 4; i++) {
                String[] a = input[i].split("");
                for (int j = 0; j < 8; j++) {
                    gears.get(i).add(j, Integer.parseInt(a[j]));
                }
            }

            int commandCount = Integer.parseInt(input[4]);

            for (int i = 0; i < commandCount; i++) {
                mainQueue.add(new Command(
                        Integer.parseInt(input[5 + i].split(" ")[0]) - 1 ,
                        Integer.parseInt(input[5 + i].split(" ")[1]))
                );
            }

            run();

            int result = 0;
            for (int i = 0; i < 4; i++) {
                result += gears.get(i).get(0) == 1 ? Math.pow(2, i) : 0;
            }

            return String.valueOf(result);
        }

        private void run() {

            while (!mainQueue.isEmpty()) {
                Queue<Command> subQueue = new LinkedList<>();
                subQueue.add(mainQueue.poll());

                Command cCommand = subQueue.peek();

                //lower case
                queueUnderGears(subQueue, cCommand);

                //upper case
                queueUpperGears(subQueue, cCommand);

                while (!subQueue.isEmpty()) {
                    Command c = subQueue.poll();
                    gears.set(c.commandGears, rotate(gears.get(c.commandGears), c.commandWises));
                }
            }
        }

        private void queueUpperGears(Queue<Command> subQueue, Command cCommand) {
            int cGear = cCommand.getCommandGears();
            int cWise = cCommand.getCommandWises();

            while (cGear + 1 < 4) {
                if (gears.get(cGear).get(2) != gears.get(cGear + 1).get(6)) {
                    subQueue.add(new Command(cGear + 1, cWise * -1));
                    cGear += 1;
                    cWise = cWise * -1;
                } else {
                    break;
                }
            }
        }

        private void queueUnderGears(Queue<Command> subQueue, Command cCommand) {
            int cGear = cCommand.getCommandGears();
            int cWise = cCommand.getCommandWises();

            while (cGear - 1 >= 0) {
                if (gears.get(cGear).get(6) != gears.get(cGear - 1).get(2)) {
                    subQueue.add(new Command(cGear - 1, cWise * -1));
                    cGear -= 1;
                    cWise = cWise * -1;
                } else {
                    break;
                }
            }
        }

        private List<Integer> rotate(List<Integer> integers, int commandWises) {
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                result.add(integers.get((i + (commandWises * -1) + 8) % 8));
            }

            return result;
        }
    }

}

