using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using NUnit.Framework;


namespace Algo
{
    [TestFixture]
    public class heap3
    {
        public void Test1()
        {
            // {{0, 3}, {1, 9}, {2, 6}, };
            string[] input_1 = new string[] { "I 16","D 1" };
           
            //[[0, 1], [1, 1], [50, 7]]
            // Assert.True(solution(input_1) == [0,0]);
           

        }

        public int[] solution(string[] operations) {
           int[] answer = new int[2];
            List<int> arr = new List<int>();
            foreach (var oper in operations)
            {
                switch (oper)
                {
                    case "D 1":
                        if (arr.Count != 0)
                        {
                            arr.RemoveAt(arr.IndexOf(arr.Max()));
                        }

                        break;
                    case "D -1":
                        if (arr.Count != 0)
                        {
                            arr.RemoveAt(arr.IndexOf(arr.Min()));
                        }

                        break;
                    default:
                        arr.Add(Convert.ToInt32(oper.Substring(2)));
                        break;
                }
            }
            if(arr.Count != 0)
            {
                answer[0] = arr.Max();
                answer[1] = arr.Min();
            }
            else
            {
                answer[0] = 
                    answer[1] = 0;
            }
            
            
            return answer;
        }
    }
}