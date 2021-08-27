using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using NUnit.Framework;

namespace PS
{
    [TestFixture]
    public class Solution
    {
        [Test]
        public void Test_Productlocation()
        {
            int[] inputProgress = new int[] { 99, 99, 55 ,1, 2};
            int[] inputSpeeds = new int[] {1, 1, 5, 90, 50};
   
            Assert.True(solution_Product(inputProgress, inputSpeeds)[0] == 1);
        }
        
       
        public int[] solution_Product(int[] progresses, int[] speeds) 
        {
            List<int> answer = new List<int>();
            List<int> progressesList = progresses.ToList();
            List<int> speedList = speeds.ToList();

            int returnJob = 0;
            while (progressesList.Count != 0)
            {
                for (int i = 0; i < progressesList.Count; i++)
                {
                    progressesList[i] += speedList[i];

                   
                   
                }
                while (progressesList.Count != 0 && progressesList[0] > 99 )
                {
                    returnJob++;
                    progressesList.RemoveAt(0);
                    speedList.RemoveAt(0);
                }

                if (returnJob > 0)
                {
                    answer.Add(returnJob);
                    returnJob = 0;
                }
           
            }

            return answer.ToArray();
        }
    }
}