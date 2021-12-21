DY = [0, -1, 0, 1]
DX = [-1, 0, 1, 0]

N, M = map(int, input().split())
A = list()
for _ in range(N):
    A.append(list(map(int, input().split())))


def get_max_case(y, x, depth, sum):
    cell = A[y][x]
    if depth == 3:
        return sum+cell

    best = 0
    if depth == 1:
        A[y][x] = 0
    else:
        A[y][x] = -1
    for d in range(4):
        ny = y + DY[d]
        nx = x + DX[d]
        
        if not (0 <= ny < N and 0 <= nx < M):
            continue

        next_cell = A[ny][nx]
        if next_cell == -1:
            continue
        elif next_cell == 0:
            best = max(best, get_max_case(ny, nx, depth, sum+cell))
        else:
            best = max(best, get_max_case(ny, nx, depth+1, sum+cell))

    A[y][x] = cell
    return best


answer = 0
for y in range(N):
    for x in range(M):
        answer = max(answer, get_max_case(y, x, 0, 0))

print(answer)
