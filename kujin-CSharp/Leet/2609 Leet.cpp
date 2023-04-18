class Solution {
public:
    int findTheLongestBalancedSubstring(string S) {
        int Cnt0=0;
        int Cnt1=0;
        int MaxNum=0;
        int N=S.length();
        for(int i=0;i < N; i++)
        {
            if(i!=0 && S[i-1]=='1' && S[i]=='0')
            {
                Cnt0=1;
                Cnt1=0;
            }
            else{
                if(S[i]=='0')
                {
                    Cnt0++;
                }
                else
                {
                    Cnt1++;
                }
                MaxNum = max(MaxNum,min(Cnt1,Cnt0)*2);
            }
        }
        return MaxNum;
    }
};