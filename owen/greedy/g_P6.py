def solution(routes):
    answer = 1
    
    routes.sort(key = lambda x : x[0], reverse=True)
    C_location = routes[0][0]
    for route in routes[1:]:
        if route[1] >= C_location and route[0] <= C_location:
            continue
        else:
            C_location = route[0]
            answer += 1

    
    return answer

print(solution([[-20,15], [-14,-5], [-18,-13], [-5,-3]]))


# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.01ms, 10.2MB)
# 테스트 2 〉	통과 (0.02ms, 10.2MB)
# 테스트 3 〉	통과 (0.02ms, 10.2MB)
# 테스트 4 〉	통과 (0.02ms, 10.2MB)
# 테스트 5 〉	통과 (0.02ms, 10.3MB)
# 효율성  테스트
# 테스트 1 〉	통과 (0.54ms, 10.4MB)
# 테스트 2 〉	통과 (0.30ms, 10.4MB)
# 테스트 3 〉	통과 (1.02ms, 10.5MB)
# 테스트 4 〉	통과 (0.06ms, 10.1MB)
# 테스트 5 〉	통과 (1.16ms, 10.6MB)
# 채점 결과
# 정확성: 50.0
# 효율성: 50.0
# 합계: 100.0 / 100.0