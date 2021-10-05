
def solution(n, costs):
    sorted_costs = sorted(costs, key= lambda x : x[2])
    union_set = set([sorted_costs[0][0]])

    answer = 0
    while len(union_set) < n:
        for connect in sorted_costs:
            if connect[0] in union_set and connect[1] in union_set:
                continue
            if connect[0] in union_set or connect[1] in union_set:
                union_set.update([connect[0], connect[1]])
                answer += connect[-1]
                break


    
    return answer


print(solution(4, [[0,1,1],[0,2,2],[1,2,5],[1,3,1],[2,3,8]]))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.3MB)
# 테스트 2 〉	통과 (0.01ms, 10.2MB)
# 테스트 3 〉	통과 (0.02ms, 10.3MB)
# 테스트 4 〉	통과 (0.03ms, 10.3MB)
# 테스트 5 〉	통과 (0.02ms, 10.2MB)
# 테스트 6 〉	통과 (0.03ms, 10.2MB)
# 테스트 7 〉	통과 (0.03ms, 10.2MB)
# 테스트 8 〉	통과 (0.01ms, 10.2MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0