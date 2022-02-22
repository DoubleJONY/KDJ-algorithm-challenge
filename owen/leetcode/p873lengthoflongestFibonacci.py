class Solution:
    def lenLongestFibSubseq(self, arr: List[int]) -> int:
        s = set(arr)
        answer = 0
        for i in range(len(arr)-1):
            for j in range(i+1, len(arr)):
                answer_tmp = 2
                
                curp1, curp2 = arr[i], arr[j]
                
                while curp1 + curp2 in s:
                    answer_tmp += 1
                    curp1, curp2 = curp2, curp1 + curp2
                    
                answer = max(answer, answer_tmp)
                
        return answer if answer > 2 else 0
    
#a