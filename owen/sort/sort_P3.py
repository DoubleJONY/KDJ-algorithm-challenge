import collections

def solution(citations):
    
    citations.sort()
    l = len(citations)

    for i in range(l):
        if citations[i] >= l-i:
            
            return l-i
    return 0
        




print(solution([3, 0, 6, 1, 5]))
print(solution([5,5,5,5]))
print(solution([20,19,18,1]))
print(solution([22,42]))
print(solution([0,0,0]))
print(solution([1,4]))