def solution(m, n, puddles):
    puddles = [[p,q] for [q,p] in puddles]
    dp_map = [[0] * (m+1) for i in range(n+1)]
    dp_map[1][1] = 1
    
    for i in range(1, n+1):
        for j in range(1, m+1):
            if i==1 and j==1: 
                continue
            if [i, j] in puddles:
                dp_map[i][j] = 0
            else:
                dp_map[i][j] = dp_map[i-1][j] + dp_map[i][j-1]
    dp_map[n][m] = dp_map[n][m] % 1000000007
    return dp_map[n][m]

print(solution(4,3, [[2,2]]))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.3MB)
# 테스트 2 〉	통과 (0.01ms, 10.3MB)
# 테스트 3 〉	통과 (0.03ms, 10.3MB)
# 테스트 4 〉	통과 (0.04ms, 10.3MB)
# 테스트 5 〉	통과 (0.05ms, 10.3MB)
# 테스트 6 〉	통과 (0.08ms, 10.3MB)
# 테스트 7 〉	통과 (0.04ms, 10.3MB)
# 테스트 8 〉	통과 (0.10ms, 10.2MB)
# 테스트 9 〉	통과 (0.05ms, 10.2MB)
# 테스트 10 〉	통과 (0.03ms, 10.3MB)
# 효율성  테스트
# 테스트 1 〉	통과 (4.31ms, 10.4MB)
# 테스트 2 〉	통과 (1.13ms, 10.3MB)
# 테스트 3 〉	통과 (1.61ms, 10.2MB)
# 테스트 4 〉	통과 (2.11ms, 10.3MB)
# 테스트 5 〉	통과 (1.41ms, 10.3MB)
# 테스트 6 〉	통과 (3.39ms, 10.4MB)
# 테스트 7 〉	통과 (1.51ms, 10.1MB)
# 테스트 8 〉	통과 (2.55ms, 10.3MB)
# 테스트 9 〉	통과 (2.92ms, 10.3MB)
# 테스트 10 〉	통과 (1.80ms, 10.2MB)
# 채점 결과
# 정확성: 50.0
# 효율성: 50.0
# 합계: 100.0 / 100.0