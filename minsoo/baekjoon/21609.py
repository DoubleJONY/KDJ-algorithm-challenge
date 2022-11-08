# https://www.acmicpc.net/problem/21609

from collections import deque

BLANK = -2
BLACK = -1
RAINBOW = 0


def stdin_bj21609() -> tuple[list[list[int]], int]:
    """
    첫째 줄에 격자 한 변의 크기 N, 색상의 개수 M이 주어진다.
    둘째 줄부터 N개의 줄에 격자의 칸에 들어있는 블록의 정보가 1번 행부터 N번 행까지 순서대로 주어진다.
    각 행에 대한 정보는 1열부터 N열까지 순서대로 주어진다. 입력으로 주어지는 칸의 정보는 -1, 0, M이하의 자연수로만 이루어져 있다.
    """

    size, num_colors = map(int, input().split())

    grid = [list(map(int, input().split())) for _ in range(size)]

    return grid, num_colors


class Group(list):
    def __init__(self, *args, color: int, **kwargs):
        super().__init__(*args, **kwargs)

        self.color = color
        self.num_rainbows = int(color == RAINBOW)

        self.r, self.c = (float("inf"), float("inf")) if color == RAINBOW else args[0][0]

    def append(self, *args, color: int, **kwargs):
        super().append(*args, **kwargs)

        if color == RAINBOW:
            self.num_rainbows += 1
            return

        if self.color == RAINBOW:
            self.color = color

        if color != self.color:
            raise ValueError(
                f"Rainbow or identical color are allowed. Expected {self.color} but found {color}."
            )

        r, c = args[0]

        if r > self.r:
            return
        if r == self.r and c > self.c:
            return

        self.r, self.c = r, c


def get_groups(grid: list[list[int]]) -> list[Group]:
    groups = []

    visited_other_than_rainbow = set()

    def valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[0])

    def get_group(r: int, c: int) -> Group:
        group = Group([(r, c)], color=grid[r][c])

        q = deque([(r, c)])
        visited = {(r, c)}
        if grid[r][c] != RAINBOW:
            visited_other_than_rainbow.add((r, c))

        while q:
            r, c = q.popleft()

            for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                nr, nc = r + dr, c + dc

                if not valid(nr, nc):
                    continue
                if (nr, nc) in visited:
                    continue
                if group.color != RAINBOW and grid[nr][nc] != RAINBOW and group.color != grid[nr][nc]:
                    continue

                visited.add((nr, nc))
                if grid[nr][nc] != RAINBOW:
                    visited_other_than_rainbow.add((nr, nc))

                if grid[nr][nc] == BLANK or grid[nr][nc] == BLACK:
                    continue

                q.append((nr, nc))

                group.append((nr, nc), color=grid[nr][nc])

        return group

    for r, row in enumerate(grid):
        for c, v in enumerate(row):
            if (r, c) in visited_other_than_rainbow:
                continue
            if v == BLANK or v == BLACK:
                continue

            group = get_group(r, c)

            if group.color == RAINBOW:
                continue
            if len(group) < 2:
                continue

            groups.append(group)

    return groups


def remove_group(grid: list[list[int]], group: Group) -> list[list[int]]:
    for r, c in group:
        grid[r][c] = BLANK

    return grid


def gravitate(grid: list[list[int]]) -> list[list[int]]:
    for c in range(len(grid[0])):
        black_idxs = [r for r in range(len(grid)) if grid[r][c] == BLACK] + [len(grid)]

        for i, black_idx in enumerate(black_idxs):
            subcolumn = [
                grid[r][c]
                for r in range(black_idxs[i - 1] + 1 if i else 0, black_idx)
                if not (grid[r][c] == BLACK or grid[r][c] == BLANK)
            ]

            for r in range(black_idxs[i - 1] + 1 if i else 0, black_idx):
                grid[r][c] = BLANK

            for idx in range(len(subcolumn)):
                grid[black_idx - 1 - idx][c] = subcolumn[-1 - idx]

    return grid


def rotate_ccw(grid: list[list[int]]) -> list[list[int]]:
    return [list(row) for row in list(zip(*grid))[::-1]]


def main():
    grid, num_colors = stdin_bj21609()

    score = 0

    while True:
        groups = get_groups(grid)

        if not groups:
            break

        groups = sorted(
            groups,
            key=lambda group: (len(group), group.num_rainbows, group.r, group.c),
            reverse=True
        )
        group = groups[0]

        grid = remove_group(grid, group)

        score += len(group) ** 2

        grid = gravitate(grid)
        grid = rotate_ccw(grid)
        grid = gravitate(grid)

    print(score)


if __name__ == "__main__":
    main()
