using System.Collections;
using System.Collections.Generic;
using System;
using System.Linq;

public class Solution {
    public int[] solution(string[] genres, int[] plays) {
        Dictionary<string, int> sumGenres = new Dictionary<string, int>();
        Dictionary<int, string> dic = new Dictionary<int, string>();
        
        for (int i = 0; i < genres.Length; i++)
        {
            if (sumGenres.ContainsKey(genres[i]))
            {
                sumGenres[genres[i]] += plays[i];
                
            }
            else
            {
                sumGenres.Add(genres[i], plays[i]);
            }

            
        }

        List<int> answerList = new List<int>();
        int selectSongIndex_1 = 0;
        int selectSongIndex_2 = 0;
        int selectSongPlay_1 = 0;
        int selectSongPlay_2 = 0;

        foreach (var orderGenres in sumGenres.OrderByDescending(x=> x.Value))
        {
            selectSongIndex_1 = -1;
            selectSongIndex_2 = -1;
            selectSongPlay_1 = 0;
            selectSongPlay_2 = 0;
            for (int i = 0; i < genres.Length; i++)
            {
                if (genres[i] == orderGenres.Key)
                {
                    if (selectSongIndex_1 == -1)
                    {
                        selectSongIndex_1 = i;
                        selectSongPlay_1 = plays[i];
                      
                    }
                    else if (selectSongPlay_1 < plays[i])
                    {
                         selectSongIndex_2 = selectSongIndex_1;
                        selectSongPlay_2 = selectSongPlay_1;

                         selectSongIndex_1 = i;
                        selectSongPlay_1 = plays[i];
                    }
                    else if (selectSongPlay_2< plays[i])
                    {
                       selectSongIndex_2 = i;
                        selectSongPlay_2 = plays[i];
                    }
                }
            }

            if(selectSongIndex_1 != -1) answerList.Add(selectSongIndex_1);
            if(selectSongIndex_2 != -1) answerList.Add(selectSongIndex_2);
        }
    
        return answerList.ToArray();
    }
}