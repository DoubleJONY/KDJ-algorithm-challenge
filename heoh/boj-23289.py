from __future__ import annotations
from collections import defaultdict
from dataclasses import dataclass


RIGHT = 0
LEFT = 1
UP = 2
DOWN = 3
OFFSET = [(0, 1), (0, -1), (-1, 0), (1, 0)]

WALL_UP = 1
WALL_RIGHT = 2


class Solution:
    def read_input(self):
        CELL_EMPTY = 0
        CELL_CENSOR = 5
        CELL_TO_DIR = {1: RIGHT, 2: LEFT, 3: UP, 4: DOWN}
        WALL_TO_FLAG = {0: WALL_UP, 1: WALL_RIGHT}

        self.R, self.C, self.K = map(int, input().split())

        self.heaters = []
        self.censors = []
        for r in range(self.R):
            for c, v in enumerate(map(int, input().split())):
                if v == CELL_EMPTY:
                    continue
                elif v == CELL_CENSOR:
                    self.censors.append((r, c))
                else:
                    self.heaters.append(Heater(r, c, CELL_TO_DIR[v]))

        self.W = int(input())
        self.walls = defaultdict(lambda: 0)
        for _ in range(self.W):
            x, y, t = map(int, input().split())
            self.walls[x-1, y-1] |= WALL_TO_FLAG[t]

    def solve(self):
        self.init()

        while not self.check_temperature_is_over_k():
            self.spread_air()
            self.balance_heat()
            self.cool_border()
            self.eat_chocolate()

            if self.n_chocolates > 100:
                break

        return self.n_chocolates

    def init(self):
        self.n_chocolates = 0
        self.map = [[0] * self.C for _ in range(self.R)]

    def spread_air(self):
        for heater in self.heaters:
            self.spread_air_from(heater)

    def spread_air_from(self, heater: Heater):
        SPREAD_CHECKER = {
            LEFT: self.get_next_cells_l,
            RIGHT: self.get_next_cells_r,
            UP: self.get_next_cells_u,
            DOWN: self.get_next_cells_d,
        }

        get_next_cells = SPREAD_CHECKER[heater.dir]
        dr, dc = OFFSET[heater.dir]
        r, c = heater.row+dr, heater.col+dc
        
        next_cells = set()
        next_cells.add((r, c))

        for t in range(5, 0, -1):
            current_cells = next_cells
            next_cells = set()
            for r, c in current_cells:
                self.map[r][c] += t

                for nr, nc in get_next_cells(r, c):
                    next_cells.add((nr, nc))

    def get_next_cells_l(self, r, c):
        next_cells = []
        nr, nc = r, c-1
        if self.check_valid_pos(nr, nc):
            if not self.has_left_wall(r, c):
                next_cells.append((nr, nc))
        nr, nc = r-1, c-1
        if self.check_valid_pos(nr, nc):
            if not self.has_up_wall(r, c) and not self.has_right_wall(nr, nc):
                next_cells.append((nr, nc))
        nr, nc = r+1, c-1
        if self.check_valid_pos(nr, nc):
            if not self.has_down_wall(r, c) and not self.has_right_wall(nr, nc):
                next_cells.append((nr, nc))
        return next_cells

    def get_next_cells_r(self, r, c):
        next_cells = []
        nr, nc = r, c+1
        if self.check_valid_pos(nr, nc):
            if not self.has_right_wall(r, c):
                next_cells.append((nr, nc))
        nr, nc = r-1, c+1
        if self.check_valid_pos(nr, nc):
            if not self.has_up_wall(r, c) and not self.has_left_wall(nr, nc):
                next_cells.append((nr, nc))
        nr, nc = r+1, c+1
        if self.check_valid_pos(nr, nc):
            if not self.has_down_wall(r, c) and not self.has_left_wall(nr, nc):
                next_cells.append((nr, nc))
        return next_cells

    def get_next_cells_u(self, r, c):
        next_cells = []
        nr, nc = r-1, c
        if self.check_valid_pos(nr, nc):
            if not self.has_up_wall(r, c):
                next_cells.append((nr, nc))
        nr, nc = r-1, c-1
        if self.check_valid_pos(nr, nc):
            if not self.has_left_wall(r, c) and not self.has_down_wall(nr, nc):
                next_cells.append((nr, nc))
        nr, nc = r-1, c+1
        if self.check_valid_pos(nr, nc):
            if not self.has_right_wall(r, c) and not self.has_down_wall(nr, nc):
                next_cells.append((nr, nc))
        return next_cells

    def get_next_cells_d(self, r, c):
        next_cells = []
        nr, nc = r+1, c
        if self.check_valid_pos(nr, nc):
            if not self.has_down_wall(r, c):
                next_cells.append((nr, nc))
        nr, nc = r+1, c-1
        if self.check_valid_pos(nr, nc):
            if not self.has_left_wall(r, c) and not self.has_up_wall(nr, nc):
                next_cells.append((nr, nc))
        nr, nc = r+1, c+1
        if self.check_valid_pos(nr, nc):
            if not self.has_right_wall(r, c) and not self.has_up_wall(nr, nc):
                next_cells.append((nr, nc))
        return next_cells

    def has_left_wall(self, r, c):
        return self.walls[r, c-1] & WALL_RIGHT != 0

    def has_right_wall(self, r, c):
        return self.walls[r, c] & WALL_RIGHT != 0

    def has_up_wall(self, r, c):
        return self.walls[r, c] & WALL_UP != 0

    def has_down_wall(self, r, c):
        return self.walls[r+1, c] & WALL_UP != 0

    def check_valid_pos(self, r, c):
        return (0 <= r < self.R) and (0 <= c < self.C)

    def balance_heat(self):
        visited = set()
        next_map = [[0] * self.C for _ in range(self.R)]
        for r in range(self.R):
            for c in range(self.C):
                v = self.map[r][c]
                next_map[r][c] += v
                for nd, (dr, dc) in enumerate(OFFSET):
                    nr, nc = r+dr, c+dc
                    if (nr, nc, r, c) in visited:
                        continue
                    if not self.check_valid_pos(nr, nc):
                        continue
                    if nd == LEFT and self.has_left_wall(r, c):
                        continue
                    if nd == RIGHT and self.has_right_wall(r, c):
                        continue
                    if nd == UP and self.has_up_wall(r, c):
                        continue
                    if nd == DOWN and self.has_down_wall(r, c):
                        continue
                    visited.add((r, c, nr, nc))
                    nv = self.map[nr][nc]
                    diff = abs(nv - v) // 4
                    if v > nv:
                        next_map[r][c] -= diff
                        next_map[nr][nc] += diff
                    else:
                        next_map[r][c] += diff
                        next_map[nr][nc] -= diff
        self.map = next_map

    def cool_border(self):
        for r in range(self.R):
            c = 0
            if self.map[r][c] > 0:
                self.map[r][c] -= 1
            c = self.C - 1
            if self.map[r][c] > 0:
                self.map[r][c] -= 1
        for c in range(1, self.C-1):
            r = 0
            if self.map[r][c] > 0:
                self.map[r][c] -= 1
            r = self.R - 1
            if self.map[r][c] > 0:
                self.map[r][c] -= 1

    def eat_chocolate(self):
        self.n_chocolates += 1

    def check_temperature_is_over_k(self):
        for r, c in self.censors:
            if self.map[r][c] < self.K:
                return False
        return True


@dataclass
class Heater:
    row: int
    col: int
    dir: int


def main():
    solution = Solution()
    solution.read_input()
    answer = solution.solve()
    print(answer)


if __name__ == '__main__':
    main()
