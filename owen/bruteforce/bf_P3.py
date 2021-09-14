def div_cell(b, y):
    total_cell = b + y

    for i in range(3, int(total_cell ** (1/2) + 1)):
        if total_cell % i == 0:
            out_side = int(total_cell / i)
            one_side = min([out_side, i]) - 2
            other_side = y / one_side
            if (one_side + other_side)*2+4 == b :
                return i,out_side 
    return -1

def solution(brown, yellow):
    one_side, other_side = div_cell(brown, yellow)
    answer = [max([one_side, other_side]), min([one_side, other_side])]
    return answer


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.02ms, 10.3MB)
# 테스트 2 〉	통과 (0.02ms, 10.2MB)
# 테스트 3 〉	통과 (0.09ms, 10.2MB)
# 테스트 4 〉	통과 (0.02ms, 10.2MB)
# 테스트 5 〉	통과 (0.02ms, 10.2MB)
# 테스트 6 〉	통과 (0.04ms, 10.1MB)
# 테스트 7 〉	통과 (0.07ms, 10.2MB)
# 테스트 8 〉	통과 (0.08ms, 10.3MB)
# 테스트 9 〉	통과 (0.08ms, 10.2MB)
# 테스트 10 〉	통과 (0.07ms, 10.2MB)
# 테스트 11 〉	통과 (0.02ms, 10.1MB)
# 테스트 12 〉	통과 (0.01ms, 10.2MB)
# 테스트 13 〉	통과 (0.02ms, 10.2MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0