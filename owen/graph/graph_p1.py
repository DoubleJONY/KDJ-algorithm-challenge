
from collections import deque


def solution(n, edge):
    
    answer = 0
    edge.sort(key = lambda x:x[0])
    print(edge)
    q = deque()
    length_list = [0] * (n+1)
    graph = [[] for i in range(n+1)]

    print(graph)
    for e in edge: # 양방향이므로 
        graph[e[0]].append(e[1])
        graph[e[1]].append(e[0])
    print(graph)
    
    q.append(1)
    length_list[1] = 1


    while q:
        now = q.popleft()
        for idx in graph[now]:
            if length_list[idx] == 0:
                q.append(idx)
                length_list[idx] = length_list[now] + 1


    max_length = max(length_list)
    for c in length_list:
        if c == max_length:
            answer += 1


    return answer






print(solution(6, [[3, 6], [4, 3], [3, 2], [1, 3], [1, 2], [2, 4], [5, 2]]))

# 채점을 시작합니다.
# 정확성  테스트
# 테스트 1 〉	통과 (0.02ms, 10.3MB)
# 테스트 2 〉	통과 (0.02ms, 10.3MB)
# 테스트 3 〉	통과 (0.03ms, 10.3MB)
# 테스트 4 〉	통과 (0.25ms, 10.3MB)
# 테스트 5 〉	통과 (0.97ms, 10.5MB)
# 테스트 6 〉	통과 (2.85ms, 11MB)
# 테스트 7 〉	통과 (26.62ms, 16.8MB)
# 테스트 8 〉	통과 (38.21ms, 20.3MB)
# 테스트 9 〉	통과 (37.47ms, 19.9MB)
# 채점 결과
# 정확성: 100.0
# 합계: 100.0 / 100.0  11