def solution(array, commands):
    answer = []

    for i in commands:
        tmp_ = array[i[0]-1:i[1]]
        tmp_.sort()

        answer.append(tmp_[i[2]-1])

    return answer



print(solution([1, 5, 2, 6, 3, 7, 4], [[2, 5, 3], [4, 4, 1], [1, 7, 3]]))
print(solution([1, 5, 2, 6, 3, 7, 4], [[2, 5, 3], [4, 10, 1], [1, 7, 3]]))




# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.00ms, 10.2MB)
# 테스트 2 〉	통과 (0.00ms, 9.99MB)
# 테스트 3 〉	통과 (0.00ms, 10.1MB)
# 테스트 4 〉	통과 (0.00ms, 10.2MB)
# 테스트 5 〉	통과 (0.00ms, 10.2MB)
# 테스트 6 〉	통과 (0.00ms, 10.1MB)
# 테스트 7 〉	통과 (0.00ms, 10.1MB)
# 채점 결과
# 정확성: 100.0