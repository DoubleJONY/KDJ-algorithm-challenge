using NUnit.Framework;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

namespace PS
{
    [TestFixture]
    public class Stock
    {
        [Test]
        public void Test_Stock()
        {
            int[] prices = { 1, 2, 3, 2, 3 };
           


            Assert.True(solution(prices) == null);
           
            
        }


        public int[] solution(int[] prices) {
            int[] answer = new int[prices.Length];
           
           

            for (int i = 0; i < prices.Length; i++)
            {
                for (int j = i+1; j < prices.Length; j++)
                {
                   
                        if (prices[j] < prices[i])
                        {
                            answer[i] = j - i;
                            break;
                        }

                        if (j == prices.Length - 1) answer[i] = j - i;
                }
            }


         
            return answer;
        }
    
        
        public int[] solution_old(int[] prices) {
            int[] answer = null;
            Queue<KeyValuePair<int, int>> stocks = new Queue<KeyValuePair<int, int>>();
            KeyValuePair<int, int> temp;
            for (int i = 0; i < prices.Length; i++)
            {
                if (stocks.Count != 0)
                {
                    for (int j = 0; j < stocks.Count; j++)
                    {
                        temp = stocks.Dequeue();

                        if (temp.Value >= 0)
                        {
                            if (temp.Key <= prices[i])
                            {
                                stocks.Enqueue(new KeyValuePair<int, int>(temp.Key, temp.Value + 1));
                            }
                            else
                            {
                                stocks.Enqueue(new KeyValuePair<int, int>(temp.Key, -(temp.Value + 1)));
                            }
                        }
                        else
                        {
                            stocks.Enqueue(temp);
                        }
                    }
                    
                   
                    
                }
                stocks.Enqueue(new KeyValuePair<int, int>(prices[i],0));
            }

            answer = stocks.Select(x => x.Value).ToArray();

            for (int i = 0; i < answer.Length; i++)
            {
                if (answer[i] < 0) answer[i] *= -1;

            }
            

            return answer;
        }
    }
    
  
}