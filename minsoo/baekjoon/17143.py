# https://www.acmicpc.net/problem/17143


DR = [-1, 1, 0, 0]
DC = [0, 0, 1, -1]


class Env:
    def __init__(self):
        self.r, self.c, m = map(int, input().split())
        self.shark_map = [[None for _ in range(self.c)] for _ in range(self.r)]
        self.sharks = set()
        for _ in range(m):
            r, c, speed, d, size = map(int, input().split())
            self.shark_map[r - 1][c - 1] = (size, d - 1, speed)
            self.sharks.add((r - 1, c - 1))

    def _pop(self, r: int, c: int):
        size, *_ = self.shark_map[r][c]
        self.shark_map[r][c] = None
        self.sharks.remove((r, c))
        return size

    def _step(self, r: int, c: int, d: int, speed: int):
        _r = r + speed * DR[d]
        if _r >= self.r:
            div, mod = divmod(_r, self.r - 1)
            if div % 2:
                d ^= 1
                _r = self.r - 1 - mod
            else:
                _r = mod
        elif _r < 0:
            div, mod = divmod(abs(_r), self.r - 1)
            if div % 2:
                _r = self.r - 1 - mod
            else:
                d ^= 1
                _r = mod

        _c = c + speed * DC[d]
        if _c >= self.c:
            div, mod = divmod(_c, self.c - 1)
            if div % 2:
                d ^= 1
                _c = self.c - 1 - mod
            else:
                _c = mod
        elif _c < 0:
            div, mod = divmod(abs(_c), self.c - 1)
            if div % 2:
                _c = self.c - 1 - mod
            else:
                d ^= 1
                _c = mod

        return _r, _c, d

    def step(self, fish_at: int):
        size = 0
        for r, shark in enumerate(self[fish_at]):
            if shark:
                size = self._pop(r, fish_at)
                break

        _shark_map = [[None for _ in range(self.c)] for _ in range(self.r)]
        _sharks = set()
        for r, c in sorted(
            self.sharks,
            key=lambda x: self.shark_map[x[0]][x[1]][0],
            reverse=True,
        ):
            _size, d, speed = self.shark_map[r][c]

            _r, _c, d = self._step(r, c, d, speed)

            if _shark_map[_r][_c]:
                self._pop(r, c)
                continue

            _shark_map[_r][_c] = (_size, d, speed)
            _sharks.add((_r, _c))

        self.shark_map = _shark_map
        self.sharks = _sharks

        return size

    def __getitem__(self, c: int):
        return list(zip(*self.shark_map))[c]

    def __repr__(self):
        return "\n".join(f"{row}" for row in self.shark_map)


env = Env()

size_sum = 0
for fish_at in range(env.c):
    size = env.step(fish_at)
    size_sum += size

print(size_sum)
