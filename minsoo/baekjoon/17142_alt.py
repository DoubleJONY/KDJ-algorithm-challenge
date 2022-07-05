# https://www.acmicpc.net/problem/17142

from collections import deque
from itertools import combinations

CORRIDOR = 0
WALL = 1
VIRUS = 2


def get_virus_locations(lab: list[list[int]]):
    return [
        (r, c)
        for r, row in enumerate(lab)
        for c, _id in enumerate(row)
        if _id == VIRUS
    ]


def get_time_for_occupation(
    lab: list[list[int]], activated_virus_locations: list[tuple[int, int]]
):
    occupied = [[False for _ in row] for row in lab]
    for r, c in activated_virus_locations:
        occupied[r][c] = True

    def valid(r: int, c: int):
        return (
            0 <= r < len(lab)
            and 0 <= c < len(lab[0])
            and lab[r][c] != WALL
            and not occupied[r][c]
        )

    t_took = 0
    q = deque((r, c, 0) for r, c in activated_virus_locations)
    while q:
        r, c, t = q.popleft()
        if lab[r][c] == CORRIDOR:
            t_took = max(t_took, t)

        for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
            nr, nc = r + dr, c + dc
            if not valid(nr, nc):
                continue

            q.append((nr, nc, t + 1))
            occupied[nr][nc] = True

    def fully_occupied():
        for r, row in enumerate(lab):
            for c, _id in enumerate(row):
                if _id != CORRIDOR:
                    continue
                if not occupied[r][c]:
                    return False

        return True

    return t_took if fully_occupied() else float("inf")


lab_size, num_to_activate = map(int, input().split())
lab = [list(map(int, input().split())) for _ in range(lab_size)]

virus_locations = get_virus_locations(lab)

min_t = min(
    get_time_for_occupation(lab, activated_virus_locations)
    for activated_virus_locations in combinations(
        virus_locations, r=num_to_activate
    )
)

print(-1 if min_t == float("inf") else min_t)
