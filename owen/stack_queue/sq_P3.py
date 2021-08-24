def solution(bridge_length, weight, truck_weights):
    answer = 0
    bridge_state = [0] * bridge_length

    while len(bridge_state):
        answer += 1
        bridge_state.pop(0)

        if len(truck_weights):
            if (sum(bridge_state)+truck_weights[0]<=weight):
                bridge_state.append(truck_weights.pop(0))
            else:
                bridge_state.append(0)
            
    return answer



print(solution(2,10,[7,4,5,6]))



# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (11.50ms, 10.2MB)
# 테스트 2 〉	통과 (1430.63ms, 10.2MB)
# 테스트 3 〉	통과 (0.02ms, 10.2MB)
# 테스트 4 〉	통과 (317.67ms, 10.1MB)
# 테스트 5 〉	통과 (9121.30ms, 10.2MB)
# 테스트 6 〉	통과 (1588.26ms, 10.2MB)
# 테스트 7 〉	통과 (5.88ms, 10.1MB)
# 테스트 8 〉	통과 (0.45ms, 10.1MB)
# 테스트 9 〉	통과 (5.83ms, 10.1MB)
# 테스트 10 〉	통과 (0.28ms, 10.1MB)
# 테스트 11 〉	통과 (0.01ms, 10.1MB)
# 테스트 12 〉	통과 (0.32ms, 10.2MB)
# 테스트 13 〉	통과 (2.03ms, 10.2MB)
# 테스트 14 〉	통과 (0.02ms, 10.1MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0