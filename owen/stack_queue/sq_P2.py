def solution(priorities, location):

    d2_list = [[v, i] for i, v in enumerate(priorities)]
    print(d2_list)
    target = location
    out_rank = 0
    while len(d2_list) > 0:
        tmp = d2_list[0]
        H_rank = max([i[0] for i in d2_list])

        if tmp[0] == H_rank:
            del d2_list[0]
            out_rank += 1
            if tmp[1] == target:
                return out_rank
        else:
            d2_list.append(d2_list[0])
            del d2_list[0]
            




print(solution([2, 1, 3, 2], 2))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.24ms, 10.2MB)
# 테스트 2 〉	통과 (3.07ms, 10.2MB)
# 테스트 3 〉	통과 (0.16ms, 10.2MB)
# 테스트 4 〉	통과 (0.07ms, 10.2MB)
# 테스트 5 〉	통과 (0.01ms, 10.2MB)
# 테스트 6 〉	통과 (0.19ms, 10.1MB)
# 테스트 7 〉	통과 (0.20ms, 10.2MB)
# 테스트 8 〉	통과 (1.16ms, 10.2MB)
# 테스트 9 〉	통과 (0.03ms, 10.2MB)
# 테스트 10 〉	통과 (0.21ms, 10.1MB)
# 테스트 11 〉	통과 (0.89ms, 10.2MB)
# 테스트 12 〉	통과 (0.06ms, 10.1MB)
# 테스트 13 〉	통과 (0.78ms, 10.2MB)
# 테스트 14 〉	통과 (0.01ms, 10.2MB)
# 테스트 15 〉	통과 (0.03ms, 10.2MB)
# 테스트 16 〉	통과 (0.07ms, 10.1MB)
# 테스트 17 〉	통과 (1.46ms, 10.3MB)
# 테스트 18 〉	통과 (0.03ms, 10.2MB)
# 테스트 19 〉	통과 (1.02ms, 10.2MB)
# 테스트 20 〉	통과 (0.17ms, 10.1MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0