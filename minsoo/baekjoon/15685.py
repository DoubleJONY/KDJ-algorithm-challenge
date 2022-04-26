# https://www.acmicpc.net/problem/15685

DX = [1, 0, -1, 0]
DY = [0, -1, 0, 1]


class DragonCurve:
    def __init__(self, x: int, y: int, d: int, g: int):
        self.components = [(x, y), (x + DX[d], y + DY[d])]
        for _ in range(g):
            self._next_gen()

    @staticmethod
    def _rotate_ccw(x: int, y: int):
        return (-y, x)

    def _next_gen(self):
        x_o, y_o = self.components[-1]

        _components = []
        for x, y in self.components[:-1][::-1]:
            _x, _y = self._rotate_ccw(x - x_o, y - y_o)
            _components.append((_x + x_o, _y + y_o))

        self.components.extend(_components)

    @classmethod
    def make(cls, x: int, y: int, d: int, g: int):
        return set(cls(x, y, d, g).components)


def count_squares(components: set[tuple[int, int]]):
    num_squares = 0
    for y in range(100):
        for x in range(100):
            if (
                (x, y) in components
                and (x + 1, y) in components
                and (x, y + 1) in components
                and (x + 1, y + 1) in components
            ):
                num_squares += 1

    return num_squares


curve_components = set()
for _ in range(int(input())):
    curve_components |= DragonCurve.make(*map(int, input().split()))

print(count_squares(curve_components))
