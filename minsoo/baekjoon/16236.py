# https://www.acmicpc.net/problem/16236

from copy import deepcopy
import heapq

DR = [-1, 0, 1, 0]
DC = [0, 1, 0, -1]

n = int(input())
m = [list(map(int, input().split())) for _ in range(n)]

size = 2
remaining = 2


def find_shark():
    global m

    for r, row in enumerate(m):
        for c, v in enumerate(row):
            if v == 9:
                m[r][c] = 0
                return (r, c)


def eat_fish(r: int, c: int):
    global m, size, remaining

    m[r][c] = 0
    remaining -= 1
    if not remaining:
        size += 1
        remaining = size


def is_valid(r: int, c: int, d: int):
    return 0 <= r + DR[d] < n and 0 <= c + DC[d] < n


def bfs(loc: tuple[int, int]):
    t = 0

    r, c = loc
    _m = deepcopy(m)
    _m[r][c] = -1

    pq = [(0, r, c, -1)]
    while pq:
        dist, r, c, d = heapq.heappop(pq)
        fish = m[r][c]

        if 0 < fish < size:
            eat_fish(r, c)
            return t + dist, (r, c)

        if m[r][c] > size:
            continue

        for _d in range(4):
            if _d ^ d == 2:
                continue
            if is_valid(r, c, _d):
                _r, _c = r + DR[_d], c + DC[_d]
                if _m[_r][_c] == -1:
                    continue
                _m[_r][_c] = -1
                heapq.heappush(pq, (dist + 1, _r, _c, _d))

    return t, loc


loc = find_shark()
t = 0

while True:
    _t, loc = bfs(loc)
    if not _t:
        break
    t += _t

print(t)
