using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Algo.Greedy
{
    [TestFixture]
    public class Greedy_3
    {
        [Test]
        public void Test1()
        {
            // 1924"	2	"94"
            // "1231234"	3	"3234"
            // "4177252841"	4	"775841"
            string n1 = "1924";
            int k1 = 2;
            string n2 = "1231234";
            int k2 = 3;
            string n3 = "4177252841";
            int k3 = 4;
            
            Assert.True(solution(n1, k1) == "94");
            // Assert.True(solution(n2, k2) == "3234");
            // Assert.True(solution(n3, k3) == "775841");
          
        }

        public string solution(string number, int k) {
            int numSize = number.Length - k;
            List<int> temp = new List<int>(numSize);
            int start = 0;
            for(int i=0; i<numSize; i++) 
            {
                char maxNum = number[start];
                int maxIdx = start;
                for(int j=start; j<=k+i; j++) 
                {
                    if(maxNum < number[j]) 
                    {
                        maxNum = number[j];
                        maxIdx = j;
                    }
                }
                start = maxIdx + 1;
                temp.Add((int)Char.GetNumericValue(maxNum));
            }
            string answer = string.Join("",temp);
            return answer;
        }
       
    }
    
}