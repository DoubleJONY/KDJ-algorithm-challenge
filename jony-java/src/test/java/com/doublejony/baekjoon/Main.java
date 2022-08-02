package com.doublejony.baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    int REMOVED = 0;

    public String solution(String[] input) {

        int N = Integer.parseInt(input[0].split(" ")[0]);
        int M = Integer.parseInt(input[0].split(" ")[1]);
        int T = Integer.parseInt(input[0].split(" ")[2]);

        List<LinkedList<Integer>> circles = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            LinkedList<Integer> k = new LinkedList<>();
            for (int j = 0; j < M; j++) {
                k.add(Integer.parseInt(input[i + 1].split(" ")[j]));
            }
            circles.add(k);
        }

        List<Xdk> xdkList = new ArrayList<>();

        for (int i = 0; i < T; i++) {
            xdkList.add(new Xdk(
                    Integer.parseInt(input[N + 1 + i].split(" ")[0]) - 1,
                    Integer.parseInt(input[N + 1 + i].split(" ")[1]),
                    Integer.parseInt(input[N + 1 + i].split(" ")[2]))
            );
        }

        for (int i = 0; i < T; i++) {
            spin(xdkList.get(i), circles);
            if (!removeAllAdjoin(N, M, circles)) {
                standardizeAll(N, M, circles);
            }
        }

        return String.valueOf(sum(circles));
    }

    private void spin(Xdk xdk, List<LinkedList<Integer>> circles) {

        int k;

        //reverse direction
        if (xdk.d == 0) {
            k = xdk.k;
        } else {
            k = circles.get(xdk.x).size() - xdk.k;
        }

        List<Integer> spinCircleList = new ArrayList<>();

        int mul = 1;
        while (((xdk.x + 1) * mul) - 1 < circles.size()) {
            spinCircleList.add(((xdk.x + 1) * mul) - 1);
            mul++;
        }

        //spin
        for (int c : spinCircleList) {
            for (int i = 0; i < k; i++) {
                int t = circles.get(c).removeLast();
                circles.get(c).addFirst(t);
            }
        }
    }

    private boolean removeAllAdjoin(int N, int M, List<LinkedList<Integer>> circles) {

        boolean isChanged = false;

        int[] di = { 0, 0, 1, -1 };
        int[] dj = { 1, -1, 0, 0 };

        Map<String, Point> pointList = new HashMap<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                boolean isCurrentChanged = false;
                int current = circles.get(i).get(j);

                if (current == REMOVED) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {
                    int neari = i + di[k];
                    int nearj = j + dj[k];

                    //validate and calibrate nearNumber
                    if (neari < 0 || neari >= N) {
                        continue;
                    }

                    if (nearj < 0) {
                        nearj = M - 1;
                    } else if (nearj >= M) {
                        nearj = 0;
                    }

                    //remove adjoin numbers
                    if (circles.get(neari).get(nearj) == current) {
                        pointList.put(neari + "," + nearj, new Point(neari, nearj));

                        isChanged = true;
                        isCurrentChanged = true;
                    }
                }

                if (isCurrentChanged) {
                    pointList.put(i + "," + j, new Point(i, j));
                }
            }
        }

        for (Point point : pointList.values()) {
            circles.get(point.i).set(point.j, REMOVED);
        }

        return isChanged;
    }

    private int sum(List<LinkedList<Integer>> circles) {

        int sum = 0;
        for (LinkedList<Integer> circle : circles) {
            for (Integer number : circle) {
                sum += number;
            }
        }
        return sum;
    }

    private void standardizeAll(int N, int M, List<LinkedList<Integer>> circles) {

        double sum = 0;
        int count = 0;

        for (LinkedList<Integer> circle : circles) {
            for (Integer number : circle) {
                sum += number;
                if (number != REMOVED) {
                    count++;
                }
            }
        }

        double avg = sum / count;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int current = circles.get(i).get(j);

                if (current > avg) {
                    circles.get(i).set(j, current - 1);
                } else if (current != REMOVED && current < avg) {
                    circles.get(i).set(j, current + 1);
                }
            }
        }

    }

    private class Xdk {

        int x;
        int d;
        int k;

        public Xdk(int x, int d, int k) {

            this.x = x;
            this.d = d;
            this.k = k;
        }
    }


    private class Point {

        int i;
        int j;

        public Point(int i, int j) {

            this.i = i;
            this.j = j;
        }
    }
}


