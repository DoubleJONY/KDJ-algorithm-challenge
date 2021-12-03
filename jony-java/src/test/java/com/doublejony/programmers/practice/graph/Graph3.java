package com.doublejony.programmers.practice.graph;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
                        new int[]{6, 2, 4, 0, 5, 0, 6, 4, 2, 4, 2, 0},
                        3
                },
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

    /**
     * https://velog.io/@easycelsius/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4%EB%B0%A9%EC%9D%98-%EA%B0%9C%EC%88%98
     */
    class Solution {
        class Pair {
            public int x;
            public int y;

            public Pair(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int hashCode() {
                return Objects.hash(x,y);
            }

            public boolean equals(Object o) {
                return this.x == ((Pair) o).x && this.y == ((Pair) o).y;
            }
        }

        public int solution(int[] arrows) {

            // 변수 선언
            int cnt = 0;

            // 방향 관련 배열 선언
            Pair pointHC = new Pair(0, 0);
            int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
            int[] dy = { 1, 1, 0, -1, -1, -1, 0, 1 };

            // 방문 여부 관련 선언
            // key = 시작 node의 hashcode, value = 연결된 node들의 hashcode
            HashMap<Pair, ArrayList<Pair>> visitied = new HashMap<>();

            // 로직 처리
            for (int arrow : arrows) {
                for (int i = 0; i <= 1; i++) { // 교차점 처리를 위한 스케일업(반복 2번)

                    // 이동 진행
                    Pair newPointHC = new Pair(pointHC.x + dx[arrow], pointHC.y + dy[arrow]);

                    // 처음 방문하는 경우 = map에 키값이 없는 경우
                    if (!visitied.containsKey(newPointHC)) {
                        // 리스트에 연결점 추가
                        visitied.put(newPointHC, makeEdgeList(pointHC));

                        if(visitied.get(pointHC) == null) { // 기존점도 없다면 업데이트
                            visitied.put(pointHC, makeEdgeList(newPointHC));
                        } else { // 기존점이 있다면 추가하기
                            visitied.get(pointHC).add(newPointHC);
                        }

                        // 재방문했고 간선을 처음 통과하는 경우
                    } else if (visitied.containsKey(newPointHC) && !(visitied.get(newPointHC).contains(pointHC))) {
                        visitied.get(newPointHC).add(pointHC);
                        visitied.get(pointHC).add(newPointHC);
                        cnt++;
                    }

                    // 이동 완료
                    pointHC = newPointHC;
                }
            }

            return cnt;
        }

        // 밸류값에 넣기 위한 리스트 만들기
        public ArrayList<Pair> makeEdgeList(Pair pointHC) {
            ArrayList<Pair> edge = new ArrayList<>();
            edge.add(pointHC);
            return edge;
        }

    }

    /**
     * 테스트 1 〉	실패 (런타임 에러)
     * 테스트 2 〉	통과 (4.10ms, 76.3MB)
     * 테스트 3 〉	실패 (런타임 에러)
     * 테스트 4 〉	실패 (21.30ms, 82.7MB)
     * 테스트 5 〉	실패 (520.67ms, 881MB)
     * 테스트 6 〉	실패 (메모리 초과)
     * 테스트 7 〉	실패 (런타임 에러)
     * 테스트 8 〉	실패 (메모리 초과)
     * 테스트 9 〉	실패 (메모리 초과)
     * @param arrows
     * @param expected
     */
    @Test
    @UseDataProvider("testCase")
    public void solution2(int[] arrows, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution2().solution(arrows), timer.stop());
    }

    class Solution2 {
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
                            } else if (drawHistory.get(i-1).getX() == nextHeight && drawHistory.get(i-1).getY() == nextWidth){
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
