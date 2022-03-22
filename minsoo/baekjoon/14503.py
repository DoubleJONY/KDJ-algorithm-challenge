# https://www.acmicpc.net/problem/14503


class Vaccum:
    dr, dc = [-1, 0, 1, 0], [0, 1, 0, -1]

    def __init__(self) -> None:
        m, n = map(int, input().split())
        *self.position, self.direction = map(int, input().split())
        self.map = [list(map(int, input().split())) for _ in range(m)]
        self.n_cleaned = 0

    def rotate(self):
        self.direction = (self.direction - 1) % 4

    def can_move(self, backward=False):
        r, c = self.position
        if not backward:
            return self.map[r + self.dr[self.direction]][c + self.dc[self.direction]] == 0
        return self.map[r - self.dr[self.direction]][c - self.dc[self.direction]] != 1

    def move(self, backward=False):
        r, c = self.position
        d = (self.direction - 2) % 4 if backward else self.direction
        self.position = (r + self.dr[d], c + self.dc[d])

    def _clean(self):
        r, c = self.position
        if self.map[r][c] == 0:
            self.map[r][c] = -1
            self.n_cleaned += 1

        f = False
        for _ in range(4):
            self.rotate()
            if self.can_move():
                self.move()
                f = True
                break

        if not f:
            if not self.can_move(backward=True):
                return self.n_cleaned
            self.move(backward=True)

        return self._clean()

    @classmethod
    def clean(cls):
        return cls()._clean()


print(Vaccum.clean())
