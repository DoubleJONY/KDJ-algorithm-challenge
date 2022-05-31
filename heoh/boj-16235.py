N, M, K = map(int, input().split())
A = [list(map(int, input().split())) for _ in range(N)]
T = [list(map(int, input().split())) for _ in range(M)]

cells = [[5] * N for _ in range(N)]
trees = [[z, x-1, y-1] for x, y, z in T]
trees = sorted(trees)

for t in range(K):
    alives = []
    deads = []
    babies = []

    # 봄
    for tree in trees:
        z, x, y = tree
        if z <= cells[y][x]:
            cells[y][x] -= z
            tree[0] += 1
            alives.append(tree)
        else:
            deads.append(tree)

    # 여름
    for tree in deads:
        z, x, y = tree
        cells[y][x] += z // 2

    # 가을
    for tree in alives:
        z, x, y = tree
        if z % 5 == 0:
            for dy, dx in (
                (-1, -1), (-1,  0), (-1,  1),
                ( 0, -1),           ( 0,  1),
                ( 1, -1), ( 1,  0), ( 1,  1),
            ):
                ny = y + dy
                nx = x + dx
                if 0 <= nx < N and 0 <= ny < N:
                    baby = [1, nx, ny]
                    babies.append(baby)

    # 겨울
    for y in range(N):
        for x in range(N):
            cells[y][x] += A[y][x]

    trees = alives + babies

print(len(trees))
