from collections import defaultdict

DY = [-1, -1, 0, 1, 1, 1, 0, -1]
DX = [0, 1, 1, 1, 0, -1, -1, -1]

N, M, K = map(int, input().split())
A = [list(map(int, input().split())) for _ in range(M)]

R = 0
C = 1
M = 2
S = 3
D = 4

def get_remaining():
    return sum([a[M] for a in A])

def all_is_odd(values):
    return all([v % 2 == 1 for v in values])

def all_is_even(values):
    return all([v % 2 == 0 for v in values])

def move():
    global A

    for i in range(len(A)-1, -1, -1):
        a = A[i]
        d = a[D]
        s = a[S]
        a[R] = ((a[R] + DY[d] * s) + N) % N
        a[C] = ((a[C] + DX[d] * s) + N) % N

    cells = defaultdict(list)
    for a in A:
        r, c, m, s, d = a
        cells[r, c].append(a)

    next_A = []
    for (r, c) in cells:
        cell = cells[r, c]
        if len(cell) >= 2:
            next_m = sum([a[M] for a in cell]) // 5
            next_s = sum([a[S] for a in cell]) // len(cell)
            if next_m > 0:
                dirs = [a[D] for a in cell]
                if all_is_odd(dirs) or all_is_even(dirs):
                    next_A += [[r, c, next_m, next_s, next_d] for next_d in range(0, 8, 2)]
                else:
                    next_A += [[r, c, next_m, next_s, next_d] for next_d in range(1, 8, 2)]
        else:
            next_A += cell

    A = next_A

for _ in range(K):
    move()

print(get_remaining())
