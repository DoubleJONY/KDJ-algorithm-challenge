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

    int answer = 0;
    int SHARK = 99;

    Queue<SharkMap> queue;

    int[][] directions = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1},
            {-1, 1}};

    public String solution(String[] input) {

        int[][] map = new int[4][4];
        int[][] directionMap = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                map[i][j] = Integer.parseInt(input[i].split(" ")[j * 2]);
                directionMap[i][j] = Integer.parseInt(input[i].split(" ")[(j * 2) + 1]) - 1;
            }
        }

        answer = map[0][0];
        map[0][0] = SHARK;

        queue = new LinkedList<SharkMap>();
        queue.add(new SharkMap(answer, map, directionMap));

        while (!queue.isEmpty()) {
            bfs(queue.poll());
        }

        return String.valueOf(answer);
    }

    private void bfs(SharkMap sharkMap) {

        moveFishes(sharkMap);
        moveShark(sharkMap);

        answer = Math.max(answer, sharkMap.score);
    }

    private void moveShark(SharkMap sharkMap) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (sharkMap.map[i][j] == SHARK) {
                    int dx = directions[sharkMap.directionMap[i][j]][0];
                    int dy = directions[sharkMap.directionMap[i][j]][1];
                    while (true) {
                        try {
                            if (sharkMap.map[i + dx][j + dy] > 0 && sharkMap.map[i + dx][j + dy] <= 16) {

                                int[][] newMap = new int[4][4];
                                for (int k = 0; k < 4; k++) {
                                    System.arraycopy(sharkMap.map[k], 0, newMap[k], 0, 4);
                                }
                                int[][] newDirectionMap = new int[4][4];
                                for (int k = 0; k < 4; k++) {
                                    System.arraycopy(sharkMap.directionMap[k], 0, newDirectionMap[k], 0, 4);
                                }

                                SharkMap newSharkMap = new SharkMap(sharkMap.score, newMap, newDirectionMap);
                                newSharkMap.score += newSharkMap.map[i + dx][j + dy];
                                newSharkMap.map[i + dx][j + dy] = SHARK;
                                newSharkMap.map[i][j] = -1;
                                newSharkMap.directionMap[i][j] = -1;
                                queue.add(newSharkMap);
                            }
                            if (dx != 0) {
                                dx = dx + (dx / Math.abs(dx));
                            }
                            if (dy != 0) {
                                dy = dy + (dy / Math.abs(dy));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void moveFishes(SharkMap sharkMap) {

        for (int i = 1; i <= 16; i++) {
            boolean doNext = false;
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (sharkMap.map[j][k] == i) {
                        doNext = true;
                        int origin = sharkMap.directionMap[j][k];
                        while (true) {
                            int dx = directions[sharkMap.directionMap[j][k]][0];
                            int dy = directions[sharkMap.directionMap[j][k]][1];
                            try {
                                if (sharkMap.map[j + dx][k + dy] != SHARK) {
                                    int temp = sharkMap.map[j + dx][k + dy];
                                    sharkMap.map[j + dx][k + dy] = sharkMap.map[j][k];
                                    sharkMap.map[j][k] = temp;

                                    temp = sharkMap.directionMap[j + dx][k + dy];
                                    sharkMap.directionMap[j + dx][k + dy] = sharkMap.directionMap[j][k];
                                    sharkMap.directionMap[j][k] = temp;

                                    break;
                                }
                            } catch (IndexOutOfBoundsException ignored) {

                            }
                            sharkMap.directionMap[j][k] += 1;
                            if (sharkMap.directionMap[j][k] >= 8) {
                                sharkMap.directionMap[j][k] = 0;
                            }
                            if (sharkMap.directionMap[j][k] == origin) {
                                break;
                            }
                        }
                    }
                    if (doNext) {
                        break;
                    }
                }
                if (doNext) {
                    break;
                }
            }
        }
    }

    class SharkMap {

        int score;
        int[][] map;
        int[][] directionMap;

        public SharkMap(int moveCount, int[][] map, int[][] directionMap) {

            this.score = moveCount;
            this.map = map;
            this.directionMap = directionMap;
        }
    }
}


