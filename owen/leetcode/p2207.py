class Solution:
    def maximumSubsequenceCount(self, text: str, pattern: str) -> int:
  

        res = 0
        cnt1 = 0
        cnt2 = 0
        for i in range(len(text)): 
            if text[i] == pattern[1]:
                res += cnt1
                cnt2 += 1
            if text[i] == pattern[0]: 
                cnt1 += 1
            
        
        return res + max(cnt1, cnt2)