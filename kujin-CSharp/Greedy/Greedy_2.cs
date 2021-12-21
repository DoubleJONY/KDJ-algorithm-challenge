using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
namespace Algo.Greedy
{
    [TestFixture]
    public class Greedy_2
    {
        [Test]
        public void Test1()
        {
            // "JEROEN"	56
            // "JAN"	23
            string name_1 = "JEROEN";
            string name_2 = "JAN";
            Assert.True(solution(name_1) == 56);
            Assert.True(solution(name_2) == 23);
            // Assert.True(solution(n2, k2) == "3234");
            // Assert.True(solution(n3, k3) == "775841");
          
        }
        public int solution(string name) {
            int answer = 0;
            //알파벳 갯수 26개
            List<char> nameSpellingList = new List<char>();
            foreach (var nameSpelling in name)
            {
                nameSpellingList.Add(nameSpelling);

            }

            int min = nameSpellingList.Count - 1;
            char c = 'A';
            int nextIndex = 0;
            for (int i = 0; i < nameSpellingList.Count; i++)
            {
                c = nameSpellingList[i];
                if (c - 'A' >  'Z' - c + 1)
                {
                    answer += 'Z' - c + 1;
                }
                else
                {
                    answer += c - 'A';
                }

                nextIndex = i + 1;
                while (nextIndex < nameSpellingList.Count && nameSpellingList[nextIndex] == 'A')
                    nextIndex++;
                
                min = Math.Min(min, (i * 2) + nameSpellingList.Count - nextIndex);

            }



            answer += min;
            
            
            return answer;
        }
    }
}