package com.doublejony.backjun.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

@RunWith(DataProviderRunner.class)
public class 구슬탈출2 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "5 5",
                                "#####",
                                "#..B#",
                                "#.#.#",
                                "#RO.#",
                                "#####"
                        },
                        "1"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Main().solution(input), timer.stop());
    }

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
                map[i] = input[i+1];
            }

            initPoints(map);
            moveOrb("", 0, this.red, this.blue);

            return input[0];
        }

        private void moveOrb(String direction, int depth, Point red, Point blue) {

            if(red.height == this.goal.height && red.width == this.goal.width) {
                if(depth < minDepth) {
                    this.minDepth = depth;
                }
                return;
            }

            if(blue.height == this.goal.height && blue.width == this.goal.width) {
                return;
            }

            if(direction != null) {
                depth++;

                switch (direction) {
                    case "down":
                        while(true) {
                            if(map[red.height + 1].charAt(red.width) != '#'){
                                red.setPoint(red.height + 1, red.width);
                            } else {
                                break;
                            }
                        }
                        break;
                    case "up":
                        while(true) {
                            if(map[red.height - 1].charAt(red.width) != '#'){
                                red.setPoint(red.height - 1, red.width);
                            } else {
                                break;
                            }
                        }
                        break;
                    case "left":
                        while(true) {
                            if(map[red.height].charAt(red.width - 1) != '#'){
                                red.setPoint(red.height, red.width - 1);
                            } else {
                                break;
                            }
                        }
                        break;
                    case "right":
                        while(true) {
                            if(map[red.height].charAt(red.width + 1) != '#'){
                                red.setPoint(red.height, red.width + 1);
                            } else {
                                break;
                            }
                        }
                        break;
                }
            }

        }

        private void initPoints(String[] map) {
            for (int i = 0; i < map.length; i++) {
                if(map[i].contains("R")) {
                    this.red = new Point(i, map[i].indexOf("R"));
                }
                if(map[i].contains("B")) {
                    this.blue = new Point(i, map[i].indexOf("B"));
                }
                if(map[i].contains("O")) {
                    this.goal = new Point(i, map[i].indexOf("O"));
                }
            }
        }

        class Point {
            int height = 0;
            int width = 0;

            public Point(int height, int width) {
                this.height = height;
                this.width = width;
            }

            public void setPoint(int height, int width){
                this.height = height;
                this.width = width;
            }

            public boolean equals(Point a) {
                return this.height == a.height && this.width == a.width;
            }
        }
    }
}
