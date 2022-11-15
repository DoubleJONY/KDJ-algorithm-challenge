# https://www.acmicpc.net/problem/21610

from collections import namedtuple

DR = [0, -1, -1, -1, 0, 1, 1, 1]
DC = [-1, -1, 0, 1, 1, 1, 0, -1]

Casting = namedtuple("Casting", ["d", "s"])


class Cloud:
    def __init__(self, *components: tuple[int, int], size: int):
        self.components = components
        self.size = size

    def __len__(self) -> int:
        return len(self.components)

    def __getitem__(self, index: int) -> tuple[int, int]:
        return self.components[index]

    def __iadd__(self, casting: Casting):
        d, s = casting

        self.components = [
            ((r + s * DR[d]) % self.size, (c + s * DC[d]) % self.size)
            for r, c in self.components
        ]

        return self


def main():
    size, grid, castings = stdin_bj21610()

    cloud = Cloud((size - 1, 0), (size - 1, 1), (size - 2, 0), (size - 2, 1), size=size)

    for casting in castings:
        grid, cloud = cast(grid, cloud, casting)

    print(sum(sum(row) for row in grid))


def stdin_bj21610() -> tuple[int, list[list[int]], list[Casting]]:
    """
    첫째 줄에 N, M이 주어진다.
    둘째 줄부터 N개의 줄에는 N개의 정수가 주어진다. r번째 행의 c번째 정수는 A[r][c]를 의미한다.
    다음 M개의 줄에는 이동의 정보 d, s가 순서대로 한 줄에 하나씩 주어진다.
    """

    size, num_castings = map(int, input().split())
    grid = [list(map(int, input().split())) for _ in range(size)]

    def from_zero(d: str, s: str) -> tuple[int, int]:
        return int(d) - 1, int(s)

    castings = [Casting(*from_zero(*input().split())) for _ in range(num_castings)]

    return size, grid, castings


def cast(grid: list[list[int]], cloud: Cloud, casting: Casting) -> tuple[list[list[int]], Cloud]:
    """
    1. 모든 구름이 d 방향으로 s칸 이동한다.
    2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양이 1 증가한다.
    3. 구름이 모두 사라진다.
    4. 2에서 물이 증가한 칸 (r, c)에 물복사버그 마법을 시전한다.물복사버그 마법을 사용하면,
       대각선 방향으로 거리가 1인 칸에 물이 있는 바구니의 수만큼 (r, c)에 있는 바구니의 물이 양이 증가한다.
       - 이때는 이동과 다르게 경계를 넘어가는 칸은 대각선 방향으로 거리가 1인 칸이 아니다.
       - 예를 들어, (N, 2)에서 인접한 대각선 칸은 (N-1, 1), (N-1, 3)이고, (N, N)에서 인접한 대각선 칸은 (N-1, N-1)뿐이다.
    5. 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다.
       - 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
    """

    cloud += casting

    for r, c in cloud:
        grid[r][c] += 1

    def is_valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[r])

    def magic(r: int, c: int) -> int:
        amount = 0

        for nr, nc in [(r + DR[d], c + DC[d]) for d in [1, 3, 5, 7]]:
            if not is_valid(nr, nc):
                continue
            if not grid[nr][nc]:
                continue

            amount += 1

        return amount

    for r, c in cloud:
        grid[r][c] += magic(r, c)

    cloud_components = []

    for r, row in enumerate(grid):
        for c, water in enumerate(row):
            if water < 2:
                continue
            if (r, c) in cloud:
                continue

            cloud_components.append((r, c))
            grid[r][c] -= 2

    cloud = Cloud(*cloud_components, size=len(grid))

    return grid, cloud


if __name__ == "__main__":
    main()
