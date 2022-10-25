package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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

        String[] answer = new Main().solution(input.toArray(new String[input.size()]));

        System.out.println(answer[0]);
        System.out.println(answer[1]);
    }

    int[] dh = new int[] { -1, 0, 1, 0 };
    int[] dw = new int[] { 0, 1, 0, -1 };

    public String[] solution(String[] input) {

        int N = Integer.parseInt(input[0].split(" ")[0]);
        int Q = Integer.parseInt(input[0].split(" ")[1]);

        int nn = (int) Math.pow(2, N);
        int[][] iceMap = new int[nn][nn];

        for (int i = 0; i < nn; i++) {
            for (int j = 0; j < nn; j++) {
                iceMap[i][j] = Integer.parseInt(input[i + 1].split(" ")[j]);
            }
        }

        int[] lList = new int[Q];

        for (int i = 0; i < Q; i++) {
            lList[i] = Integer.parseInt(input[nn + 1].split(" ")[i]);
        }

        String[] result = new String[2];

        for (int i = 0; i < Q; i++) {
            spin(iceMap, lList[i]);
            melt(iceMap);
        }

        result[0] = String.valueOf(sumIces(iceMap));
        result[1] = String.valueOf(getBiggestIceSize(iceMap));

        return new String[] { result[0], result[1] };
    }

    private void spin(int[][] iceMap, int L) {

        int gridSize = (int) Math.pow(2, L);

        for (int i = 0; i < iceMap.length; i += gridSize) {
            for (int j = 0; j < iceMap.length; j += gridSize) {

                int[][] localMap = new int[gridSize][gridSize];
                for (int k = 0; k < gridSize; k++) {
                    for (int l = 0; l < gridSize; l++) {
                        localMap[k][l] = iceMap[i + k][j + l];
                    }
                }

                int[][] rotatedMap = rotate(localMap);

                for (int k = 0; k < gridSize; k++) {
                    for (int l = 0; l < gridSize; l++) {
                        iceMap[i + k][j + l] = rotatedMap[k][l];
                    }
                }
            }
        }
    }

    private int[][] rotate(int[][] arr) {

        int n = arr.length;
        int m = arr[0].length;
        int[][] rotate = new int[m][n];

        for (int i = 0; i < rotate.length; i++) {
            for (int j = 0; j < rotate[i].length; j++) {
                rotate[i][j] = arr[n - 1 - j][i];
            }
        }

        return rotate;
    }

    private void melt(int[][] iceMap) {

        int[][] newIceMap = new int[iceMap.length][iceMap.length];

        for (int i = 0; i < iceMap.length; i++) {
            for (int j = 0; j < iceMap.length; j++) {
                newIceMap[i][j] = iceMap[i][j];
            }
        }

        for (int i = 0; i < iceMap.length; i++) {
            for (int j = 0; j < iceMap.length; j++) {
                if (iceMap[i][j] <= 0) {
                    continue;
                }
                int iced = 0;
                for (int k = 0; k < 4; k++) {
                    try {
                        if (iceMap[i + dh[k]][j + dw[k]] > 0) {
                            iced++;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {

                    }
                }
                if (iced < 3) {
                    newIceMap[i][j]--;
                }
            }
        }

        for (int i = 0; i < iceMap.length; i++) {
            for (int j = 0; j < iceMap.length; j++) {
                iceMap[i][j] = newIceMap[i][j];
            }
        }
    }

    private int sumIces(int[][] iceMap) {

        return Arrays.stream(iceMap).mapToInt(ints -> Arrays.stream(ints, 0, iceMap.length).sum()).sum();
    }

    private int getBiggestIceSize(int[][] iceMap) {

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visitedMap = new boolean[iceMap.length][iceMap.length];

        int max = 0;
        for (int i = 0; i < iceMap.length; i++) {
            for (int j = 0; j < iceMap.length; j++) {
                if (iceMap[i][j] > 0 && !visitedMap[i][j]) {
                    queue.add(new int[] { i, j });
                    visitedMap[i][j] = true;
                    int cnt = 1;

                    while (!queue.isEmpty()) {
                        int[] t = queue.poll();
                        int th = t[0];
                        int tw = t[1];

                        for (int k = 0; k < 4; k++) {
                            int nh = th + dh[k];
                            int nw = tw + dw[k];
                            try {
                                if (iceMap[nh][nw] > 0 && !visitedMap[nh][nw]) {
                                    visitedMap[nh][nw] = true;
                                    queue.add(new int[] { nh, nw });
                                    cnt++;
                                }
                            } catch (IndexOutOfBoundsException ignored) {

                            }
                        }
                    }
                    max = Math.max(max, cnt);
                }
            }
        }
        return max;
    }
}

