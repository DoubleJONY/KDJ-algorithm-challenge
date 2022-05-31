package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 나무 재테크
 * https://www.acmicpc.net/problem/16235
 */
@RunWith(DataProviderRunner.class)
public class BJ16235 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{

                {
                        new String[]{
                                "5 2 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 1 3",
                                "3 2 3"
                        },
                        "15"
                },
                {
                        new String[]{
                                "5 2 3",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 1 3",
                                "3 2 3"
                        },
                        "13"
                },
                {
                        new String[]{
                                "5 2 4",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 1 3",
                                "3 2 3"
                        },
                        "13"
                },
                {
                        new String[]{
                                "5 2 5",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 1 3",
                                "3 2 3"
                        },
                        "13"
                },
                {
                        new String[]{
                                "5 2 6",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 1 3",
                                "3 2 3"
                        },
                        "85"
                },
                {
                        new String[]{
                                "1 1 1",
                                "1",
                                "1 1 1"
                        },
                        "1"
                },
                {
                        new String[]{
                                "1 1 4",
                                "1",
                                "1 1 1"
                        },
                        "0"
                },
                {
                        new String[]{
                                "5 2 1",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 3 2 3 2",
                                "2 1 3",
                                "3 2 3"
                        },
                        "2"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ16235.Main().solution(input), timer.stop());
    }


    public class Main {

        public String solution(String[] input) {

            int N = Integer.parseInt(input[0].split(" ")[0]);
            int M = Integer.parseInt(input[0].split(" ")[1]);
            int K = Integer.parseInt(input[0].split(" ")[2]);

            int[][][] tree = new int[N][N][100];
            int[][] nutrient = new int[N][N];

            for (int i = 0; i < N; i++) {
                String[] row = input[i + 1].split(" ");
                for (int j = 0; j < N; j++) {
                    nutrient[i][j] = Integer.parseInt(row[j]);
                }
            }

            for (int i = 0; i < M; i++) {
                tree[Integer.parseInt(input[i+N+1].split(" ")[0]) - 1][Integer.parseInt(input[i+N+1].split(" ")[1]) - 1][0] = Integer.parseInt(input[i+N+1].split(" ")[2]);
            }

            TreeCraft treeCraft = new TreeCraft(tree, nutrient, N);

            for (int i = 0; i < K; i++) {
                treeCraft.nextYear();
            }

            return String.valueOf(treeCraft.getTrees());
        }

        class TreeCraft {

            int N;

            int[][][] tree;
            int[][] nutrient;
            int[][] S2D2Nutrient;

            int[][] treeCount;
            int[][] deathTreeAges;

            public TreeCraft(int[][][] tree, int[][] nutrient, int N) {
                this.tree = tree;
                this.S2D2Nutrient = nutrient;
                this.N = N;

                this.nutrient = new int[N][N];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        this.nutrient[i][j] = 5;
                    }
                }

                treeCount = new int[N][N];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        treeCount[i][j] = tree[i][j][0] > 0 ? 1 : 0;
                    }
                }

                deathTreeAges = new int[N][N];
            }

            /**
             * 사계절 진행
             */
            public void nextYear() {
                spring();
                summer();
                fall();
                winter();
            }

            /**
             * 봄
             * 나무가 자라거나 죽는다.
             */
            private void spring() {

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        for (int k = treeCount[i][j] - 1; k >= 0; k--) {
                            if (tree[i][j][k] > 0) {
                                // tree eat nutrient
                                if (nutrient[i][j] >= tree[i][j][k]) {
                                    // grow tree
                                    nutrient[i][j] -= tree[i][j][k];
                                    tree[i][j][k]++;
                                } else {
                                    // tree die
                                    deathTreeAges[i][j] = tree[i][j][k];
                                    tree[i][j][k] = -1;
                                }
                            }
                        }
                    }
                }
            }

            /**
             * 여름
             * 죽은 나무가 자양분이 된다.
             */
            private void summer() {

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        for (int k = 0; k < treeCount[i][j]; k++) {
                            if (tree[i][j][k] == -1) {
                                // add nutrient from deathTreeAges
                                nutrient[i][j] += deathTreeAges[i][j] / 2;
                                deathTreeAges[i][j] = 0;
                                for (int l = k; l < treeCount[i][j]; l++) {
                                    tree[i][j][l] = tree[i][j][l + 1];
                                }
                                treeCount[i][j]--;
                                k--;
                            }
                        }
                    }
                }
            }

            /**
             * 가을
             * 나무가 번식한다.
             */
            private void fall() {

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        for (int k = 0; k < treeCount[i][j]; k++) {
                            if (tree[i][j][k] % 5 == 0) {
                                // spread tree on 8 directions
                                if (i - 1 >= 0 && j - 1 >= 0) {
                                    tree[i - 1][j - 1][treeCount[i - 1][j - 1]] = 1;
                                    treeCount[i - 1][j - 1]++;
                                }
                                if (i - 1 >= 0) {
                                    tree[i - 1][j][treeCount[i - 1][j]] = 1;
                                    treeCount[i - 1][j]++;
                                }
                                if (i - 1 >= 0 && j + 1 < N) {
                                    tree[i - 1][j + 1][treeCount[i - 1][j + 1]] = 1;
                                    treeCount[i - 1][j + 1]++;
                                }
                                if (j - 1 >= 0) {
                                    tree[i][j - 1][treeCount[i][j - 1]] = 1;
                                    treeCount[i][j - 1]++;
                                }
                                if (j + 1 < N) {
                                    tree[i][j + 1][treeCount[i][j + 1]] = 1;
                                    treeCount[i][j + 1]++;
                                }
                                if (i + 1 < N && j - 1 >= 0) {
                                    tree[i + 1][j - 1][treeCount[i + 1][j - 1]] = 1;
                                    treeCount[i + 1][j - 1]++;
                                }
                                if (i + 1 < N) {
                                    tree[i + 1][j][treeCount[i + 1][j]] = 1;
                                    treeCount[i + 1][j]++;
                                }
                                if (i + 1 < N && j + 1 < N) {
                                    tree[i + 1][j + 1][treeCount[i + 1][j + 1]] = 1;
                                    treeCount[i + 1][j + 1]++;
                                }
                            }
                        }
                    }
                }
            }

            /**
             * 겨울
             * S2D2가 양분을 뿌린다.
             */
            private void winter() {

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        // add nutrient from S2D2
                        nutrient[i][j] += S2D2Nutrient[i][j];
                    }
                }
            }

            /**
             * 생존한 나무 수 반환
             */
            public int getTrees() {
                int trees = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        trees += treeCount[i][j];
                    }
                }
                return trees;
            }
        }
    }
}

