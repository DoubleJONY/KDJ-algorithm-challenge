from collections import defaultdict
from collections import deque

N, L, R = map(int, input().split())
A = [list(map(int, input().split())) for _ in range(N)]
A = defaultdict(lambda: 1000, {(r, c): A[r][c] for c in range(N) for r in range(N)})

def propagate(p):
    g = set()
    q = deque([p])
    while q:
        p = q.popleft()
        r, c = p

        if p in g:
            continue

        g.add(p)
        for d in range(4):
            nr = r + [0, 1, 0, -1][d]
            nc = c + [1, 0, -1, 0][d]
            np = (nr, nc)
            diff = abs(A[np] - A[p])
            if (L <= diff <= R) and (np not in g):
                q.append(np)
    return g

def find_groups():
    groups = list()
    visited = set()
    for r in range(N):
        for c in range(N):
            if (r, c) not in visited:
                g = propagate((r, c))
                visited |= g
                groups.append(g)
    return groups

def update():
    groups = find_groups()
    groups = list(filter(lambda g: len(g) > 1, groups))
    for g in groups:
        mean = sum(A[i] for i in g) // len(g)
        for p in g:
            A[p] = mean
    return len(groups) > 0

cnt = 0
while update():
    cnt += 1

print(cnt)
