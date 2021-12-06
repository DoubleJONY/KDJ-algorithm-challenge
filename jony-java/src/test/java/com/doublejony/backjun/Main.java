package com.doublejony.backjun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    Point red;
    Point blue;
    Point goal;
    String[] map;
    int minDepth = 11;

    public String solution(String[] input) {

        String[] size = input[0].split(" ");
        map = new String[Integer.parseInt(size[0])];

        for (int i = 0; i < Integer.parseInt(size[0]); i++) {
            map[i] = input[i + 1];
        }

        initPoints(map);
        moveOrb("", 0, this.red, this.blue);

        return Integer.toString(minDepth == 11 ? -1 : minDepth);
    }

    private void moveOrb(String direction, int depth, Point r, Point b) {

        Point red = new Point(r.height, r.width);
        Point blue = new Point(b.height, b.width);

        if (!direction.equals("")) {
            depth++;

            if (depth > 10) {
                return;
            }

            Point orbA;
            Point orbB;

            switch (direction) {
                case "down":
                    if (red.height > blue.height) {
                        orbA = red;
                        orbB = blue;
                    } else {
                        orbA = blue;
                        orbB = red;
                    }
                    while (true) {
                        if (move(orbA, orbB, orbA.height + 1, orbA.width)) {
                            break;
                        }
                    }
                    while (true) {
                        if (move(orbB, orbA, orbB.height + 1, orbB.width)) {
                            break;
                        }
                    }
                    break;
                case "up":
                    if (red.height < blue.height) {
                        orbA = red;
                        orbB = blue;
                    } else {
                        orbA = blue;
                        orbB = red;
                    }
                    while (true) {
                        if (move(orbA, orbB, orbA.height - 1, orbA.width)) {
                            break;
                        }
                    }
                    while (true) {
                        if (move(orbB, orbA, orbB.height - 1, orbB.width)) {
                            break;
                        }
                    }
                    break;
                case "left":
                    if (red.width < blue.width) {
                        orbA = red;
                        orbB = blue;
                    } else {
                        orbA = blue;
                        orbB = red;
                    }
                    while (true) {
                        if (move(orbA, orbB, orbA.height, orbA.width - 1)) {
                            break;
                        }
                    }
                    while (true) {
                        if (move(orbB, orbA, orbB.height, orbB.width - 1)) {
                            break;
                        }
                    }
                    break;
                case "right":
                    if (red.width > blue.width) {
                        orbA = red;
                        orbB = blue;
                    } else {
                        orbA = blue;
                        orbB = red;
                    }
                    while (true) {
                        if (move(orbA, orbB, orbA.height, orbA.width + 1)) {
                            break;
                        }
                    }
                    while (true) {
                        if (move(orbB, orbA, orbB.height, orbB.width + 1)) {
                            break;
                        }
                    }
                    break;
            }
        }

        if (isGoal(red)) {
            if (!isGoal(blue) && depth < minDepth) {
                this.minDepth = depth;
            }
            return;
        }

        if ((map[red.height + 1].charAt(red.width) != '#' || map[blue.height + 1].charAt(blue.width) != '#')
                && !direction.equals("up")) {
            moveOrb("down", depth, red, blue);
        }
        if ((map[red.height - 1].charAt(red.width) != '#' || map[blue.height - 1].charAt(blue.width) != '#')
                && !direction.equals("down")) {
            moveOrb("up", depth, red, blue);
        }
        if ((map[red.height].charAt(red.width - 1) != '#' || map[blue.height].charAt(blue.width - 1) != '#')
                && !direction.equals("right")) {
            moveOrb("left", depth, red, blue);
        }
        if ((map[red.height].charAt(red.width + 1) != '#' || map[blue.height].charAt(blue.width + 1) != '#')
                && !direction.equals("left")) {
            moveOrb("right", depth, red, blue);
        }

    }

    private boolean move(Point orb, Point oppOrb, int tHeight, int tWidth) {
        if (isStuck(tHeight, tWidth, oppOrb)) {
            return true;
        }
        if (map[tHeight].charAt(tWidth) != '#') {
            orb.setPoint(tHeight, tWidth);
            return isGoal(orb);
        } else {
            return true;
        }
    }

    private boolean isGoal(Point orb) {
        return orb.height == this.goal.height && orb.width == this.goal.width;
    }

    private boolean isStuck(int tHeight, int tWidth, Point oppOrb) {
        return tHeight == oppOrb.height && tWidth == oppOrb.width && !isGoal(oppOrb);
    }

    private void initPoints(String[] map) {
        for (int i = 0; i < map.length; i++) {
            if (map[i].contains("R")) {
                this.red = new Point(i, map[i].indexOf("R"));
            }
            if (map[i].contains("B")) {
                this.blue = new Point(i, map[i].indexOf("B"));
            }
            if (map[i].contains("O")) {
                this.goal = new Point(i, map[i].indexOf("O"));
            }
        }
    }

    class Point {
        int height;
        int width;

        public Point(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public void setPoint(int height, int width) {
            this.height = height;
            this.width = width;
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> input = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String temp;
        while (!(temp = br.readLine()).isEmpty()) {
            input.add(temp);
        }

        String answer = new Main().solution(input.toArray(new String[input.size()]));

        System.out.println(answer);
    }
}
