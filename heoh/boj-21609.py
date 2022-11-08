EMPTY_BLOCK = -2
BLACK_BLOCK = -1
RAINBOW_BLOCK = 0


N, M = map(int, input().split())
A = [list(map(int, input().split())) for _ in range(N)]

B = [[0] * N for _ in range(N)]


def main():
    answer = solve()
    print(answer)


def solve():
    score = 0
    while target_group := search_target_group():
        remove_blocks(target_group)
        score += len(target_group) ** 2

        do_gravity()
        rotate()
        do_gravity()

    return score

def search_target_group():
    groups = search_all_groups()
    if not groups:
        return None
    groups = sorted(groups, reverse=True)
    size, n_rainbows, blocks = groups[0]
    return blocks

def search_all_groups():
    visited = set()

    def dfs(blocks, r, c, color, visited_rainbow):
        n_rainbows = 0
        if A[r][c] == RAINBOW_BLOCK:
            n_rainbows += 1
        blocks.append((r, c))

        for (nr, nc) in [(r-1, c), (r, c-1), (r+1, c), (r, c+1)]:
            if (nr, nc) in visited or (nr, nc) in visited_rainbow:
                continue
            if not (0 <= nr < N and 0 <= nc < N):
                continue
            if A[nr][nc] != color and A[nr][nc] != RAINBOW_BLOCK:
                continue
            if A[nr][nc] > 0:
                visited.add((nr, nc))
            else:
                visited_rainbow.add((nr, nc))
            n_rainbows += dfs(blocks, nr, nc, color, visited_rainbow)
        return n_rainbows

    groups = []
    for r in range(N):
        for c in range(N):
            if (r, c) in visited:
                continue
            if A[r][c] <= 0:
                continue
            visited.add((r, c))
            blocks = []
            n_rainbows = dfs(blocks, r, c, A[r][c], set())
            if len(blocks) >= 2:
                groups.append((len(blocks), n_rainbows, blocks))
    return groups

def remove_blocks(blocks):
    for (r, c) in blocks:
        A[r][c] = EMPTY_BLOCK

def do_gravity():
    for c in range(N):
        bottom = N-1
        for r in reversed(range(N)):
            color = A[r][c]
            if color >= 0:
                A[r][c] = EMPTY_BLOCK
                A[bottom][c] = color
                bottom -= 1
            elif color == BLACK_BLOCK:
                bottom = r - 1

def rotate():
    global A, B

    for r in range(N):
        for c in range(N):
            B[N-1 - c][r] = A[r][c]

    A, B = B, A


main()
