using NUnit.Framework;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Algo.Brutal
{
    [TestFixture]
    public class Brutal_2
    {
         [Test]
        public void Test1()
        {
            // "17"	3
            // "011"	2
            string input_1 = "17";
            string input_2 = "011";
            Assert.True(solution(input_1) == 3);
            Assert.True(solution(input_2) == 2);
        }

        // private static IEnumerable<string> FindPermutations(string set)
        // {
        //     var output = new List<string>();
        //     switch (set.Length)
        //     {
        //         case 1:
        //             output.Add(set);
        //             break;
        //         default:
        //             output.AddRange(from c in set let tail = set.Remove(set.IndexOf(c), 1) from tailPerms in FindPermutations(tail) select c + tailPerms);
        //             break;
        //     }
        //     return output;
        // }
    
       
        public bool isPrime(int n)
        {
            if(n != 0 && n != 1)
            {  
                for(long i = 2; i*i <= n; i++)
                {
                    if(n % i == 0) return false;
                }

                return true;
            }

            return false;
        }
        
        private static IEnumerable<string> FindPermutations(string set)
        {
            var output = new List<string>();
            switch (set.Length)
            {
                case 1:
                    output.Add(set);
                    break;
                default:
                    output.AddRange(from c in set let tail = set.Remove(set.IndexOf(c), 1) from tailPerms in FindPermutations(tail) select c + tailPerms);
                    break;
            }
            return output;
        }
        
        public int solution(string numbers) {
            int answer = 0;
            // List<string> lstNums = new List<string>();
            // foreach (var nChar in numbers)
            // {
            //     lstNums.Add(nChar.ToString());
            // }
            List<string> lstIntermediate  = FindPermutations(numbers).ToList();
            List<long> lstNumbers = new List<long>();
            for (int i = 0; i < numbers.Length; i++)
            {
                foreach (long lNum in lstIntermediate.Select(x => long.Parse(x.Substring(i))))
                {
                    lstNumbers.Add(lNum);
                }
            }
            lstNumbers = lstNumbers.Distinct().ToList();
          
            foreach (var num in lstNumbers)
            {
                if (isPrime((int)num)) answer++;
            }
            
            return answer;
        }
    
        
        
    }
    /*
     * int solution(int n, int k) {
    int answer = 0;

    string convertNum = "";
   
    if(k != 10)
    {
        while(n != 0)
        {
            int reminder = n % k;
            convertNum = (to_string(reminder)) + convertNum;
            n /= k;
        }
    }
    else
    {
         convertNum.append(to_string(n));
    }
        
    istringstream ss(convertNum);
    string numString = "";
    bool isPrime = true;
    while(getline(ss, numString, '0'))
    {
        isPrime = true;
        long num = atol(numString.c_str());

       if(num != 0 && num != 1)
        {  
            for(long i = 2; i*i <= num; i++)
            {
                if(num % i == 0) isPrime = false;
            }

            if(isPrime == true)
                answer++;
        }   
    }
    
    
    return answer;
}
     */
}