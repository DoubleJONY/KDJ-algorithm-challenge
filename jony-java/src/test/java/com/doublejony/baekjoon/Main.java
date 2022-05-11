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

    int N;
    int M;

    List<Point> residents;
    List<Point> chickens;

    boolean[] chickenToggle;
    int answer;

    public String solution(String[] input) {

        N = Integer.parseInt(input[0].split(" ")[0]);
        M = Integer.parseInt(input[0].split(" ")[1]);

        residents = new ArrayList<>();
        chickens = new ArrayList<>();

        answer = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int a = Integer.parseInt(input[i + 1].split(" ")[j]);

                if (a == 1) {
                    residents.add(new Point(i, j));
                } else if (a == 2) {
                    chickens.add(new Point(i, j));
                }
            }
        }

        chickenToggle = new boolean[chickens.size()];

        dfs(0, 0);

        return String.valueOf(answer);
    }

    public void dfs(int start, int depth) {
        if (depth == M) {
            int res = 0;

            for (Point resident : residents) {
                int temp = Integer.MAX_VALUE;

                for (int j = 0; j < chickens.size(); j++) {
                    if (chickenToggle[j]) {
                        temp = Math.min(temp, Math.abs(resident.x - chickens.get(j).x) + Math.abs(resident.y - chickens.get(j).y));
                    }
                }
                res += temp;
            }
            answer = Math.min(answer, res);
            return;
        }

        for (int i = start; i < chickens.size(); i++) {
            chickenToggle[i] = true;
            dfs(i + 1, depth + 1);
            chickenToggle[i] = false;
        }
    }

    class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

