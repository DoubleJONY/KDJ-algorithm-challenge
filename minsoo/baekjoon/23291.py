# https://www.acmicpc.net/problem/23291


def add_fish_to_min(tanks: list[int]):
    min_fish = min(tanks)
    for i, n_fish in enumerate(tanks):
        if n_fish == min_fish:
            tanks[i] += 1
    return tanks


def unsqueeze(tanks: list[int]):
    return list(map(lambda x: [x], tanks))


def get_wrap_pivot(tanks: list[list[int]]):
    for i, _tanks in enumerate(tanks):
        if len(_tanks) == 1:
            return i
    return float("inf")


def wrap(tanks: list[list[int]], pivot: int):
    if not pivot:
        pivot = 1

    lifted, base = tanks[:pivot], tanks[pivot:]
    for i, _lifted in enumerate(list(map(reversed, zip(*lifted)))):
        base[i].extend(_lifted)
    return base


def wrap_while_possible(tanks: list[list[int]]):
    while len(tanks) - (pivot := get_wrap_pivot(tanks)) >= len(tanks[0]):
        tanks = wrap(tanks, pivot=pivot)
    return tanks


def fold(tanks: list[list[int]]):
    pivot = len(tanks) // 2
    lifted, base = tanks[:pivot], tanks[pivot:]
    for i, _lifted in enumerate(list(map(reversed, lifted))[::-1]):
        base[i].extend(_lifted)
    return base


def is_valid(tanks: list[list[int]], i: int, j: int):
    return 0 <= i < len(tanks) and 0 <= j < len(tanks[i])


def distribute(tanks: list[list[int]]):
    diffs = [[0 for _ in row] for row in tanks]
    for i, _tanks in enumerate(tanks):
        for j, tank in enumerate(_tanks):
            for di, dj in [(-1, 0), (0, 1), (1, 0), (0, -1)]:
                _i, _j = i + di, j + dj
                if not is_valid(tanks, _i, _j):
                    continue

                diff = tanks[_i][_j] - tank
                if diff < 0:
                    diff = -(abs(diff) // 5)
                else:
                    diff = diff // 5

                diffs[i][j] += diff
                diffs[_i][_j] -= diff

    for i, _diffs in enumerate(diffs):
        for j, diff in enumerate(_diffs):
            tanks[i][j] += diff // 2
    return tanks


def flatten(tanks: list[list[int]]):
    flattened = []
    for _tanks in tanks:
        flattened.extend(_tanks)
    return flattened


def organize(tanks: list[int], k: int):
    cnt = 0
    while max(tanks) - min(tanks) > k:
        tanks = add_fish_to_min(tanks)
        tanks = wrap_while_possible(unsqueeze(tanks))
        tanks = distribute(tanks)
        tanks = flatten(tanks)
        tanks = fold(fold(unsqueeze(tanks)))
        tanks = distribute(tanks)
        tanks = flatten(tanks)

        cnt += 1
    return cnt


n, k = map(int, input().split())
tanks = list(map(int, input().split()))
print(organize(tanks, k))
