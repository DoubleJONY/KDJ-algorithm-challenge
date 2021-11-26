package com.doublejony.practice.graph;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 방의 개수
 * 원점(0,0)에서 시작해서 아래처럼 숫자가 적힌 방향으로 이동하며 선을 긋습니다.
 *
 * ex) 1일때는 오른쪽 위로 이동
 *
 * 그림을 그릴 때, 사방이 막히면 방하나로 샙니다.
 * 이동하는 방향이 담긴 배열 arrows가 매개변수로 주어질 때, 방의 갯수를 return 하도록 solution 함수를 작성하세요.
 *
 * 제한사항
 * 배열 arrows의 크기는 1 이상 100,000 이하 입니다.
 * arrows의 원소는 0 이상 7 이하 입니다.
 * 방은 다른 방으로 둘러 싸여질 수 있습니다.
 * 입출력 예
 * arrows	return
 * [6, 6, 6, 4, 4, 4, 2, 2, 2, 0, 0, 0, 1, 6, 5, 5, 3, 6, 0]	3
 */
@RunWith(DataProviderRunner.class)
public class Graph3 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        new int[]{6, 5, 2, 7, 1, 4, 2, 4, 6},
                        3
                },
                {
                        new int[]{5, 2, 7, 1, 6, 3},
                        3
                },
                {
                        new int[]{6, 6, 6, 4, 4, 4, 2, 2, 2, 0, 0, 0, 1, 6, 5, 5, 3, 6, 0},
                        3
                },
                {
                        new int[]{6, 2, 4, 0, 5, 0, 6, 4, 2, 4, 2, 0},
                        3
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int[] arrows, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(arrows), timer.stop());
    }

    class Solution {
        int answer = 0;

        int maxWidth = 0;
        int maxHeight = 0;

        List<Integer> addWidth = new ArrayList<>();
        List<Integer> subWidth = new ArrayList<>();
        List<Integer> addHeight = new ArrayList<>();
        List<Integer> subHeight = new ArrayList<>();

        int[][] map;

        public int solution(int[] arrows) {
            defineArrows();
            initBitmap(arrows);
            map = new int[maxHeight][maxWidth];
            drawBitmap(arrows);

            return answer;
        }

        private class Point {
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }

        }

        private void drawBitmap(int[] arrows) {
            int currentWidth = maxWidth / 2;
            int currentHeight = maxHeight / 2;
            int nextWidth = currentWidth;
            int nextHeight = currentHeight;

            map[currentHeight][currentWidth] = 1;
            List<Point> drawHistory = new ArrayList<>();
            drawHistory.add(new Point(currentHeight, currentWidth));

            for (int a : arrows) {

                if(addWidth.contains(a)) {
                    nextWidth++;
                }
                if(subWidth.contains(a)) {
                    nextWidth--;
                }
                if(subHeight.contains(a)) {
                    nextHeight++;
                }
                if(addHeight.contains(a)) {
                    nextHeight--;
                }


                if(a == 1 && map[nextHeight+1][nextWidth] == 1 && map[nextHeight][nextWidth-1] == 1) {
                    answer++;
                }
                if(a == 3 && map[nextHeight-1][nextWidth] == 1 && map[nextHeight][nextWidth-1] == 1) {
                    answer++;
                }
                if(a == 5 && map[nextHeight-1][nextWidth] == 1 && map[nextHeight][nextWidth+1] == 1) {
                    answer++;
                }
                if(a == 7 && map[nextHeight+1][nextWidth] == 1 && map[nextHeight][nextWidth+1] == 1) {
                    answer++;
                }

                if(map[nextHeight][nextWidth] == 1) {
                    for (int i = 0, drawHistorySize = drawHistory.size(); i < drawHistorySize; i++) {
                        if (drawHistory.get(i).getX() == currentHeight && drawHistory.get(i).getY() == currentWidth) {
                            if (drawHistory.size() > i+1 && drawHistory.get(i+1).getX() == nextHeight && drawHistory.get(i+1).getY() == nextWidth){
                                answer--;
                                break;
                            }
                        }
                    }
                    answer++;
                }
                map[nextHeight][nextWidth] = 1;
                currentHeight = nextHeight;
                currentWidth = nextWidth;

                drawHistory.add(new Point(currentHeight, currentWidth));
            }
        }

        private void defineArrows() {
            addWidth.add(1);
            addWidth.add(2);
            addWidth.add(3);

            subWidth.add(5);
            subWidth.add(6);
            subWidth.add(7);

            addHeight.add(0);
            addHeight.add(1);
            addHeight.add(7);

            subHeight.add(3);
            subHeight.add(4);
            subHeight.add(5);
        }

        private void initBitmap(int[] arrows) {
            int left = 0;
            int right = 0;
            int up = 0;
            int down = 0;

            for (int a : arrows) {
                if(addWidth.contains(a)) {
                    right++;
                }
                if(subWidth.contains(a)) {
                    left++;
                }
                if(addHeight.contains(a)) {
                    down++;
                }
                if(subHeight.contains(a)) {
                    up++;
                }
            }

            maxWidth += left + right;
            maxHeight += up + down;

        }
    }
}
