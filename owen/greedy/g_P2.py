def solution(name):
    ord_list = [min(ord(i)-ord("A"), ord("Z")- ord(i) + 1) for i in name]
    answer = sum(ord_list)
    idx = 0


    while True:
        ord_list[idx] = 0
        if sum(ord_list) == 0:
            return answer

        Left, Right = 1, 1
        while ord_list[idx - Left] == 0:
            Left += 1
        while ord_list[idx + Right] == 0:
            Right += 1

        answer += min( Left, Right)
        idx += - Left if Left < Right else Right






print(solution("JEROEN"))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.1MB)
# 테스트 2 〉	통과 (0.02ms, 10.1MB)
# 테스트 3 〉	통과 (0.01ms, 10.1MB)
# 테스트 4 〉	통과 (0.02ms, 10.2MB)
# 테스트 5 〉	통과 (0.02ms, 10.2MB)
# 테스트 6 〉	통과 (0.01ms, 10.2MB)
# 테스트 7 〉	통과 (0.03ms, 10.3MB)
# 테스트 8 〉	통과 (0.01ms, 10.2MB)
# 테스트 9 〉	통과 (0.01ms, 10.2MB)
# 테스트 10 〉	통과 (0.01ms, 10.2MB)
# 테스트 11 〉	통과 (0.01ms, 10MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0