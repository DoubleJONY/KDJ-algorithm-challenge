namespace TestApp1;

public class M873
{
    public int LenLongestFibSubseq(int[] arr)
    {
        int arrLeght = arr.Length;

        if (arrLeght < 3) return 0;
        
        HashSet<int> hashSet = new HashSet<int>();

        foreach (var i in arr)
        {
            hashSet.Add(i);
            
        }

        
        
        int maxLongSeq = 0;
        
        for (int i = 0; i < arrLeght; i++)
        {87
            for (int j = i + 1; j < arrLeght; j++)
            {
                int x = arr[j];
                int y = arr[j] + arr[i];
                //2ㄱㅐ는 기본으로 시작
                int currentLenght = 2;
                while (hashSet.Contains(y))
                {
                    int temp = y;
                    y = y + x;
                    x = temp;
                    currentLenght++;
                    maxLongSeq = Math.Max(maxLongSeq, currentLenght);
                }
            }
            
            
        }

        if (maxLongSeq > 2)
        {
            return maxLongSeq;
        }
        else
        {
            return 0;     
        }
       
    }
}