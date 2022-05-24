# https://www.acmicpc.net/problem/16234

from collections import deque

DR = [-1, 0, 1, 0]
DC = [0, 1, 0, -1]


n, lbound, ubound = map(int, input().split())
populations = [list(map(int, input().split())) for _ in range(n)]


def valid(r: int, c: int, pop: int):
    return (
        0 <= r < n
        and 0 <= c < n
        and lbound <= abs(populations[r][c] - pop) <= ubound
    )


cnt = 0
while True:
    unions = []
    visited = set()
    for r in range(n):
        for c in range(n):
            loc = (r, c)
            if loc in visited:
                continue

            q = deque([loc])
            union = {loc}

            while q:
                cr, cc = q.popleft()
                pop = populations[cr][cc]
                for d in range(4):
                    _r, _c = cr + DR[d], cc + DC[d]
                    if not valid(_r, _c, pop):
                        continue

                    _loc = (_r, _c)
                    if _loc in union:
                        continue

                    q.append(_loc)
                    union.add(_loc)

            if len(union) == 1:
                continue

            unions.append(union)
            visited |= union

    if not unions:
        break

    for union in unions:
        pop_sum = 0
        for r, c in union:
            pop_sum += populations[r][c]

        pop_avg = pop_sum // len(union)

        for r, c in union:
            populations[r][c] = pop_avg

    cnt += 1

print(cnt)
