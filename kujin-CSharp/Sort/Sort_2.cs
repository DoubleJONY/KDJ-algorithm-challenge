using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using NUnit.Framework;

namespace Algo.Sort
{
    [TestFixture]
    public class Sort_2
    {
        [Test]
        public void Test1()
        {
            // [6, 10, 2]	"6210"
            //     [3, 30, 34, 5, 9]	"9534330"
            int[] input_1 = new int[] { 6, 10, 2 };
            int[] input_2 = new int[] { 3, 30, 34, 5, 9 };
            // Assert.True(solution(input_1) == "6210");
            Assert.True(solution(input_2) == "9534330");

        }
    
        public string solution(int[] numbers) {
            if(numbers[0] == 0 && numbers[1] == 0)
            {
                return "0";
            }
            string answer = "";
            // List<string> test = new List<string>();
            StringBuilder answerBuilder = new StringBuilder(); 
            

            Array.Sort(numbers, (x, y) => 
                string.Compare(y.ToString()+x.ToString(),x.ToString()+y.ToString()));
            for (int i = 0; i < numbers.Length; i++)
            {
                answerBuilder.Append(numbers[i].ToString());
            }
            

            answer = answerBuilder.ToString();
            
            return answer;
        }
    }
}