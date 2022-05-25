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

    int[][] map;
    int[][] visited;

    int N;
    int L;
    int R;

    boolean open = false;

    Queue<Point> queue = new LinkedList<>();

    public String solution(String[] input) {

        int answer = 0;

        N = Integer.parseInt(input[0].split(" ")[0]);
        L = Integer.parseInt(input[0].split(" ")[1]);
        R = Integer.parseInt(input[0].split(" ")[2]);

        map = new int[N][N];
        visited = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
                visited[i][j] = 0;
            }
        }

        while (true) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    bfs(new Point(i, j), true);
                }
            }

            if (!open) {
                break;
            }

            answer++;
            resetVisited();
            open = false;
        }

        return String.valueOf(answer);
    }

    private void resetVisited() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                visited[i][j] = 0;
            }
        }
    }

    private void bfs(Point point, boolean init) {

        visited[point.x][point.y] = 1;

        int left = point.y - 1 < 0 ? -1 : map[point.x][point.y - 1];
        int right = point.y + 1 >= N ? -1 : map[point.x][point.y + 1];
        int up = point.x - 1 < 0 ? -1 : map[point.x - 1][point.y];
        int down = point.x + 1 >= N ? -1 : map[point.x + 1][point.y];

        if (left != -1 && Math.abs(left - map[point.x][point.y]) <= R && Math.abs(left - map[point.x][point.y]) >= L
                && visited[point.x][point.y - 1] == 0) {
            open = true;
            queue.add(new Point(point.x, point.y - 1));
            bfs(new Point(point.x, point.y - 1), false);
        }

        if (right != -1 && Math.abs(right - map[point.x][point.y]) <= R && Math.abs(right - map[point.x][point.y]) >= L
                && visited[point.x][point.y + 1] == 0) {
            open = true;
            queue.add(new Point(point.x, point.y + 1));
            bfs(new Point(point.x, point.y + 1), false);
        }

        if (up != -1 && Math.abs(up - map[point.x][point.y]) <= R && Math.abs(up - map[point.x][point.y]) >= L
                && visited[point.x - 1][point.y] == 0) {
            open = true;
            queue.add(new Point(point.x - 1, point.y));
            bfs(new Point(point.x - 1, point.y), false);
        }

        if (down != -1 && Math.abs(down - map[point.x][point.y]) <= R && Math.abs(down - map[point.x][point.y]) >= L
                && visited[point.x + 1][point.y] == 0) {
            open = true;
            queue.add(new Point(point.x + 1, point.y));
            bfs(new Point(point.x + 1, point.y), false);
        }

        if (init && open) {
            queue.add(new Point(point.x, point.y));
            int size = queue.size();
            int sum = 0;
            Queue<Point> tempQueue = new LinkedList<>();
            while (!queue.isEmpty()) {
                Point p = queue.poll();
                tempQueue.add(p);
                sum += map[p.x][p.y];
            }
            while (!tempQueue.isEmpty()) {
                Point p = tempQueue.poll();
                map[p.x][p.y] = sum / size;
            }
        }
    }

    private class Point {

        int x;
        int y;

        public Point(int x, int y) {

            this.x = x;
            this.y = y;
        }
    }
}


