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

    int m;
    int n;
    int[][] map;

    int maxValue;

    class Virus {
        int x, y;

        Virus(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public String solution(String[] input) {

        String[] a = input[0].split(" ");

        m = Integer.parseInt(a[0]);
        n = Integer.parseInt(a[1]);

        map = new int[m][n];

        maxValue = 0;

        for (int i = 0; i < m; i++) {
            String[] line = input[i + 1].split(" ");
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(line[j]);
            }
        }

        buildWall(0);

        return Integer.toString(maxValue);
    }

    private void buildWall(int depth) {
        if (depth >= 3) {
            spreadVirus();
            return;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] == 0) {
                    map[i][j] = 1;
                    buildWall(depth + 1);
                    map[i][j] = 0;
                }
            }
        }
    }

    private void spreadVirus() {
        int[][] virusMap = new int[m][n];
        Queue<Virus> queue = new LinkedList<Virus>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                virusMap[i][j] = map[i][j];
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                if (virusMap[i][j] == 2) {
                    queue.add(new Virus(i, j));
                }
        }

        while (!queue.isEmpty()) {
            Virus virus = queue.remove();

            if (virus.x + 1 < m && virusMap[virus.x + 1][virus.y] == 0) {
                virusMap[virus.x + 1][virus.y] = 2;
                queue.add(new Virus(virus.x + 1, virus.y));
            }

            if (virus.x - 1 >= 0 && virusMap[virus.x - 1][virus.y] == 0) {
                virusMap[virus.x - 1][virus.y] = 2;
                queue.add(new Virus(virus.x - 1, virus.y));
            }

            if (virus.y + 1 < n && virusMap[virus.x][virus.y + 1] == 0) {
                virusMap[virus.x][virus.y + 1] = 2;
                queue.add(new Virus(virus.x, virus.y + 1));
            }

            if (virus.y - 1 >= 0 && virusMap[virus.x][virus.y - 1] == 0) {
                virusMap[virus.x][virus.y - 1] = 2;
                queue.add(new Virus(virus.x, virus.y - 1));
            }

        }
        countSafeArea(virusMap);
    }

    private void countSafeArea(int[][] virusMap) {
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (virusMap[i][j] == 0) {
                    count++;
                }
            }
        }

        maxValue = Math.max(count, maxValue);
    }
}

