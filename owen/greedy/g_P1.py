def solution(n, lost, reserve):

    set_res = set(reserve) - set(lost)
    set_lost = set(lost) - set(reserve)
    for num in set_res:
        if num - 1 in set_lost:
            set_lost.remove(num - 1)
        elif num + 1 in set_lost:
            set_lost.remove(num + 1)
    return n - len(set_lost)




print(5, [2, 4], [ 1, 3, 5] )



# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.1MB)
# 테스트 2 〉	통과 (0.01ms, 10.1MB)
# 테스트 3 〉	통과 (0.01ms, 10MB)
# 테스트 4 〉	통과 (0.01ms, 10.2MB)
# 테스트 5 〉	통과 (0.01ms, 10.2MB)
# 테스트 6 〉	통과 (0.01ms, 10.1MB)
# 테스트 7 〉	통과 (0.01ms, 10MB)
# 테스트 8 〉	통과 (0.01ms, 10.1MB)
# 테스트 9 〉	통과 (0.01ms, 10.2MB)
# 테스트 10 〉	통과 (0.01ms, 10.1MB)
# 테스트 11 〉	통과 (0.01ms, 10.2MB)
# 테스트 12 〉	통과 (0.00ms, 10.1MB)
# 테스트 13 〉	통과 (0.01ms, 10.1MB)
# 테스트 14 〉	통과 (0.01ms, 10MB)
# 테스트 15 〉	통과 (0.01ms, 10MB)
# 테스트 16 〉	통과 (0.01ms, 10.2MB)
# 테스트 17 〉	통과 (0.01ms, 10.1MB)
# 테스트 18 〉	통과 (0.01ms, 10.2MB)
# 테스트 19 〉	통과 (0.01ms, 10.2MB)
# 테스트 20 〉	통과 (0.01ms, 10.2MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0