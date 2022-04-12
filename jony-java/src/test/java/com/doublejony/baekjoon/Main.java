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

    int N;
    int M;

    int answer;

    public String solution(String[] input) {
        N = Integer.parseInt(input[0].split(" ")[0]);
        M = Integer.parseInt(input[0].split(" ")[1]);

        Queue<Camera> cameras = new LinkedList<>();

        answer = 9999999;

        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String[] row = input[i + 1].split(" ");
            for (int j = 0; j < M; j++) {
                if (Integer.parseInt(row[j]) > 0 && Integer.parseInt(row[j]) < 6) {
                    cameras.add(new Camera(Integer.parseInt(row[j]), i, j));
                }
                map[i][j] = Integer.parseInt(row[j]);
            }
        }

        dfs(map, cameras);

        return answer == 9999999 ? "0" : String.valueOf(answer);
    }

    class Camera {
        int type;
        int n;
        int m;

        public Camera(int type, int n, int m) {
            this.type = type;
            this.n = n;
            this.m = m;
        }
    }

    private void dfs(int[][] map, Queue<Camera> cameras) {
        if (cameras.isEmpty()) {
            int sum = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == 0) {
                        sum++;
                    }
                }
            }

            answer = Math.min(answer, sum);
            return;
        }

        int[][] newMap = copyMap(map);

        Camera camera = cameras.poll();

        switch (camera.type) {
            case 5:
                fillUp(newMap, camera.n, camera.m);
                fillDown(newMap, camera.n, camera.m);
                fillLeft(newMap, camera.n, camera.m);
                fillRight(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));
                return;
            case 4:
                // case 1
                fillLeft(newMap, camera.n, camera.m);
                fillUp(newMap, camera.n, camera.m);
                fillRight(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 2
                newMap = copyMap(map);
                fillUp(newMap, camera.n, camera.m);
                fillRight(newMap, camera.n, camera.m);
                fillDown(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 3
                newMap = copyMap(map);
                fillRight(newMap, camera.n, camera.m);
                fillDown(newMap, camera.n, camera.m);
                fillLeft(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 4
                newMap = copyMap(map);
                fillDown(newMap, camera.n, camera.m);
                fillLeft(newMap, camera.n, camera.m);
                fillUp(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                return;
            case 3:
                // case 1
                fillUp(newMap, camera.n, camera.m);
                fillRight(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 2
                newMap = copyMap(map);
                fillRight(newMap, camera.n, camera.m);
                fillDown(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 3
                newMap = copyMap(map);
                fillDown(newMap, camera.n, camera.m);
                fillLeft(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 4
                newMap = copyMap(map);
                fillLeft(newMap, camera.n, camera.m);
                fillUp(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                return;
            case 2:
                // case 1
                fillLeft(newMap, camera.n, camera.m);
                fillRight(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 2
                newMap = copyMap(map);
                fillUp(newMap, camera.n, camera.m);
                fillDown(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                return;
            case 1:
                // case 1
                fillRight(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 2
                newMap = copyMap(map);
                fillDown(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 3
                newMap = copyMap(map);
                fillLeft(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));

                // case 4
                newMap = copyMap(map);
                fillUp(newMap, camera.n, camera.m);
                dfs(newMap, cloneQueue(cameras));
        }
    }

    private Queue<Camera> cloneQueue(Queue<Camera> cameras) {
        return new LinkedList<>(cameras);
    }

    private int[][] copyMap(int[][] map) {
        int[][] newMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(map[i], 0, newMap[i], 0, M);
        }
        return newMap;
    }

    private void fillUp(int[][] map, int n, int m) {

        for (int i = n - 1; i >= 0; i--) {
            if (map[i][m] == 6) {
                break;
            } else if (map[i][m] == 0) {
                map[i][m] = 7;
            }
        }
    }

    private void fillDown(int[][] map, int n, int m) {

        for (int i = n + 1; i < N; i++) {
            if (map[i][m] == 6) {
                break;
            } else if (map[i][m] == 0) {
                map[i][m] = 7;
            }
        }
    }

    private void fillLeft(int[][] map, int n, int m) {

        for (int i = m - 1; i >= 0; i--) {
            if (map[n][i] == 6) {
                break;
            } else if (map[n][i] == 0) {
                map[n][i] = 7;
            }
        }
    }

    private void fillRight(int[][] map, int n, int m) {

        for (int i = m + 1; i < M; i++) {
            if (map[n][i] == 6) {
                break;
            } else if (map[n][i] == 0) {
                map[n][i] = 7;
            }
        }
    }
}

