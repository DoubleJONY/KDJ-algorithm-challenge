def solution(triangle):
    for i in range(1, len(triangle)):
        for j in range(i+1):
            if j == 0:
                triangle[i][j] += triangle[i-1][j]
            elif j == i:
                triangle[i][j] += triangle[i-1][j-1]
            else:
                triangle[i][j] += max(triangle[i-1][j], triangle[i-1][j-1])
    return max(triangle[-1])


print(solution([[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.03ms, 10.2MB)
# 테스트 3 〉	통과 (0.04ms, 10.3MB)
# 테스트 4 〉	통과 (0.15ms, 10.2MB)
# 테스트 5 〉	통과 (2.13ms, 10.3MB)
# 테스트 6 〉	통과 (0.30ms, 10.4MB)
# 테스트 7 〉	통과 (1.05ms, 10.3MB)
# 테스트 8 〉	통과 (0.25ms, 10.3MB)
# 테스트 9 〉	통과 (0.01ms, 10.3MB)
# 테스트 10 〉	통과 (0.14ms, 10.3MB)
# 효율성  테스트
# 테스트 1 〉	통과 (36.41ms, 14.3MB)
# 테스트 2 〉	통과 (28.32ms, 13.2MB)
# 테스트 3 〉	통과 (40.82ms, 14.7MB)
# 테스트 4 〉	통과 (35.17ms, 14.2MB)
# 테스트 5 〉	통과 (32.67ms, 13.8MB)
# 테스트 6 〉	통과 (42.99ms, 14.8MB)
# 테스트 7 〉	통과 (39.87ms, 14.5MB)
# 테스트 8 〉	통과 (32.32ms, 13.7MB)
# 테스트 9 〉	통과 (35.24ms, 13.9MB)
# 테스트 10 〉	통과 (38.07ms, 14.4MB)
# 채점 결과
# 정확성: 64.3
# 효율성: 35.7
# 합계: 100.0 / 100.0