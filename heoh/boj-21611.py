DIRS = [None, (-1, 0), (1, 0), (0, -1), (0, 1)]
UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

COLOR = 0
COUNT = 1


class Solution:
    N, M = map(int, input().split())
    A = [list(map(int, input().split())) for _ in range(N)]
    S = [list(map(int, input().split())) for _ in range(M)]
    counts = [None, 0, 0, 0]

    def solve(self):
        for d, s in self.S:
            self.spell_blizzard(d, s)
            self.reshape_board()
        answer = (1 * self.counts[1]) + (2 * self.counts[2]) + (3 * self.counts[3])
        print(answer)

    def spell_blizzard(self, d, s):
        y = x = self.N // 2
        dy, dx = DIRS[d]
        for _ in range(s):
            y += dy
            x += dx
            self.A[y][x] = 0

    def reshape_board(self):
        groups = self.parse_group_sequence()
        groups = self.do_blast(groups)
        self.place_groups(groups)

    def parse_group_sequence(self):
        groups = [[0, 0]]
        color = -1
        for y, x in self.iter_snail():
            before_color = color
            color = self.A[y][x]
            last = groups[-1]

            if before_color == 0 and color == 0:
                break
            if color == 0:
                continue

            if last[COLOR] == color:
                last[COUNT] += 1
            else:
                last = [color, 1]
                groups.append(last)

            self.A[y][x] = 0
        return groups

    def do_blast(self, input_groups):
        groups = input_groups
        while len(next_groups := self.do_blast_group(groups)) != len(groups):
            groups = next_groups
        return groups

    def do_blast_group(self, groups):
        next_groups = groups[:1]
        blasted_groups = []
        for group in groups[1:]:
            if group[COUNT] >= 4:
                blasted_groups.append(group)
                continue

            last = next_groups[-1]
            if last[COLOR] == group[COLOR]:
                last[COUNT] += group[COUNT]
            else:
                next_groups.append(group)

        for group in blasted_groups:
            self.counts[group[COLOR]] += group[COUNT]

        return next_groups

    def place_groups(self, groups):
        locs = self.iter_snail()
        for group in groups[1:]:
            try:
                y, x = next(locs)
                self.A[y][x] = group[COUNT]
                y, x = next(locs)
                self.A[y][x] = group[COLOR]
            except StopIteration:
                break

    def iter_snail(self):
        NEXT_DIR = [None, LEFT, RIGHT, DOWN, UP]
        N = self.N
        y = x = N // 2
        d = LEFT
        n = 1
        while True:
            for _ in range(2):
                dy, dx = DIRS[d]
                for _ in range(n):
                    y += dy
                    x += dx
                    if not (0 <= y < N and 0 <= x < N):
                        return
                    yield y, x
                d = NEXT_DIR[d]
            n += 1


Solution().solve()
