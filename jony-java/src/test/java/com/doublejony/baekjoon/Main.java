package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> input = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp;
        while ((temp = br.readLine()) != null && !temp.isEmpty()) {
            input.add(temp);
        }

        String answer = new Main().solution(input.toArray(new String[input.size()]));

        System.out.println(answer);
    }


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

