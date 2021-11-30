# https://programmers.co.kr/learn/courses/30/lessons/49190
# https://bellog.tistory.com/147

from collections import defaultdict, deque


def solution(arrows):
    dp = [(-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1)]

    q = deque([(0, 0)])
    for d in arrows:
        for _ in range(2):
            x, y = q[-1]
            dx, dy = dp[d]
            q.append((x + dx, y + dy))

    visited = defaultdict(lambda: False)
    visited_from = defaultdict(set)
    cnt = 0

    prev = q.popleft()
    visited[prev] = True
    while q:
        curr = q.popleft()
        if visited[curr]:
            if prev not in visited_from[curr]:
                cnt += 1
        else:
            visited[curr] = True

        visited_from[curr].add(prev)
        visited_from[prev].add(curr)

        prev = curr

    return cnt
