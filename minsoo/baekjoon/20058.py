# https://www.acmicpc.net/problem/20058

from collections import deque


def stdin_bj20058() -> tuple[list[list[int]], list[int]]:
    """
    첫째 줄에 N과 Q가 주어진다. 둘째 줄부터 2N개의 줄에는 격자의 각 칸에 있는 얼음의 양이 주어진다. r번째 줄에서 c번째 주어지는 정수는 A[r][c] 이다.
    마지막 줄에는 마법사 상어가 시전한 단계 L1, L2, ..., LQ가 순서대로 주어진다.
    """

    size, num_cast = map(int, input().split())
    grid = [list(map(int, input().split())) for _ in range(2 ** size)]
    cast_sizes = list(map(int, input().split()))

    return grid, cast_sizes


def cast(grid: list[list[int]], *, step: int) -> list[list[int]]:
    def rotate(subgrid: list[list[int]]) -> list[list[int]]:
        return list(map(lambda col: list(col[::-1]), zip(*subgrid)))

    for r in range(0, len(grid), step):
        for c in range(0, len(grid[0]), step):
            subgrid = [row[c:c + step] for row in grid[r:r + step]]
            rotated = rotate(subgrid)

            for ridx in range(step):
                grid[r + ridx][c:c + step] = rotated[ridx]

    return grid


def melt(grid: list[list[int]]) -> list[list[int]]:
    def valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[0])

    def num_surrounds(r: int, c: int) -> int:
        return sum(
            grid[r + dr][c + dc] > 0
            for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]
            if valid(r + dr, c + dc)
        )

    be_melt = []

    for r, row in enumerate(grid):
        for c, v in enumerate(row):
            if not v:
                continue
            if num_surrounds(r, c) < 3:
                be_melt.append((r, c))

    for r, c in be_melt:
        grid[r][c] -= 1

    return grid


def get_largest(grid: list[list[int]]) -> int:
    def valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[0])

    def get_components(r: int, c: int) -> set[tuple[int, int]]:
        q = deque([(r, c)])
        components = {(r, c)}

        while q:
            r, c = q.popleft()
            for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                nr, nc = r + dr, c + dc

                if not valid(nr, nc):
                    continue
                if not grid[nr][nc] or (nr, nc) in components:
                    continue

                q.append((nr, nc))
                components.add((nr, nc))

        return components

    largest = 0

    visited = set()

    for r, row in enumerate(grid):
        for c, v in enumerate(row):
            if not v or (r, c) in visited:
                continue

            components = get_components(r, c)
            visited |= components

            largest = max(largest, len(components))

    return largest


def main():
    grid, cast_sizes = stdin_bj20058()

    for cast_size in cast_sizes:
        grid = cast(grid, step=2 ** cast_size)
        grid = melt(grid)

    remaining = sum(sum(row) for row in grid)
    largest = get_largest(grid)

    print(remaining, largest, sep="\n")


if __name__ == "__main__":
    main()
