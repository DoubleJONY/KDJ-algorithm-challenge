# https://programmers.co.kr/learn/courses/30/lessons/49190

from collections import defaultdict

def solution(arrows):
    dx = [-1, -1, 0, 1, 1, 1, 0, -1]
    dy = [0, 1, 1, 1, 0, -1, -1, -1]
    
    visited = defaultdict(lambda: False)
    visited_from = defaultdict(set)
    
    prev = (0, 0)
    visited[prev] = True
    
    cnt = 0
    for d in arrows:
        for _ in range(2):
            x, y = prev
            curr = (x + dx[d], y + dy[d])
            
            if visited[curr]:
                if prev not in visited_from[curr]:
                    cnt += 1
            else:
                visited[curr] = True
            
            visited_from[curr].add(prev)
            visited_from[prev].add(curr)
            
            prev = curr
    
    return cnt
