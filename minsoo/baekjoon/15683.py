# https://www.acmicpc.net/problem/15683

from copy import deepcopy

WATCHING_DIRS = {
    1: [1, 0, 0, 0],
    2: [1, 0, 1, 0],
    3: [1, 1, 0, 0],
    4: [1, 1, 1, 0],
    5: [1, 1, 1, 1],
}
WALL = 6

DR = [-1, 0, 1, 0]
DC = [0, 1, 0, -1]


class CCTV:
    def __init__(self, r: int, c: int, watching_dirs: list[int]):
        self.loc = (r, c)
        self._watching_dirs = watching_dirs
        self._ref_d = 0

    def rotate(self):
        self._ref_d = (self._ref_d - 1) % 4

    def __getitem__(self, idx):
        return self._watching_dirs[(self._ref_d + idx) % 4]


m, n = map(int, input().split())
room = [list(map(int, input().split())) for _ in range(m)]

cctvs = []
n_walls = 0
for r, row in enumerate(room):
    for c, val in enumerate(row):
        if val == WALL:
            n_walls += 1
        elif val:
            cctvs.append(CCTV(r, c, WATCHING_DIRS[val]))


def is_valid(r, c):
    return 0 <= r < m and 0 <= c < n


def watch(room: list[list[int]], loc: tuple[int, int], d: int):
    r, c = loc
    _r, _c = r + DR[d], c + DC[d]
    watching = 0
    while is_valid(_r, _c):
        if room[_r][_c] == WALL:
            break

        if not room[_r][_c]:
            room[_r][_c] = -1
            watching += 1

        _r, _c = _r + DR[d], _c + DC[d]

    return watching


def dfs(room: list[list[int]], cctv_idx: int = 0, watching: int = 0):
    if cctv_idx == len(cctvs):
        return watching

    max_watching = 0

    cctv = cctvs[cctv_idx]
    for rot in range(4):
        if rot:
            cctv.rotate()

        _watching = 0
        _room = deepcopy(room)
        for d in range(4):
            if cctv[d]:
                _watching += watch(_room, cctv.loc, d)

        max_watching = max(
            max_watching, dfs(_room, cctv_idx + 1, watching + _watching)
        )

    return max_watching


print(m * n - (dfs(room) + len(cctvs) + n_walls))
