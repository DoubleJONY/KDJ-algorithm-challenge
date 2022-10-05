package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

