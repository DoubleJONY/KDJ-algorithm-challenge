# https://programmers.co.kr/learn/courses/30/lessons/49191

def solution(n, results):
    graph = [[False for _ in range(n + 1)] for _ in range(n + 1)]
    for a, b in results:
        graph[a][b] = True

    for j in range(1, n + 1):
        for i in range(1, n + 1):
            for k in range(1, n + 1):
                if graph[i][j] and graph[j][k]:
                    graph[i][k] = True

    cnt = 0
    for i in range(1, n + 1):
        _cnt = 0
        for j in range(1, n + 1):
            if graph[i][j] is not graph[j][i]:
                _cnt += 1
        if _cnt == n - 1:
            cnt += 1

    return cnt
