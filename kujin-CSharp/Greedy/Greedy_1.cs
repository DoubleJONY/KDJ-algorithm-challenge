using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Algo.Greedy
{
    [TestFixture]
    public class Greedy_1
    {
        [Test]
        public void Test1()
        {
            // 5	[2, 4]	[1, 3, 5] 5
            int n_1 = 5;
            int[] lost = new int[] { 2, 4 };
            int[] reserve = new int[] { 1, 3, 5 };
            //5	[2, 4]	[3]	4
            int n_2 = 5;
            int[] lost_2 = new int[] { 2, 4 };
            int[] reserve_2 = new int[] {  3 };
            //3	[3]	[1]	2
            int n_3 = 4;
            int[] lost_3 = new int[] { 3, 3, 3 };
            int[] reserve_3 = new int[] {  3 };
            Assert.True(solution(n_1, lost, reserve) == 5);
            Assert.True(solution(n_2, lost_2, reserve_2) == 4);
            Assert.True(solution(n_3, lost_3, reserve_3) == 2);
        }

        public int solution(int n, int[] lost, int[] reserve)
        {
            int answer = n;
            List<int> reserveList = new List<int>();
            List<int> lostList = new List<int>();
            foreach (int item in reserve)
            {
                reserveList.Add(item);
            }
            foreach (var item in lost)
            {
                if (reserveList.Contains(item) == true)
                {
                    reserveList.Remove(item);
                  
                }
                else
                {
                    lostList.Add(item);
                }
            }
            lostList.Sort();
            lostList.Reverse();
            foreach (var lostItem in lostList)
            {
                if (reserveList.Contains(lostItem + 1) == true)
                {
                    reserveList.Remove((lostItem + 1));
                    continue;
                }
                else if (reserveList.Contains(lostItem) == true)
                {
                    reserveList.Remove((lostItem));
                    continue;
                }
                else if (reserveList.Contains(lostItem - 1) == true)
                {
                    reserveList.Remove((lostItem - 1));
                    continue;
                }
                else
                {
                    answer--;
                }
            }
           
            
            
            
            return answer;
        }
    }
}