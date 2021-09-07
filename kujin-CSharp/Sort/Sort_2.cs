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
            
            // StringBuilder answerBuilder = new StringBuilder(); 
            // Array.Sort(numbers);
            //
            //
            // Dictionary<int, List<int>> makeBigNum = new Dictionary<int, List<int>>();
            // makeBigNum.Add(9,new List<int>());
            // makeBigNum.Add(8,new List<int>());
            // makeBigNum.Add(7,new List<int>());
            // makeBigNum.Add(6,new List<int>());
            // makeBigNum.Add(5,new List<int>());
            // makeBigNum.Add(4,new List<int>());
            // makeBigNum.Add(3,new List<int>());
            // makeBigNum.Add(2,new List<int>());
            // makeBigNum.Add(1,new List<int>());
            // makeBigNum.Add(0,new List<int>());
            //
            // foreach (var num in numbers)
            // {
            //     int temp = num;
            //     while (temp > 9)
            //     {
            //         temp /= 10;
            //        
            //     }
            //     makeBigNum[temp].Add(num);
            // }
            //
            // foreach (var list in makeBigNum)
            // {
            //     foreach (var num in list.Value)
            //     {
            //         answerBuilder.Append(num.ToString());
            //     }
            // }



            answer = answerBuilder.ToString();
            
            return answer;
        }
    }
}