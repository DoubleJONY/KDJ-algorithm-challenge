package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 어른 상어
 * https://www.acmicpc.net/problem/19237
 */
@RunWith(DataProviderRunner.class)
public class BJ19237 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "5 4 4",
                                "0 0 0 0 3",
                                "0 2 0 0 0",
                                "1 0 0 0 4",
                                "0 0 0 0 0",
                                "0 0 0 0 0",
                                "4 4 3 1",
                                "2 3 1 4",
                                "4 1 2 3",
                                "3 4 2 1",
                                "4 3 1 2",
                                "2 4 3 1",
                                "2 1 3 4",
                                "3 4 1 2",
                                "4 1 2 3",
                                "4 3 2 1",
                                "1 4 3 2",
                                "1 3 2 4",
                                "3 2 1 4",
                                "3 4 1 2",
                                "3 2 4 1",
                                "1 4 2 3",
                                "1 4 2 3"
                        },
                        "14"
                },
                {
                        new String[]{
                                "4 2 6",
                                "1 0 0 0",
                                "0 0 0 0",
                                "0 0 0 0",
                                "0 0 0 2",
                                "4 3",
                                "1 2 3 4",
                                "2 3 4 1",
                                "3 4 1 2",
                                "4 1 2 3",
                                "1 2 3 4",
                                "2 3 4 1",
                                "3 4 1 2",
                                "4 1 2 3"
                        },
                        "26"
                },
                {
                        new String[]{
                                "5 4 1",
                                "0 0 0 0 3",
                                "0 2 0 0 0",
                                "1 0 0 0 4",
                                "0 0 0 0 0",
                                "0 0 0 0 0",
                                "4 4 3 1",
                                "2 3 1 4",
                                "4 1 2 3",
                                "3 4 2 1",
                                "4 3 1 2",
                                "2 4 3 1",
                                "2 1 3 4",
                                "3 4 1 2",
                                "4 1 2 3",
                                "4 3 2 1",
                                "1 4 3 2",
                                "1 3 2 4",
                                "3 2 1 4",
                                "3 4 1 2",
                                "3 2 4 1",
                                "1 4 2 3",
                                "1 4 2 3"
                        },
                        "-1"
                },
                {
                        new String[]{
                                "5 4 10",
                                "0 0 0 0 3",
                                "0 0 0 0 0",
                                "1 2 0 0 0",
                                "0 0 0 0 4",
                                "0 0 0 0 0",
                                "4 4 3 1",
                                "2 3 1 4",
                                "4 1 2 3",
                                "3 4 2 1",
                                "4 3 1 2",
                                "2 4 3 1",
                                "2 1 3 4",
                                "3 4 1 2",
                                "4 1 2 3",
                                "4 3 2 1",
                                "1 4 3 2",
                                "1 3 2 4",
                                "3 2 1 4",
                                "3 4 1 2",
                                "3 2 4 1",
                                "1 4 2 3",
                                "1 4 2 3"
                        },
                        "-1"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ19237.Main().solution(input),
                timer.stop());
    }

    public class Main {

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);
            int M = Integer.parseInt(input[0].split(" ")[1]);
            int k = Integer.parseInt(input[0].split(" ")[2]);

            int[][] sharkMap = new int[N][N];
            int[][] smellMap = new int[N][N];


            List<Shark> sharkList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    sharkMap[i][j] = Integer.parseInt(input[i+1].split(" ")[j]);
                    if (sharkMap[i][j] != 0) {
                        sharkList.add(new Shark(sharkMap[i][j], i, j));
                        smellMap[i][j] = k;
                    }
                }
            }

            SharkMap map = new SharkMap(N, M, k, sharkMap, smellMap);

            sharkList.sort(Comparator.naturalOrder());

            for (int i = 0; i < M; i++) {
                sharkList.get(i).setDirection(Integer.parseInt(input[1+N].split(" ")[i]));
            }

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < 4; j++) {
                    List<Integer> dir = new ArrayList<>();
                    for (int l = 0; l < 4; l++) {
                        dir.add(Integer.parseInt(input[2+(i*4)+j+N].split(" ")[l]));
                    }
                    switch (j) {
                        case 0:
                            sharkList.get(i).setUpMap(dir);
                            break;
                        case 1:
                            sharkList.get(i).setDownMap(dir);
                            break;
                        case 2:
                            sharkList.get(i).setLeftMap(dir);
                            break;
                        case 3:
                            sharkList.get(i).setRightMap(dir);
                            break;
                    }
                }
            }


            while (true) {

                //move all
                    //check empty space
                //kill conflicts
                //reduce smell
                map.reduceSmell();
                //confirm new shark position
                map.confirmSharkMap(sharkList);

                //check finish (1번 상어만 살아남은 경우 & 1000번)
            }

            return null;

        }

        class SharkMap {

            int N; //Map Size
            int M; //Count of Shark
            int k; //Smell Time

            int[][] sharkMap;
            int[][] smellMap;

            public SharkMap(int n, int m, int k, int[][] sharkMap, int[][] smellMap) {
                N = n;
                M = m;
                this.k = k;
                this.sharkMap = sharkMap;
                this.smellMap = smellMap;
            }

            public boolean isSharkConflict(int h, int w, List<Shark> sharkList) {

            }

            public void moveShark(int h, int w, int sharkNo) {

                //상어가 움직이는 순간 냄새가 1인 칸은 이동할 수 없다고 가정
                sharkMap[h][w] = sharkNo;
            }

            public void reduceSmell() {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        smellMap[i][j]--;
                        if (smellMap[i][j] <= 0) {
                            smellMap[i][j] = 0;
                            sharkMap[i][j] = 0;
                        }
                    }
                }
            }

            public void confirmSharkMap(List<Shark> sharkList) {
                for (Shark shark : sharkList) {
                    sharkMap[shark.getH()][shark.getW()] = shark.getNo();
                    smellMap[shark.getH()][shark.getW()] = k;
                }
            }
        }

        class Shark implements Comparable<Shark>{
            int UP = 1;
            int DOWN = 2;
            int LEFT = 3;
            int RIGHT = 4;

            int no; //Shark IndexNumber
            int h; //height
            int w; //width
            int direction;
            List<Integer> upMap;
            List<Integer> downMap;
            List<Integer> leftMap;
            List<Integer> rightMap;

            public Shark(int no, int h, int w) {
                this.no = no;
                this.h = h;
                this.w = w;
            }

            public int getNo() {
                return no;
            }

            public void setNo(int no) {
                this.no = no;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public List<Integer> getUpMap() {
                return upMap;
            }

            public void setUpMap(List<Integer> upMap) {
                this.upMap = upMap;
            }

            public List<Integer> getDownMap() {
                return downMap;
            }

            public void setDownMap(List<Integer> downMap) {
                this.downMap = downMap;
            }

            public List<Integer> getLeftMap() {
                return leftMap;
            }

            public void setLeftMap(List<Integer> leftMap) {
                this.leftMap = leftMap;
            }

            public List<Integer> getRightMap() {
                return rightMap;
            }

            public void setRightMap(List<Integer> rightMap) {
                this.rightMap = rightMap;
            }

            @Override
            public int compareTo(Shark o) {
                return this.getNo() > o.getNo() ? 1 : -1;
            }
        }
    }
}

