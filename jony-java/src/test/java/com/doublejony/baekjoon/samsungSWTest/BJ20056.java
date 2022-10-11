package com.doublejony.baekjoon.samsungSWTest;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 마법사 상어
 * https://www.acmicpc.net/problem/20056
 */
@RunWith(DataProviderRunner.class)
public class BJ20056 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new String[]{
                                "4 2 2",
                                "1 1 5 2 2",
                                "1 4 7 1 6"
                        },
                        "8"
                },
                {
                        new String[]{
                                "7 5 3",
                                "1 3 5 2 4",
                                "2 3 5 2 6",
                                "5 2 9 1 7",
                                "6 2 1 3 5",
                                "4 4 2 4 2"
                        },
                        "9"
                },
                {
                        new String[]{
                                "4 2 1",
                                "1 1 5 2 2",
                                "1 4 7 1 6"
                        },
                        "8"
                },
                {
                        new String[]{
                                "4 2 3",
                                "1 1 5 2 2",
                                "1 4 7 1 6"
                        },
                        "0"
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(String[] input, String expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new BJ20056.Main().solution(input),
                timer.stop());
    }

    public class Main {

        int N;
        int M;
        int K;

        final int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
        final int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

        public String solution(String[] input) {

            N = Integer.parseInt(input[0].split(" ")[0]);
            M = Integer.parseInt(input[0].split(" ")[1]);
            K = Integer.parseInt(input[0].split(" ")[2]);

            List<Fireball> fireballs = new ArrayList<>();

            for (int i = 0; i < M; i++) {
                fireballs.add(new Fireball(
                        Integer.parseInt(input[i + 1].split(" ")[0]) - 1,
                        Integer.parseInt(input[i + 1].split(" ")[1]) - 1,
                        Integer.parseInt(input[i + 1].split(" ")[2]),
                        Integer.parseInt(input[i + 1].split(" ")[3]),
                        Integer.parseInt(input[i + 1].split(" ")[4])
                ));
            }

            for (int i = 0; i < K; i++) {
                moveAll(fireballs);
                mergeAll(fireballs);
            }

            return String.valueOf(fireballs.stream().mapToInt(fireball -> (int) fireball.m).sum());
        }

        private void moveAll(List<Fireball> fireballs) {
            fireballs.forEach(Fireball::move);
        }

        private void mergeAll(List<Fireball> fireballs) {
            List<Fireball> newFireballs = new ArrayList<>();
            for (int i = 0; i < fireballs.size() - 1; i++) {
                if (!fireballs.get(i).enable) {
                    continue;
                }
                List<Fireball> mergeFireballs = new ArrayList<>();
                for (int j = i + 1; j < fireballs.size(); j++) {
                    if (fireballs.get(i).isOverlapped(fireballs.get(j))) {
                        mergeFireballs.add(fireballs.get(j));
                        fireballs.get(j).delete();
                    }
                }

                if (mergeFireballs.isEmpty()) {
                    continue;
                }

                fireballs.get(i).delete();

                double mergeM = fireballs.get(i).m;
                int mergeS = fireballs.get(i).s;
                int matchD = fireballs.get(i).d % 2;
                boolean isMatchD = true;
                for (Fireball fireball : mergeFireballs) {
                    mergeM += fireball.m;
                    mergeS += fireball.s;
                    if (fireball.d % 2 != matchD) {
                        isMatchD = false;
                    }
                }
                if (mergeM / 5 > 0) {
                    newFireballs.addAll(fireballs.get((i)).merge(mergeM / 5, mergeS / (mergeFireballs.size() + 1), isMatchD));
                }
            }
            fireballs.removeIf(fireball -> !fireball.enable);
            fireballs.addAll(newFireballs);
        }

        class Fireball {

            int r;
            int c;
            double m;
            int s;
            int d;
            boolean enable;

            public Fireball(int r, int c, double m, int s, int d) {
                this.r = r;
                this.c = c;
                this.m = m;
                this.s = s;
                this.d = d;
                this.enable = true;
            }

            public void move() {
                this.r = r + (dr[d] * s);
                this.c = c + (dc[d] * s);

                while (this.r >= N) {
                    this.r -= N;
                }
                while (this.r < 0) {
                    this.r += N;
                }
                while (this.c >= N) {
                    this.c -= N;
                }
                while (this.c < 0) {
                    this.c += N;
                }
            }

            public boolean isOverlapped(Fireball o) {
                return this.r == o.r && this.c == o.c && this.enable;
            }

            public void delete() {
                this.enable = false;
            }

            public List<Fireball> merge(double mergeM, int mergeS, boolean matchD) {

                int[] newD = matchD ? new int[]{0, 2, 4, 6} : new int[]{1, 3, 5, 7};

                List<Fireball> newFireballs = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    newFireballs.add(new Fireball(
                            this.r,
                            this.c,
                            mergeM,
                            mergeS,
                            newD[i]
                    ));
                }

                return newFireballs;
            }
        }
    }
}

