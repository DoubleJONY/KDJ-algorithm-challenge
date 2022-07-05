from collections import deque

FAILED = 1e9

def main():
    N, M = map(int, input().split())
    A = [list(map(int, input().split())) for _ in range(N)]
    viruses = find_viruses(N, A)

    answer = dfs(N, M, A, viruses, [], 0)
    if answer == FAILED:
        answer = -1
    print(answer)

def find_viruses(N, A):
    viruses = []
    for r in range(N):
        for c in range(N):
            if A[r][c] == 2:
                pos = (r, c)
                viruses.append(pos)
    return viruses

def dfs(N, M, A, viruses, inits, index):
    if len(inits) == M:
        return simulate(N, A, inits)

    result = FAILED
    for i in range(index, len(viruses)):
        p = viruses[i]
        case_result = dfs(N, M, A, viruses, inits + [p], i + 1)
        result = min(result, case_result)

    return result

def simulate(N, A, inits):
    n_empty = count_empty(A)
    if n_empty == 0:
        return 0

    A = clone_matrix(A)
    visited = set(inits)
    queue = deque([(p, 0) for p in inits])
    while queue:
        pos, t = queue.popleft()
        r, c = pos

        if A[r][c] == 0:
            n_empty -= 1
            if n_empty == 0:
                return t

        for nr, nc in [(r-1, c), (r, c-1), (r+1, c), (r, c+1)]:
            next_pos = (nr, nc)
            next_t = t + 1
            if not (0 <= nr < N and 0 <= nc < N):
                continue
            if A[nr][nc] == 1:
                continue
            if next_pos in visited:
                continue
            visited.add(next_pos)
            queue.append((next_pos, next_t))

    return FAILED

def count_empty(A):
    return sum(sum(v == 0 for v in row) for row in A)

def clone_matrix(A):
    return [list(row) for row in A]

main()
