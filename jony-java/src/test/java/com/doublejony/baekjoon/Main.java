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

    int[] dx = {1, 0, -1, 0, 1, 0, -1, 0};
    int[] dy = {0, -1, 0, 1, 0, -1, 0, 1};

    public String solution(String[] input) {
        int n = Integer.parseInt(input[0]);
        Queue<Dragon> queue = new LinkedList<>();
        for (int i = 1; i < input.length; i++) {
            String[] data = input[i].split(" ");
            queue.add(new Dragon(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }

        int map[][] = new int[101][101]; // 100 * 100 이라면서 왜 0 <= x,y <= 100 ?

        while (!queue.isEmpty()) {
            Dragon dragon = queue.poll();
            draw(map, dragon);
        }

        return String.valueOf(countSquare(map));
    }

    private int countSquare(int[][] map) {

        int answer = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (map[i][j] == 1 && map[i][j+1] == 1 && map[i+1][j] == 1 && map[i+1][j+1] == 1) {
                    answer++;
                }
            }
        }

        return answer;
    }

    private void draw(int[][] map, Dragon dragon) {
        int nx = dragon.x;
        int ny = dragon.y;

        List<Integer> list = new ArrayList<>();

        list.add(dragon.d);

        for (int i = 1; i <= dragon.gen; i++) {
            for (int j = list.size() - 1; j >= 0; j--) {
                list.add((list.get(j) + 1) % 4);
            }
        }

        map[ny][nx] = 1;
        for (int a : list) {
            nx += dx[a];
            ny += dy[a];
            if (nx >= 0 && nx <= 100 && ny >= 0 && ny <= 100) {
                if (map[ny][nx] == 0) {
                    map[ny][nx] = 1;
                }
            }
        }

    }

    class Dragon {
        int x;
        int y;
        int d;
        int gen;

        public Dragon(int x, int y, int d, int gen) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.gen = gen;
        }
    }
}

