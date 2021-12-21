using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using NUnit.Framework;

namespace Algo.Greedy
{
    [TestFixture]
    public class Greedy_5
    {
        [Test]
        public void Test1()
        {
            // 4	[[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]]	4
            int n_1 = 4;
            int[,] arr_1 = new int[,] { { 0, 1, 1 }, { 0, 2, 2 }, { 1, 2, 5 }, { 1, 3, 1 }, { 2, 3, 8 } };
            Assert.True(solution(n_1, arr_1) == 4);
        }

        public int solution(int n, int[,] costs)
        {
            int[,] distance = new int[n, n];
            int i, j;

            for (i = 0; i < n; i++)
            {
                for (j = 0; j < n; j++)
                {
                    distance[i, j] = -1;
                }
            }

            for (i = 0; i < costs.GetLength(0); i++)
            {
                distance[costs[i, 0], costs[i, 1]] = costs[i, 2];
                distance[costs[i, 1], costs[i, 0]] = costs[i, 2];
            }

            List<int> ans = new List<int>();
            ans.Add(0);
            int min;
            int minIdx = -1;
            int answer = 0;
            while (ans.Count < n)
            {
                min = 9999999;
                foreach (int it in ans)
                {
                    for (i = 0; i < n; i++)
                    {
                        if (ans.Contains(i))
                            continue;
                        if (distance[it, i] != -1 && distance[it, i] < min)
                        {
                            min = distance[it, i];
                            minIdx = i;
                        }
                    }
                }

                answer += min;
                ans.Add(minIdx);
            }

            return answer;
        }


        //DFS에서 재활용
        // startNodeAndCost[0] = int.MaxValue;
        //
        //
        // int temp = 0;
        // int minmumCost = 0;
        // while ((temp++) != 100)
        // {
        //   
        //     // if ()
        //     // {
        //     //     
        //     // }
        //     
        //     //경유지 넣기
        //     transit.Push(node);
        //     //방문 처리
        //     alreadyCheck[node] = true;
        //
        //     minmumCost = int.MaxValue;
        //
        //     connectNodes = costs.Where(x => x.Item1 == node);
        //     
        //     foreach (var connect in connectNodes)
        //     {
        //         //방문하지 않는 노드를 최소신장으로 감 
        //         if (connect.Item3 < minmumCost && alreadyCheck[connect.Item2] == false)
        //         {
        //             minmumCost = connect.Item3;
        //             node = connect.Item2;
        //         }
        //         
        //         //코스트 넣기
        //         if (connect.Item2 != 0)
        //         {
        //             if (startNodeAndCost[connect.Item2] == -1 || connect.Item3 < startNodeAndCost[connect.Item2])
        //             {
        //                 startNodeAndCost[connect.Item2] = connect.Item3;
        //             }
        //         }
        //     }
        //
        //     if (node == transit.Peek())
        //     {
        //         node = transit.Pop();
        //     }
        //     
        //     
        //
        //
        //
        // }
        //
        //
        //
    }
}