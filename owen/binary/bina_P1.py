def solution(n, times):
    answer = 0

    left, right = 1, max(times) * n
    
    while left <= right:
        mid = (left+ right) // 2
        people = 0
        people = sum(list([mid // time for time in times]))
        
        
        if people >= n:
            answer = mid
            right = mid - 1
        
        elif people < n:
            left = mid + 1
            
    return answer



# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.11ms, 10.2MB)
# 테스트 3 〉	통과 (3.97ms, 10.2MB)
# 테스트 4 〉	통과 (316.09ms, 18.4MB)
# 테스트 5 〉	통과 (422.22ms, 18.5MB)
# 테스트 6 〉	통과 (335.27ms, 18.7MB)
# 테스트 7 〉	통과 (629.73ms, 18.5MB)
# 테스트 8 〉	통과 (682.34ms, 18.5MB)
# 테스트 9 〉	통과 (0.05ms, 10.2MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0