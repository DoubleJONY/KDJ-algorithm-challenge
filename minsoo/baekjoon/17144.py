# https://www.acmicpc.net/problem/17144


PURIFIER = -1


def find_purifier(dust_map: list[list[int]]):
    for r in range(len(dust_map)):
        if dust_map[r][0] == PURIFIER:
            return r, r + 1


def is_valid(r: int, c: int, dust_map: list[list[int]]):
    return (
        0 <= r < len(dust_map)
        and 0 <= c < len(dust_map[0])
        and dust_map[r][c] != PURIFIER
    )


def spread(dust_map: list[list[int]]):
    spreaded_dust_map = [
        [PURIFIER if dust == PURIFIER else 0 for dust in row]
        for row in dust_map
    ]

    for r, row in enumerate(dust_map):
        for c, dust in enumerate(row):
            if dust == PURIFIER:
                continue

            to_be_spreaded = dust // 5
            cnt = 0
            for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                if not is_valid(r + dr, c + dc, spreaded_dust_map):
                    continue

                spreaded_dust_map[r + dr][c + dc] += to_be_spreaded
                cnt += 1

            remaining = dust - cnt * to_be_spreaded
            spreaded_dust_map[r][c] += remaining

    return spreaded_dust_map


def purify(dust_map: list[list[int]], purifier_loc: tuple[int, int]):
    upper_loc, lower_loc = purifier_loc

    r, c = upper_loc, 0
    for dr, dc in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
        while 0 <= r + dr <= upper_loc and 0 <= c + dc < len(dust_map[0]):
            if dust_map[r + dr][c + dc] == PURIFIER:
                dust_map[r][c] = 0
            elif dust_map[r][c] != PURIFIER:
                dust_map[r][c] = dust_map[r + dr][c + dc]

            r += dr
            c += dc

    r, c = lower_loc, 0
    for dr, dc in [(1, 0), (0, 1), (-1, 0), (0, -1)]:
        while (
            lower_loc <= r + dr < len(dust_map)
            and 0 <= c + dc < len(dust_map[0])
        ):
            if dust_map[r + dr][c + dc] == PURIFIER:
                dust_map[r][c] = 0
            elif dust_map[r][c] != PURIFIER:
                dust_map[r][c] = dust_map[r + dr][c + dc]

            r += dr
            c += dc

    return dust_map


def total_dust(dust_map: list[list[int]]):
    return sum(
        sum(filter(lambda dust: dust != PURIFIER, row)) for row in dust_map
    )


r, c, t = map(int, input().split())
dust_map = [list(map(int, input().split())) for _ in range(r)]
purifier_loc = find_purifier(dust_map)

for _ in range(t):
    dust_map = spread(dust_map)
    dust_map = purify(dust_map, purifier_loc)

print(total_dust(dust_map))
