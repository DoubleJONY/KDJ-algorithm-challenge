using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

public class Solution {
    public int solution(string[,] clothes) {
         Dictionary<string, int> dic = new Dictionary<string, int>();
        int answer = 0;
        
        for (int i = 0; i < clothes.Length/clothes.Rank; i++)
        {
            if (dic.ContainsKey(clothes[i,1]) == false)
            {
                dic.Add(clothes[i,1], 1);
            }
            else
            {
                dic[clothes[i, 1]] += 1;
            }
        }

        for (int i = 1; i <= dic.Count; i++)
        {
            if (i == 1)
            {
                foreach (var item in dic.Select(x=>x.Value))
                {
                    answer += item;
                }
            }
            else
            {
                answer += Combination(dic.Select(x => x.Value).ToArray(), new int[i], 0, 0);
            }
        }

       
        return answer;
    }
    
    public int Combination(int[] arr, int[] comb, int index, int depth)
    {
        if (depth == comb.Length)
        {
            int last = 0;
            
            return 1;
        }
        else
        {
            int sum = 0;
            for (int i = index; i < arr.Length; i++)
            {
                comb[depth] = arr[index];
                sum += Combination(arr, comb, index + 1, depth + 1);
            }

            return sum;
        }

      
    }
    
}