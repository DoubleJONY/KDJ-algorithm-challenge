package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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

    int N;
    int[][] A;
    int sum;
    int min;

    public String solution(String[] input) {

        sum = 0;
        min = Integer.MAX_VALUE;

        N = Integer.parseInt(input[0]);

        A = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(input[1 + i].split(" ")[j]);
                sum += A[i][j];
            }
        }

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                for (int d1 = 1; d1 < N; d1++) {
                    for (int d2 = 1; d2 < N; d2++) {
                        if (x + d1 + d2 >= N || y - d1 < 0 || y + d2 >= N) {
                            continue;
                        }

                        min = Math.min(min, dfs(x, y, d1, d2));
                    }
                }
            }
        }

        return String.valueOf(min);
    }

    private int dfs(int x, int y, int d1, int d2) {
        boolean[][] border = new boolean[N][N];

        for (int i = 0; i <= d1; i++) {
            border[x + i][y - i] = true;
            border[x + d2 + i][y + d2 - i] = true;
        }

        for (int i = 0; i <= d2; i++) {
            border[x + i][y + i] = true;
            border[x + d1 + i][y - d1 + i] = true;
        }

        int[] peoplesByArea = new int[5];

        // area 1
        for (int i = 0; i < x + d1; i++) {
            for (int j = 0; j <= y; j++) {
                if (border[i][j]) {
                    break;
                }
                peoplesByArea[0] += A[i][j];
            }
        }

        // area 2
        for (int i = 0; i <= x + d2; i++) {
            for (int j = N - 1; j > y; j--) {
                if (border[i][j]) {
                    break;
                }
                peoplesByArea[1] += A[i][j];
            }
        }

        // area 3
        for (int i = x + d1; i < N; i++) {
            for (int j = 0; j < y - d1 + d2; j++) {
                if (border[i][j]) {
                    break;
                }
                peoplesByArea[2] += A[i][j];
            }
        }

        // area 4
        for (int i = x + d2 + 1; i < N; i++) {
            for (int j = N - 1; j >= y - d1 + d2; j--) {
                if (border[i][j]) {
                    break;
                }
                peoplesByArea[3] += A[i][j];
            }
        }

        // area 5
        peoplesByArea[4] = sum;

        for (int i = 0; i < 4; i++) {
            peoplesByArea[4] -= peoplesByArea[i];
        }

        Arrays.sort(peoplesByArea);
        return peoplesByArea[4] - peoplesByArea[0];
    }

}


