from collections import deque

DIR = [(-1, 0), (1, 0), (0, -1), (0, 1)]


def main():
    N, Q = map(int, input().split())
    A = [list(map(int, input().split())) for _ in range(2**N)]
    L = list(map(int, input().split()))

    for i in range(Q):
        spell_firestorm(A, L[i])
        # print(f'firestorm {i}:')
        # print_mat(A)
        # print()
        decrease_ice(A)
        # print(f'ice {i}:')
        # print_mat(A)
        # print()

    print(sum_all(A))
    print(search_largest_group(A))


def print_mat(A):
    N = len(A)
    for r in range(N):
        for c in range(N):
            print(A[r][c], end=' ')
        print()

def spell_firestorm(A, L):
    if L == 0:
        return

    N = len(A)
    width = 2 ** L
    for r in range(0, N, width):
        for c in range(0, N, width):
            rotate_area(A, r, c, width)


def rotate_area(A, sr, sc, width):
    B = [[0] * width for _ in range(width)]
    for dr in range(width):
        for dc in range(width):
            B[dr][dc] = A[sr+dr][sc+dc]

    for dr in range(width):
        for dc in range(width):
            A[sr+dc][sc+width-1-dr] = B[dr][dc]


def decrease_ice(A):
    decreasing_cells = []

    N = len(A)
    for r in range(N):
        for c in range(N):
            if A[r][c] > 0 and not (count_neighbors(A, r, c) >= 3):
                decreasing_cells.append((r, c))

    for (r, c) in decreasing_cells:
        A[r][c] -= 1


def count_neighbors(A, r, c):
    N = len(A)
    count = 0
    for dr, dc in DIR:
        nr = r + dr
        nc = c + dc
        if 0 <= nr < N and 0 <= nc < N:
            if A[nr][nc] > 0:
                count += 1
    return count


def sum_all(A):
    N = len(A)
    total = 0
    for r in range(N):
        for c in range(N):
            total += A[r][c]
    return total


def search_largest_group(A):
    best = 0

    N = len(A)
    visited = set()
    for r in range(N):
        for c in range(N):
            if A[r][c] > 0 and (r, c) not in visited:
                area = 1
                q = deque()
                q.append((r, c))
                visited.add((r, c))
                while q:
                    cr, cc = q.popleft()
                    for (dr, dc) in DIR:
                        nr = cr + dr
                        nc = cc + dc
                        if 0 <= nr < N and 0 <= nc < N and A[nr][nc] > 0 and (nr, nc) not in visited:
                            q.append((nr, nc))
                            area += 1
                        visited.add((nr, nc))
                best = max(best, area)
    return best


if __name__ == '__main__':
    main()
