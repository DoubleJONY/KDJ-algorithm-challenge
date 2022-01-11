DY = [0, -1, 0, 1]
DX = [-1, 0, 1, 0]

N, M = map(int, input().split())
A = list()
for _ in range(N):
    A.append(list(map(int, input().split())))

max_in_map = max(max(row) for row in A)
answer = 0


def get_max_case(y, x, depth, sum, back_chances=1):
    cell = A[y][x]
    if depth == 3:
        return sum+cell

    ideal_sum = sum + max_in_map * (4 - depth)
    if ideal_sum < answer:
        return 0

    best = 0
    A[y][x] = 0
    dirs = range(3)
    if depth == 0:
        dirs = range(2)
    for d in dirs:
        ny = y + DY[d]
        nx = x + DX[d]
        
        if not (0 <= ny < N and 0 <= nx < M):
            continue

        next_cell = A[ny][nx]
        if next_cell == 0:
            if back_chances > 0:
                best = max(best, get_max_case(ny, nx, depth, sum+cell, back_chances-1))
            else:
                continue
        else:
            best = max(best, get_max_case(ny, nx, depth+1, sum+cell, back_chances))

    A[y][x] = cell
    return best


for y in range(N):
    for x in range(M):
        answer = max(answer, get_max_case(y, x, 0, 0, True))

print(answer)
