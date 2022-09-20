# https://www.acmicpc.net/problem/19237

from collections import deque

BLANK = -1

DR = [-1, 1, 0, 0]
DC = [0, 0, -1, 1]


class Scent:
    def __init__(self, idx: int, lasts: int = 0):
        self.idx = idx
        self.lasts = lasts

    def __isub__(self, other):
        if self.lasts > 0:
            self.lasts -= 1
        return self

    def __bool__(self):
        return self.lasts > 0


class SharkEnv:
    def __init__(
        self,
        index_map: list[list[int]],
        directions: list[int],
        direction_priorities: list[list[list[int]]],
        scent_lasts: int
    ):
        locations = [
            (idx, r, c)
            for r, row in enumerate(index_map)
            for c, idx in enumerate(row)
            if idx != BLANK
        ]
        locations = sorted(locations)

        self.locations = deque(list(map(lambda x: x[1:], locations)))

        self.scent_map = [
            [Scent(idx, 0 if idx == BLANK else scent_lasts) for c, idx in enumerate(row)]
            for r, row in enumerate(index_map)
        ]

        self.index_map = index_map
        self.directions = directions

        self.direction_priorities = direction_priorities
        self.scent_lasts = scent_lasts

    def step(self):
        self.locations, locations = deque([]), self.locations

        index_map = [[BLANK for _ in row] for row in self.index_map]

        while locations:
            r, c = locations.popleft()

            idx = self.index_map[r][c]
            d = self.directions[idx]

            moved_or_skipped = False
            for nd in self.direction_priorities[idx][d]:
                nr, nc = r + DR[nd], c + DC[nd]

                def can_move():
                    if not (0 <= nr < len(index_map) and 0 <= nc < len(index_map[0])):
                        return False
                    if self.scent_map[nr][nc]:
                        return False
                    return True

                if not can_move():
                    continue

                if index_map[nr][nc] != BLANK:
                    moved_or_skipped = True
                    break

                self.locations.append((nr, nc))

                index_map[nr][nc] = idx
                self.directions[idx] = nd

                moved_or_skipped = True
                break

            if not moved_or_skipped:
                for nd in self.direction_priorities[idx][d]:
                    nr, nc = r + DR[nd], c + DC[nd]

                    if not (0 <= nr < len(index_map) and 0 <= nc < len(index_map[0])):
                        continue
                    if self.scent_map[nr][nc].idx != idx:
                        continue

                    self.locations.append((nr, nc))

                    index_map[nr][nc] = idx
                    self.directions[idx] = nd
                    break

        for r, row in enumerate(self.scent_map):
            for c, scent in enumerate(row):
                scent -= 1

        for r, c in self.locations:
            self.scent_map[r][c] = Scent(index_map[r][c], lasts=self.scent_lasts)

        self.index_map = index_map

        return len(self.locations) == 1


def make_shark_env():
    map_size, num_sharks, scent_lasts = map(int, input().split())

    index_map = [list(map(lambda n: int(n) - 1, input().split())) for _ in range(map_size)]
    directions = list(map(lambda n: int(n) - 1, input().split()))

    direction_priorities = [
        [list(map(lambda n: int(n) - 1, input().split())) for _ in range(4)]
        for _ in range(num_sharks)
    ]

    return SharkEnv(index_map, directions, direction_priorities, scent_lasts)


def main():
    env = make_shark_env()

    tot_t = -1
    for t in range(1000):
        if env.step():
            tot_t = t + 1
            break

    print(tot_t)


if __name__ == "__main__":
    main()
