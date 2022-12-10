# https://www.acmicpc.net/problem/23290

from collections import defaultdict, namedtuple
from copy import deepcopy

NUM_ROWS = 4
NUM_COLUMNS = 4

FISH_DR = [0, -1, -1, -1, 0, 1, 1, 1]
FISH_DC = [-1, -1, 0, 1, 1, 1, 0, -1]
NUM_FISH_DIRS = 8

SHARK_DR = [-1, 0, 1, 0]
SHARK_DC = [0, -1, 0, 1]
NUM_SHARK_DIRS = 4

Location = namedtuple("Location", ["r", "c"])


def is_valid(r: int, c: int) -> bool:
    return 0 <= r < NUM_ROWS and 0 <= c < NUM_COLUMNS


def count_fish(fish_locs: dict[Location, list[int]]) -> int:
    return sum(len(directions) for directions in fish_locs.values())


def main() -> None:
    fish_locs, shark_loc, num_castings = stdin_bj23290()
    scent = {}

    for _ in range(num_castings):
        copied_fish_locs = deepcopy(fish_locs)
        fish_locs = move_fish(fish_locs, scent, shark_loc)

        for loc in list(scent.keys()):
            scent[loc] -= 1
            if not scent[loc]:
                scent.pop(loc)

        shark_loc, fish_locs, scent = move_shark(shark_loc, fish_locs, scent)

        for loc, directions in copied_fish_locs.items():
            fish_locs[loc].extend(directions)

    print(count_fish(fish_locs))


def stdin_bj23290() -> tuple[dict[Location, list[int]], Location, int]:
    num_fish, num_castings = map(int, input().split())

    def n_to_idx(n: str) -> int:
        return int(n) - 1

    fish_locs = defaultdict(list)
    for _ in range(num_fish):
        *loc, d = map(n_to_idx, input().split())
        fish_locs[Location(*loc)].append(d)

    shark_loc = Location(*map(n_to_idx, input().split()))

    return fish_locs, shark_loc, num_castings


def move_fish(
    fish_locs: dict[Location, list[int]], scent: dict[Location, int], shark_loc: Location
) -> dict[Location, list[int]]:
    next_fish_locs = defaultdict(list)

    def forward(r: int, c: int, *, d: int) -> Location:
        return Location(r + FISH_DR[d], c + FISH_DC[d])

    for loc, directions in fish_locs.items():
        next_loc = loc
        for d in directions:
            for _ in range(NUM_FISH_DIRS):
                forward_loc = forward(*loc, d=d)
                if (
                    is_valid(*forward_loc)
                    and forward_loc not in scent
                    and forward_loc != shark_loc
                ):
                    next_loc = forward_loc
                    break

                d = (d - 1) % NUM_FISH_DIRS

            next_fish_locs[next_loc].append(d)

    return next_fish_locs


def move_shark(
    shark_loc: Location, fish_locs: dict[Location, list[int]], scent: dict[Location, int]
) -> tuple[Location, dict[Location, list[int]], dict[Location, int]]:
    def dfs(
        r: int,
        c: int,
        *,
        route: list[Location],
        num_excluded: int,
        remaining: int
    ) -> tuple[int, list[Location]]:
        if not remaining:
            return num_excluded, route

        max_excluded = None
        max_route = None
        for loc in [Location(r + SHARK_DR[d], c + SHARK_DC[d]) for d in range(NUM_SHARK_DIRS)]:
            if not is_valid(*loc):
                continue

            next_excluded = num_excluded
            if loc in fish_locs and loc not in route:
                next_excluded += len(fish_locs[loc])

            total_excluded, total_route = dfs(
                *loc,
                route=route + [loc],
                num_excluded=next_excluded,
                remaining=remaining - 1
            )

            if max_excluded is None or total_excluded > max_excluded:
                max_excluded = total_excluded
                max_route = total_route

        return max_excluded, max_route

    num_excluded, route = dfs(
        *shark_loc,
        route=[],
        num_excluded=0,
        remaining=3
    )

    for loc in route:
        if loc not in fish_locs:
            continue

        fish_locs.pop(loc)
        scent[loc] = 2

    shark_loc = route[-1]

    return shark_loc, fish_locs, scent


if __name__ == "__main__":
    main()
