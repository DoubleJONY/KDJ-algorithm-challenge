# https://www.acmicpc.net/problem/14502

from collections import deque
from itertools import combinations

CORRIDOR = 0
WALL = 1
VIRUS = 2
MOVEMENTS = [(-1, 0), (1, 0), (0, -1), (0, 1)]

m, n = map(int, input().split())
lab = [list(map(int, input().split())) for _ in range(m)]

corridor_map = set()
virus_map = []

for r, row in enumerate(lab):
    for c, type in enumerate(row):
        if type == CORRIDOR:
            corridor_map.add((r, c))
        elif type == VIRUS:
            virus_map.append((r, c))


def spread(walls):
    q = deque(virus_map)
    while q:
        i, j = q.popleft()
        for di, dj in MOVEMENTS:
            r, c = i + di, j + dj
            if 0 <= r < m and 0 <= c < n:
                if lab[r][c] == CORRIDOR and (r, c) not in walls:
                    lab[r][c] = VIRUS
                    q.append((r, c))

    cnt = 0
    for r, row in enumerate(lab):
        for c, type in enumerate(row):
            if type == VIRUS and (r, c) not in virus_map:
                lab[r][c] = CORRIDOR
            elif type == CORRIDOR and (r, c) not in walls:
                cnt += 1
    return cnt


_max = 0
for walls in combinations(corridor_map, 3):
    _max = max(_max, spread(walls))

print(_max)
