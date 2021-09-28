using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using NUnit.Framework;

namespace Algo.Brutal
{
    [TestFixture]
    public class Brutal_1
    {
        [Test]
        public void Test1()
        {
           //[1,2,3,4,5]	[1]
           //[1,3,2,4,2]	[1,2,3]
           //[5, 5, 5, 1, 4, 1] -> [1,3]
           int[] input_1 = new int[] { 1,2,3,4,5 };
           int[] answer_1 = new int[] { 1 };
           int[] input_2 = new int[] { 1,3,2,4,2 };
           int[] answer_2 = new int[] { 1,2,3 }; 
           int[] input_3 = new int[] { 5, 5, 5, 1, 4, 1 };
           int[] answer_3 = new int[] {1,3};
            // Assert.True(solution(input_1) == answer_1);
            // Assert.True(solution(input_2) == answer_2);
            Assert.True(solution(input_3) == answer_3);
        }

        public int[] solution(int[] answers)
        {
            List<int> answer = new List<int>();

            int index_1 = 0;
            int index_2 = 0;
            int index_3 = 0;
            int correct_1 = 0;
            int correct_2 = 0;
            int correct_3 = 0;
            int[] student_1 = new[] { 1, 2, 3, 4, 5 };
            int[] student_2 = new[] { 2, 1, 2, 3, 2, 4, 2, 5 };
            int[] student_3 = new[] { 3, 3, 1, 1, 2, 2, 4, 4, 5, 5 };

            for (int i = 0; i < answers.Length; i++)
            {
                if (student_1[index_1] == answers[i])
                {
                    correct_1++;
                }
                if (student_2[index_2] == answers[i])
                {
                    correct_2++;
                }
                if (student_3[index_3] == answers[i])
                {
                    correct_3++;
                }

                index_1 = (index_1 + 1) % student_1.Length;
                index_2 = (index_2 + 1) % student_2.Length;
                index_3 = (index_3 + 1) % student_3.Length;

            }

            answer.Add(correct_1);
            answer.Add(correct_2);
            answer.Add(correct_3);

            int max = answer.Max();
            answer.Clear();
            if (max == correct_1)
            {
                answer.Add(1);
            }
            if (max == correct_2)
            {
                answer.Add(2);
            }
            if (max == correct_2)
            {
                answer.Add(3);
            }
            
            return answer.ToArray();
        }
    }
}