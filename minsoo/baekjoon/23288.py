# https://www.acmicpc.net/problem/23288

from collections import deque

DR = [0, 1, 0, -1]
DC = [1, 0, -1, 0]


class Dice:
    def __init__(self):
        self.surface_groups = [[4, 1, 3], [2, 1, 5]]
        self.bottom = 6

        self.r = 0
        self.c = 0
        self.d = 0

    def roll(self) -> "Dice":
        sgid = self.d % 2
        surface_group = self.surface_groups[sgid] + [self.bottom]

        idx = 1 if self.d // 2 else -1
        surface_group = surface_group[idx:] + surface_group[:idx]

        *self.surface_groups[sgid], self.bottom = surface_group
        self.surface_groups[sgid ^ 1][1] = surface_group[1]

        self.r += DR[self.d]
        self.c += DC[self.d]

        return self

    @property
    def forward(self) -> tuple[int, int]:
        return self.r + DR[self.d], self.c + DC[self.d]


def main():
    grid, dice, num_moves = stdin_bj23288()

    score = 0

    for _ in range(num_moves):
        grid, dice = roll(grid, dice)
        score += get_score(grid, dice)

    print(score)


def stdin_bj23288() -> tuple[list[list[int]], Dice, int]:
    """
    첫째 줄에 지도의 세로 크기 N, 가로 크기 M (2 ≤ N, M ≤ 20), 그리고 이동하는 횟수 K (1 ≤ K ≤ 1,000)가 주어진다.
    둘째 줄부터 N개의 줄에 지도에 쓰여 있는 수가 북쪽부터 남쪽으로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다.
    지도의 각 칸에 쓰여 있는 수는 10 미만의 자연수이다.
    """

    num_rows, num_cols, num_moves = map(int, input().split())
    grid = [list(map(int, input().split())) for _ in range(num_rows)]

    dice = Dice()

    return grid, dice, num_moves


def roll(grid: list[list[int]], dice: Dice) -> tuple[list[list[int]], Dice]:
    """
    1. 주사위가 이동 방향으로 한 칸 굴러간다. 만약, 이동 방향에 칸이 없다면, 이동 방향을 반대로 한 다음 한 칸 굴러간다.
    2. 주사위의 아랫면에 있는 정수 A와 주사위가 있는 칸 (x, y)에 있는 정수 B를 비교해 이동 방향을 결정한다.
        - A > B인 경우 이동 방향을 90도 시계 방향으로 회전시킨다.
        - A < B인 경우 이동 방향을 90도 반시계 방향으로 회전시킨다.
        - A = B인 경우 이동 방향에 변화는 없다.
    """

    def is_valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[r])

    nr, nc = dice.forward

    if not is_valid(nr, nc):
        dice.d ^= 2

    dice = dice.roll()

    def get_next_direction(r: int, c: int) -> int:
        if dice.bottom > grid[r][c]:
            return (dice.d + 1) % 4
        if dice.bottom < grid[r][c]:
            return (dice.d - 1) % 4
        return dice.d

    dice.d = get_next_direction(dice.r, dice.c)

    return grid, dice


def get_score(grid: list[list[int]], dice: Dice) -> int:
    """
    칸 (x, y)에 대한 점수는 다음과 같이 구할 수 있다.
    (x, y)에 있는 정수를 B라고 했을때, (x, y)에서 동서남북 방향으로 연속해서 이동할 수 있는 칸의 수 C를 모두 구한다.
    이때 이동할 수 있는 칸에는 모두 정수 B가 있어야 한다. 여기서 점수는 B와 C를 곱한 값이다.
    """

    r, c = dice.r, dice.c

    number = grid[r][c]

    def is_valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[r])

    def bfs(r: int, c: int) -> int:
        q = deque([(r, c)])
        visited = {(r, c)}

        while q:
            cr, cc = q.popleft()

            for nr, nc in [(cr + dr, cc + dc) for dr, dc in zip(DR, DC)]:
                if not is_valid(nr, nc):
                    continue
                if grid[nr][nc] != number:
                    continue
                if (nr, nc) in visited:
                    continue

                q.append((nr, nc))
                visited.add((nr, nc))

        return len(visited)

    num_same_numbers = bfs(r, c)

    score = number * num_same_numbers

    return score


if __name__ == "__main__":
    main()
