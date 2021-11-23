# https://programmers.co.kr/learn/courses/30/lessons/49189

from collections import defaultdict, deque
from bisect import bisect_left

def solution(n, edge):
    graph = defaultdict(list)
    for u, v in edge:
        graph[u].append(v)
        graph[v].append(u)
    
    dists = [float("inf") for _ in range(n + 1)]
    dists[1] = 0
    
    q = deque([(1, 0)])
    while q:
        curr, dist = q.popleft()
        for adj in graph[curr]:
            if dist + 1 < dists[adj]:
                dists[adj] = dist + 1
                q.append((adj, dist + 1))
    
    dists = sorted(dists[1:])
    return len(dists) - bisect_left(dists, dists[-1])
