package com.doublejony.practice.graph;

import com.google.common.base.Stopwatch;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static com.doublejony.common.AssertResolve.resolve;

/**
 * 가장 먼 노드
 * n개의 노드가 있는 그래프가 있습니다. 각 노드는 1부터 n까지 번호가 적혀있습니다. 1번 노드에서 가장 멀리 떨어진 노드의 갯수를 구하려고 합니다. 가장 멀리 떨어진 노드란 최단경로로 이동했을 때 간선의 개수가 가장 많은 노드들을 의미합니다.
 *
 * 노드의 개수 n, 간선에 대한 정보가 담긴 2차원 배열 vertex가 매개변수로 주어질 때, 1번 노드로부터 가장 멀리 떨어진 노드가 몇 개인지를 return 하도록 solution 함수를 작성해주세요.
 *
 * 제한사항
 * 노드의 개수 n은 2 이상 20,000 이하입니다.
 * 간선은 양방향이며 총 1개 이상 50,000개 이하의 간선이 있습니다.
 * vertex 배열 각 행 [a, b]는 a번 노드와 b번 노드 사이에 간선이 있다는 의미입니다.
 * 입출력 예
 * n	vertex	return
 * 6	[[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]	3
 */
@RunWith(DataProviderRunner.class)
public class Graph1 {

    @DataProvider
    public static Object[][] testCase() {
        // @formatter:off
        return new Object[][]{
                {
                        4,
                        new int[][]{{4, 3}, {1, 3}, {2, 3}},
                        2
                },
                {
                        5,
                        new int[][] {{4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}},
                        2
                },
                {
                        5,
                        new int[][] {{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}, {4, 5}},
                        1
                },
                {
                        6,
                        new int[][] {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}},
                        3
                },
                {
                        7,
                        new int[][] {{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}, {6, 7}},
                        1
                },
                {
                        6,
                        new int[][]{{3, 6}, {4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}},
                        3
                },
                {
                        11,
                        new int[][]{{1, 2}, {1, 3}, {2, 4}, {2, 5}, {3, 5}, {3, 6}, {4, 8}, {4, 9}, {5, 9}, {5, 10}, {6, 10}, {6, 11}},
                        4
                },
                {
                        4,
                        new int[][]{{1, 2}, {2, 3}, {3, 4}},
                        1
                },
                {
                        2,
                        new int[][]{{1, 2}},
                        1
                },
                {
                        5,
                        new int[][]{{4, 3}, {3, 2}, {1, 3}, {1, 2}, {2, 4}, {5, 2}},
                        2
                },
                {
                        4,
                        new int[][]{{1, 2}, {1, 3}, {2, 3}, {2, 4}, {3, 4}},
                        1
                },
                {
                        4,
                        new int[][]{{1, 4}, {1, 3}, {2, 3}, {2, 1}},
                        3
                },
                {
                        4,
                        new int[][]{{3, 4}, {1, 3}, {2, 3}, {2, 1}},
                        1
                }
        };
        // @formatter:on
    }

    @Test
    @UseDataProvider("testCase")
    public void solution(int n, int[][] edge, long expected) {

        Stopwatch timer = Stopwatch.createStarted();
        resolve(Thread.currentThread().getStackTrace()[1].getMethodName(), expected, new Solution().solution(n, edge), timer.stop());
    }

    /**
     * 테스트 1 〉	통과 (0.94ms, 75.4MB)
     * 테스트 2 〉	통과 (1.25ms, 85MB)
     * 테스트 3 〉	통과 (1.28ms, 78.5MB)
     * 테스트 4 〉	통과 (2.21ms, 77.5MB)
     * 테스트 5 〉	통과 (13.56ms, 84.3MB)
     * 테스트 6 〉	통과 (25.09ms, 83.4MB)
     * 테스트 7 〉	통과 (2963.81ms, 112MB)
     * 테스트 8 〉	통과 (5746.76ms, 104MB)
     * 테스트 9 〉	통과 (5752.78ms, 115MB)
     */
    class Solution {
        public int solution(int n, int[][] edge) {
            int answer = 0;

//            for (int i = 0; i < edge.length; i++) {
//                if (edge[i][0] > edge[i][1]) {
//                    int temp = edge[i][0];
//                    edge[i][0] = edge[i][1];
//                    edge[i][1] = temp;
//                }
//            }

//            Arrays.sort(edge, (o1, o2) -> {
//                if (o1[0] == o2[0]) {
//                    return o1[1] - o2[1];
//                } else {
//                    return o1[0] - o2[0];
//                }
//            });

            int[] distance = new int[n+1];
            distance[1] = 1;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(1);

            while(!queue.isEmpty()) {
                int child = queue.poll();
                for (int[] i : edge) {
                    if (i[0] == child && distance[i[1]] == 0) {
                        queue.add(i[1]);
                        if (distance[i[1]] == 0) {
                            distance[i[1]] += distance[i[0]] + 1;
                        }
                    } else if (i[1] == child && distance[i[0]] == 0) {
                        queue.add(i[0]);
                        if (distance[i[0]] == 0) {
                            distance[i[0]] += distance[i[1]] + 1;
                        }
                    }
                }
            }

            int max = Arrays.stream(distance).max().getAsInt();
            for (int a : distance) {
                if(a == max) {
                    answer++;
                }
            }

            return answer;
        }
    }
}
