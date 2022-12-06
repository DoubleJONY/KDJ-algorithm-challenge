# https://www.acmicpc.net/problem/23289

from collections import defaultdict, namedtuple

Location = namedtuple("Location", ["r", "c"])
FanHeater = namedtuple("FanHeater", ["r", "c", "d"])

BLANK = 0
TO_INVESTIGATE = 5

HORIZONTAL_UPPER_WALL = 0
VERTICAL_RIGHT_WALL = 1

DR = [-1, 0, 1, 0]
DC = [0, 1, 0, -1]


def main() -> None:
    grid, blocked, fan_heaters, locations_to_investigate, threshold = stdin_bj23289()

    count = 0

    while count <= 100:
        for fan_heater in fan_heaters:
            grid = blow(grid, fan_heater, blocked)
        grid = spread(grid, blocked)
        grid = reduce(grid)

        count += 1

        if investigate(grid, locations_to_investigate, threshold):
            break

    print(count)


def stdin_bj23289() -> tuple[list[list[int]], dict[tuple, set], list[FanHeater], list[Location], int]:
    num_rows, num_cols, threshold = map(int, input().split())

    fan_heaters = []
    locations_to_investigate = []

    for r in range(num_rows):
        for c, v in enumerate(list(map(int, input().split()))):
            if v == BLANK:
                continue
            if v == TO_INVESTIGATE:
                locations_to_investigate.append(Location(r, c))
            else:
                fan_heaters.append(FanHeater(r, c, (1, 3, 0, 2)[v - 1]))

    grid = [[0 for _ in range(num_cols)] for _ in range(num_rows)]

    blocked = defaultdict(set)
    for _ in range(int(input())):
        rp1, cp1, d = map(int, input().split())
        r, c = rp1 - 1, cp1 - 1

        loc = (r - 1, c) if d == HORIZONTAL_UPPER_WALL else (r, c + 1)

        blocked[r, c].add(loc)
        blocked[loc].add((r, c))

    return grid, blocked, fan_heaters, locations_to_investigate, threshold


def blow(grid: list[list[int]], fan_heater: FanHeater, blocked: dict[tuple, set]) -> list[list[int]]:
    def is_valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[r])

    heater_d = fan_heater.d
    init_r, init_c = fan_heater.r + DR[heater_d], fan_heater.c + DC[heater_d]

    if not is_valid(init_r, init_c):
        return grid

    blown = {(init_r, init_c): 5}

    def to_blow_from(r: int, c: int):
        to_blow = []

        if heater_d % 2:
            nc = c + DC[heater_d]

            if (r - 1, c) not in blocked or not (
                (r, c) in blocked[r - 1, c] or (r - 1, nc) in blocked[r - 1, c]
            ):
                to_blow.append((r - 1, nc))

            if (r, c) not in blocked or (r, nc) not in blocked[r, c]:
                to_blow.append((r, nc))

            if (r + 1, c) not in blocked or not (
                (r, c) in blocked[r + 1, c] or (r + 1, nc) in blocked[r + 1, c]
            ):
                to_blow.append((r + 1, nc))
        else:
            nr = r + DR[heater_d]

            if (r, c - 1) not in blocked or not (
                (r, c) in blocked[r, c - 1] or (nr, c - 1) in blocked[r, c - 1]
            ):
                to_blow.append((nr, c - 1))

            if (r, c) not in blocked or (nr, c) not in blocked[r, c]:
                to_blow.append((nr, c))

            if (r, c + 1) not in blocked or not (
                (r, c) in blocked[r, c + 1] or (nr, c + 1) in blocked[r, c + 1]
            ):
                to_blow.append((nr, c + 1))

        yield from to_blow

    def blow_recursively(r: int, c: int, *, heat: int) -> None:
        if not heat:
            return

        for nr, nc in to_blow_from(r, c):
            if not is_valid(nr, nc):
                continue
            if (nr, nc) in blown:
                continue

            blown[nr, nc] = heat
            blow_recursively(nr, nc, heat=heat - 1)

    blow_recursively(init_r, init_c, heat=4)

    for (r, c), heat in blown.items():
        grid[r][c] += heat

    return grid


def spread(grid: list[list[int]], blocked: dict[tuple, set]) -> list[list[int]]:
    def is_valid(r: int, c: int) -> bool:
        return 0 <= r < len(grid) and 0 <= c < len(grid[r])

    adjustments = [[0 for _ in row] for row in grid]

    for r, row in enumerate(grid):
        for c, v in enumerate(row):
            for nr, nc in ((r + DR[d], c + DC[d]) for d in range(4)):
                if not is_valid(nr, nc):
                    continue
                if (diff := v - grid[nr][nc]) < 4:
                    continue
                if (nr, nc) in blocked and (r, c) in blocked[nr, nc]:
                    continue

                to_adjust = diff // 4

                adjustments[r][c] -= to_adjust
                adjustments[nr][nc] += to_adjust

    for r, row in enumerate(adjustments):
        for c, v in enumerate(row):
            grid[r][c] += v

    return grid


def reduce(grid: list[list[int]]) -> list[list[int]]:
    for r in [0, -1]:
        for c, v in enumerate(grid[r]):
            if v < 1:
                continue
            grid[r][c] -= 1

    for c in [0, -1]:
        for r in range(1, len(grid) - 1):
            if grid[r][c] < 1:
                continue
            grid[r][c] -= 1

    return grid


def investigate(grid: list[list[int]], locations_to_investigate: list[Location], threshold: int) -> bool:
    return all(grid[r][c] >= threshold for r, c in locations_to_investigate)


if __name__ == "__main__":
    main()
