# https://www.acmicpc.net/problem/20056

from collections import defaultdict, namedtuple

DR = [-1, -1, 0, 1, 1, 1, 0, -1]
DC = [0, 1, 1, 1, 0, -1, -1, -1]

Fire = namedtuple("Fire", ["r", "c", "m", "s", "d"])


class FireField:
    def __init__(self, size: int, fires: list[Fire]):
        self.size = size
        self.fires = fires

    def move(self) -> dict[tuple[int, int], int]:
        moved = defaultdict(list)

        def _move(fire: Fire):
            s = fire.s
            d = fire.d

            r = (fire.r + s * DR[d]) % self.size
            c = (fire.c + s * DC[d]) % self.size

            return r, c

        for index, fire in enumerate(self.fires):
            nr, nc = _move(fire)
            moved[(nr, nc)].append(index)
            self.fires[index] = Fire(nr, nc, *fire[2:])

        return moved

    def merge(self, moved: dict[tuple[int, int], int]):
        fires = []

        for (r, c), indices in moved.items():
            if len(indices) == 1:
                fire = self.fires[indices[0]]
                fires.append(fire)
            else:
                m_sum = 0
                s_sum = 0

                d_mod_sum = 0

                for index in indices:
                    _fire = self.fires[index]

                    m_sum += _fire.m
                    s_sum += _fire.s

                    d_mod_sum += _fire.d % 2

                m = m_sum // 5
                s = s_sum // len(indices)

                if not m:
                    continue

                directions = [0, 2, 4, 6] if not d_mod_sum or d_mod_sum == len(indices) else [1, 3, 5, 7]

                for d in directions:
                    fires.append(Fire(r, c, m, s, d))

        self.fires = fires

    def step(self):
        moved = self.move()
        self.merge(moved)


def stdio_bj20056() -> tuple[int, list[Fire], int]:
    """
    첫째 줄에 N, M, K가 주어진다.
    둘째 줄부터 M개의 줄에 파이어볼의 정보가 한 줄에 하나씩 주어진다. 파이어볼의 정보는 다섯 정수 ri, ci, mi, si, di로 이루어져 있다.
    서로 다른 두 파이어볼의 위치가 같은 경우는 입력으로 주어지지 않는다.
    """

    size, num_fires, num_moves = map(int, input().split())

    def get_input_fire():
        r, c, m, s, d = map(int, input().split())
        return r - 1, c - 1, m, s, d

    fires = [Fire(*get_input_fire()) for _ in range(num_fires)]

    return size, fires, num_moves


def main():
    size, fires, num_moves = stdio_bj20056()

    field = FireField(size, fires)
    for _ in range(num_moves):
        field.step()

    print(sum(fire.m for fire in field.fires))


if __name__ == "__main__":
    main()
