# https://programmers.co.kr/learn/courses/30/lessons/49191

from collections import defaultdict

def solution(n, results):
    won = defaultdict(set)
    lost_to = defaultdict(set)
    for a, b in results:
        won[a].add(b)
        lost_to[b].add(a)
    
    for a in range(1, n + 1):
        for b in lost_to[a]:
            won[b].update(won[a])
        for b in won[a]:
            lost_to[b].update(lost_to[a])
    
    cnt = 0
    for a in range(1, n + 1):
        if len(won[a]) + len(lost_to[a]) == n - 1:
            cnt += 1
    return cnt
