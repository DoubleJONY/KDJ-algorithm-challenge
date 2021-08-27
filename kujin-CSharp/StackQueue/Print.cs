using System.Collections;
using System.Collections.Generic;
using System.Linq;
using NUnit.Framework;

namespace PS
{
    [TestFixture]
    public class Print
    {
        [Test]
        public void Test_Print()
        {
            int[] inputPriorities_1 = { 2, 1, 3, 2 };
            var location_1 = 2;

            int[] inputPriorities_2 = { 1, 1, 9, 1, 1, 1 };
            var location_2 = 0;

            Assert.True(solution_Print(inputPriorities_1, location_1) == 1);
            Assert.True(solution_Print(inputPriorities_2, location_2) == 5);
            
        }


        public int solution_Print(int[] priorities, int location)
        {
            var answer = 0;
            int arrIndex = 0;
            int inputPriorities = priorities.Max();
            int countPriorities = priorities.Count(x => x == inputPriorities);

            while (true)
            {
                if (priorities[arrIndex] == inputPriorities)
                {
                    answer++;
                    priorities[arrIndex] = 0;
                    if (location == arrIndex)
                    {
                        break;
                    }

                    countPriorities--;
                    if (countPriorities == 0)
                    {
                        inputPriorities = priorities.Max();
                        countPriorities = priorities.Count(x => x == inputPriorities);
                    }
                }

                arrIndex++;
                if (arrIndex == priorities.Length) arrIndex = 0;
            }

           
            return answer;
        }
    }
}