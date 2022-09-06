# https://www.acmicpc.net/problem/19236

from collections import deque
from copy import deepcopy
from dataclasses import dataclass

DR = [-1, -1, 0, 1, 1, 1, 0, -1]
DC = [0, -1, -1, -1, 0, 1, 1, 1]


def is_valid(r: int, c: int):
    return 0 <= r < 4 and 0 <= c < 4


@dataclass
class Shark:
    loc: tuple[int, int]
    d: int
    ate: int

    def loc_dist_away(self, dist: int):
        r, c = self.loc
        return r + dist * DR[self.d], c + dist * DC[self.d]


class Env:
    def __init__(self):
        self.fish_map = [[] for _ in range(4)]
        self.fish2loc = {}

        for r in range(4):
            row = list(map(int, input().split()))
            for c in range(4):
                self.fish_map[r].append((row[2 * c], row[2 * c + 1] - 1))
                self.fish2loc[row[2 * c]] = (r, c)

        self.shark = None
        self.locate_shark(0, 0)

    def _eat(self, r: int, c: int):
        if self.fish_map[r][c] is None:
            raise ValueError

        fish, _ = self.fish_map[r][c]
        self.fish_map[r][c] = None
        self.fish2loc.pop(fish)

    def locate_shark(self, r: int, c: int):
        self.shark = Shark(
            loc=(r, c),
            d=self.fish_map[r][c][1],
            ate=self.fish_map[r][c][0] + (self.shark.ate if self.shark else 0),
        )
        self._eat(r, c)

    def _rotate(self, d: int):
        for _ in range(8):
            yield d % 8
            d += 1

    def _move(self, fish: int):
        if fish not in self.fish2loc:
            return

        r, c = self.fish2loc[fish]
        d = self.fish_map[r][c][1]
        for _d in self._rotate(d):
            _r, _c = r + DR[_d], c + DC[_d]
            if not is_valid(_r, _c) or (_r, _c) == self.shark.loc:
                continue

            tmp = self.fish_map[_r][_c]
            self.fish_map[r][c] = tmp
            self.fish_map[_r][_c] = (fish, _d)

            self.fish2loc[fish] = (_r, _c)
            if tmp:
                self.fish2loc[tmp[0]] = (r, c)
            return

    def organize(self):
        for fish in range(1, 17):
            self._move(fish)


def bfs():
    max_ate = 0

    envs = deque([Env()])
    while envs:
        env = envs.popleft()
        max_ate = max(max_ate, env.shark.ate)
        env.organize()

        for dist in range(1, 4):
            _env = deepcopy(env)
            r, c = _env.shark.loc_dist_away(dist)
            if not is_valid(r, c) or _env.fish_map[r][c] is None:
                continue
            _env.locate_shark(r, c)
            envs.append(_env)

    return max_ate


print(bfs())
