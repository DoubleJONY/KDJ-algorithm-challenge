using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using NUnit.Framework;


namespace Algo.Sort
{
    [TestFixture]
    public class Sort_1
    {
        [Test]
        public void Test1()
        {
            // [1, 5, 2, 6, 3, 7, 4]	[[2, 5, 3], [4, 4, 1], [1, 7, 3]]	[5, 6, 3]
            int[] arr_1 = new int[] { 1, 5, 2, 6, 3, 7, 4 };
            int[,] commands_1 = new int[,] { { 2, 5, 3 }, { 4, 4, 1 }, { 1, 7, 3 } };
            int[] test_1 = solution(arr_1, commands_1);
            int[] answer_1  = new int[] { 5,6,3};
            for (int i = 0; i < answer_1.Length; i++)
            {
                Assert.True(answer_1[i] == test_1[i]);
            }
        }

        public int[] solution(int[] array, int[,] commands)
        {
            int temp = 0;
            int[] answer = new int[commands.GetLength(0)];
            List<int> list = new List<int>();
            for (int commandIndex = 0; commandIndex < commands.GetLength(0); commandIndex++)
            {
                for (int i = commands[commandIndex, 0] - 1; i < commands[commandIndex, 1]; i++)
                {
                    list.Add(array[i]);
                    for (int listNum = list.Count - 1; listNum > 0; listNum--)
                    {
                        if (list[listNum] < list[listNum - 1])
                        {
                            temp = list[listNum];
                            list[listNum] = list[listNum - 1];
                            list[listNum - 1] = temp;
                        }
                        else
                        {
                            break;
                        }
                    }
                    
                }

                answer[commandIndex] = list[commands[commandIndex, 2] - 1];
                list.Clear();
                
            }


            return answer;
            //삽입정렬 방금 넣은거만 정렬함
        }
    }
}