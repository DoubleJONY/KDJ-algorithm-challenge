EMPTY = 0
WALL = 1
VIRUS = 2

DIRS = [
    (-1, 0),
    (1, 0),
    (0, -1),
    (0, 1),
]

N, M = map(int, input().split())
mat = [list(map(int, input().split())) for _ in range(N)]


def copy_mat(mat):
    return [r.copy() for r in mat]


def validate_pos(y, x):
    return (0 <= y < N) and (0 <= x < M)


def spread(mat, sy, sx):
    for dy, dx in DIRS:
        ny = sy + dy
        nx = sx + dx
        if not validate_pos(ny, nx):
            continue
        if mat[ny][nx] != EMPTY:
            continue
        mat[ny][nx] = VIRUS
        spread(mat, ny, nx)


def count_empty(mat):
    count = 0
    for y in range(N):
        for x in range(M):
            if mat[y][x] == EMPTY:
                count += 1
    return count


def simulate(mat):
    mat = copy_mat(mat)
    for y in range(N):
        for x in range(M):
            if mat[y][x] == VIRUS:
                spread(mat, y, x)

    return count_empty(mat)


max_safety_area = 0

for i0 in range(N*M):
    r0, c0 = i0 // M, i0 % M
    if mat[r0][c0] != EMPTY:
        continue
    mat[r0][c0] = WALL

    for i1 in range(N*M):
        r1, c1 = i1 // M, i1 % M
        if mat[r1][c1] != EMPTY:
            continue
        mat[r1][c1] = WALL
    
        for i2 in range(N*M):
            r2, c2 = i2 // M, i2 % M
            if mat[r2][c2] != EMPTY:
                continue
            mat[r2][c2] = WALL

            safety_area = simulate(mat)
            max_safety_area = max(max_safety_area, safety_area)

            mat[r2][c2] = EMPTY

        mat[r1][c1] = EMPTY

    mat[r0][c0] = EMPTY


print(max_safety_area)
