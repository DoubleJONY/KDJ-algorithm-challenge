package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        int dx[] = {-1, 0, 1, 0};
        int dy[] = {0, -1, 0, 1};

        public void move(int R, int C) {
            if(direction == 0 || direction == 2) {
                speed %= (R - 1) * 2;
            } else {
                speed %= (C - 1) * 2;
            }

            for (int s = 0; s < speed; s++) {
                // 현재 r, c에 방향에 맞게 1칸씩 추가하며 위치 이동
                int newR = r + dx[direction];
                int newC = c + dy[direction];

                // 이동할 새로운 위치가 범위를 벗어나 벽에 부딪히면
                if(newR < 0 || newR >= R || newC < 0 || newC >= C) {
                    r -= dx[direction]; // 다시 값 돌려주고
                    c -= dy[direction];
                    direction = (direction + 2) % 4; // 방향 반대로
                    continue;
                }

                // 위치 벗어나지 않을때는 새로운 위치로 이동
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


