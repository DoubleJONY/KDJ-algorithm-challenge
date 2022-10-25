from collections import deque


def main():
    N, Q = map(int, input().split())
    A = [list(map(int, input().split())) for _ in range(2**N)]
    L = list(map(int, input().split()))

    for i in range(Q):
        spell_firestorm(A, L[i])
        decrease_ice(A)

    print(sum_all(A))
    print(search_largest_group(A))


def spell_firestorm(A, L):
    if L == 0:
        return

    N = len(A)
    width = 2 ** L
    for r in range(0, N, width):
        for c in range(0, N, width):
            rotate_area(A, r, c, width)


def rotate_area(A, sr, sc, width):
    w = width // 2
    min_r = sr
    mid_r = sr + w
    max_r = sr + width
    min_c = sc
    mid_c = sc + w
    max_c = sc + width
    
    q1 = [min_r, min_c, mid_r, mid_c]
    q2 = [min_r, mid_c, mid_r, max_c]
    q3 = [mid_r, mid_c, max_r, max_c]
    q4 = [mid_r, min_c, max_r, mid_c]
    
    a1 = get_area(A, *q1)
    a2 = get_area(A, *q2)
    a3 = get_area(A, *q3)
    a4 = get_area(A, *q4)

    set_area(A, *q1, a4)
    set_area(A, *q2, a1)
    set_area(A, *q3, a2)
    set_area(A, *q4, a3)


def get_area(A, sr, sc, er, ec):
    values = []
    for r in range(sr, er):
        for c in range(sc, ec):
            values.append(A[r][c])
    return values


def set_area(A, sr, sc, er, ec, values):
    i = 0
    for r in range(sr, er):
        for c in range(sc, ec):
            A[r][c] = values[i]
            i += 1


def decrease_ice(A):
    decreasing_cells = []

    N = len(A)
    for r in range(N):
        for c in range(N):
            if A[r][c] > 0 and count_neighbors(A, r, c) < 3:
                decreasing_cells.append((r, c))

    for (r, c) in decreasing_cells:
        A[r][c] -= 1


def count_neighbors(A, r, c):
    N = len(A)
    neighbors = [(r-1, c), (r+1, c), (r, c-1), (r, c+1)]
    count = 0
    for nr, nc in neighbors:
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
    DIR = [(-1, 0), (1, 0), (0, -1), (0, 1)]

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
                            visited.add((nr, nc))
                            area += 1
                best = max(best, area)
    return best


if __name__ == '__main__':
    main()
