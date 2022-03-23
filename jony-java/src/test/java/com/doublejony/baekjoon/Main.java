package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    int[] DeltaRow = new int[]{-1, 0, 1, 0};
    int[] DeltaColumn = new int[]{0, 1, 0, -1};

    int h;
    int w;
    int r;
    int c;
    int direction;

    int turnCount = 0;

    int[][] map;


    public String solution(String[] input) {

        h = Integer.parseInt(input[0].split(" ")[0]);
        w = Integer.parseInt(input[0].split(" ")[1]);
        r = Integer.parseInt(input[1].split(" ")[0]);
        c = Integer.parseInt(input[1].split(" ")[1]);
        direction = Integer.parseInt(input[1].split(" ")[2]);

        map = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map[i][j] = Integer.parseInt(input[i+2].split(" ")[j]);
            }
        }

        return String.valueOf(startVacuum());
    }

    private int startVacuum() {

        int answer = 0;

        while (true) {
            if (map[r][c] == 0) {
                map[r][c] = 2;
                answer++;
            }

            if (turnCount >= 4) {
                if (map[r - DeltaRow[direction]][c - DeltaColumn[direction]] == 1) {
                    break;
                } else {
                    r -= DeltaRow[direction];
                    c -= DeltaColumn[direction];
                    turnCount = 0;
                }
            } else {
                turnLeft();
                turnCount++;

                if (map[r + DeltaRow[direction]][c + DeltaColumn[direction]] == 0) {
                    r += DeltaRow[direction];
                    c += DeltaColumn[direction];
                    turnCount = 0;
                }
            }
        }

        return answer;
    }

    private void turnLeft() {
        direction--;
        if (direction < 0) {
            direction = 3;
        }
    }
}

