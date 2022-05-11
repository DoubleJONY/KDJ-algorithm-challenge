import math
from dataclasses import dataclass
from enum import Enum
from itertools import combinations
from typing import Iterable


class CellType(Enum):
    EMPTY = 0
    HOME = 1
    SHOP = 2


@dataclass
class Cell:
    r: int
    c: int
    type: int


def main():
    N, M = map(int, input().split())
    mat = [list(map(int, input().split())) for _ in range(N)]

    cells = extract_non_empty_cells(mat)
    homes = filter_cells_by_type(cells, CellType.HOME)
    shops = filter_cells_by_type(cells, CellType.SHOP)

    answer = find_minimum_chicken_distance(M, homes, shops)
    print(answer)


def extract_non_empty_cells(mat: list[list[int]]) -> list[Cell]:
    cells = []
    for r, row in enumerate(mat):
        for c, value in enumerate(row):
            if value != CellType.EMPTY.value:
                cells.append(Cell(r, c, CellType(value)))
    return cells


def filter_cells_by_type(cells: list[Cell], type: CellType) -> list[Cell]:
    return list(filter(lambda x: x.type == type, cells))


def find_minimum_chicken_distance(M: int, homes: list[Cell], shops: list[Cell]) -> int:
    best = math.inf
    for picked_shops in combinations(shops, M):
        dist = calculate_chicken_distance(homes, picked_shops)
        best = min(best, dist)
    return best


def find_minimum_chicken_distance_v2(M: int, homes: list[Cell], shops: list[Cell]) -> int:
    def dfs(pick_index: int, picked_shops: list[Cell]) -> int:
        best = math.inf

        if pick_index >= len(shops):
            if len(picked_shops) == M:
                best = calculate_chicken_distance(homes, picked_shops)
            return best

        picked_shops.append(shops[pick_index])
        dist_when_picked = dfs(pick_index + 1, picked_shops)
        picked_shops.pop()

        dist_when_not_picked = dfs(pick_index + 1, picked_shops)

        best = min(dist_when_picked, dist_when_not_picked)
        return best

    return dfs(0, [])


def calculate_chicken_distance(homes: Iterable[Cell], shops: Iterable[Cell]) -> int:
    return sum(calculate_chicken_distance_one(home, shops) for home in homes)


def calculate_chicken_distance_one(home: Cell, shops: Iterable[Cell]) -> int:
    return min(get_distance_between(home, shop) for shop in shops)


def get_distance_between(a: Cell, b: Cell) -> int:
    return abs(a.r - b.r) + abs(a.c - b.c)


main()
