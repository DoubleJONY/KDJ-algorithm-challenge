def dfs(idx, computers):
    computers[idx][idx] = -1
    for i in range(len(computers[idx])):
        if computers[idx][i] == 1 and computers[i][i]!= -1:
            dfs(i, computers)


def solution(n, computers):
    answer = 1
    

    for i in range(n):
        if computers[i][i] == 1:
            dfs(i, computers)
            answer+=1

    
    return answer



print(solution(3, [[1, 1, 0], [1, 1, 0], [0, 0, 1]]))
print(solution(3, [[1, 1, 0], [1, 1, 1], [0, 1, 1]]))
print(solution(5, [[1, 1, 0,0,1], [1, 1, 0,0,0], [0,0, 1, 0,0],[0,0,0,1,0],[1,0,0,0,1]]))
print(solution(4, [[1, 1, 0, 1], [1, 1, 0, 0], [0, 0, 1, 1], [1, 0, 1, 1]]))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.01ms, 10.2MB)
# 테스트 3 〉	통과 (0.05ms, 10.2MB)
# 테스트 4 〉	통과 (0.05ms, 10.2MB)
# 테스트 5 〉	통과 (0.00ms, 10.3MB)
# 테스트 6 〉	통과 (0.16ms, 10.3MB)
# 테스트 7 〉	통과 (0.01ms, 10.2MB)
# 테스트 8 〉	통과 (0.17ms, 10.4MB)
# 테스트 9 〉	통과 (0.07ms, 10.1MB)
# 테스트 10 〉	통과 (0.14ms, 10.3MB)
# 테스트 11 〉	통과 (0.50ms, 10.3MB)
# 테스트 12 〉	통과 (0.45ms, 10.2MB)
# 테스트 13 〉	통과 (0.29ms, 10.2MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0