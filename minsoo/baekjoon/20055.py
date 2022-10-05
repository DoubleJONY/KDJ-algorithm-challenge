# https://www.acmicpc.net/problem/20055


class InfiniteBelt:
    def __init__(self, belt: list):
        self.belt = belt
        self._begin = 0

    def __len__(self):
        return len(self.belt)

    def __getitem__(self, index: int):
        return self.belt[(self._begin + index) % len(self)]

    def __setitem__(self, index: int, value):
        self.belt[(self._begin + index) % len(self)] = value

    def roll(self, num_rolling_steps: int = 1):
        self._begin = (self._begin - num_rolling_steps) % len(self)


class ConveyorBelt:
    def __init__(self, durabilities: list[int, ...]):
        self.loads = InfiniteBelt([0 for _ in durabilities])
        self.durabilities = InfiniteBelt(durabilities)

        self._len = len(durabilities)
        self._half_len = self._len // 2

        self._num_worn = 0

    def roll(self):
        self.loads.roll()
        if self.loads[self._half_len - 1]:
            self.loads[self._half_len - 1] = 0

        self.durabilities.roll()

    def step(self):
        for index in range(self._half_len - 1)[::-1]:
            if self.loads[index] and not self.loads[index + 1] and self.durabilities[index + 1]:
                self.loads[index] = 0
                if index + 1 != self._half_len - 1:
                    self.loads[index + 1] = 1

                self.durabilities[index + 1] -= 1
                if not self.durabilities[index + 1]:
                    self._num_worn += 1

    def load(self):
        if self.durabilities[0]:
            self.loads[0] = 1
            self.durabilities[0] -= 1
            if not self.durabilities[0]:
                self._num_worn += 1

    @property
    def worn(self):
        return self._num_worn


def stdio_bj20055():
    length, threshold = map(int, input().split())
    durabilities = list(map(int, input().split()))

    belt = ConveyorBelt(durabilities)

    return belt, length, threshold


def main():
    belt, length, threshold = stdio_bj20055()

    cnt = 0

    while belt.worn < threshold:
        belt.roll()
        belt.step()
        belt.load()

        cnt += 1

    print(cnt)


if __name__ == "__main__":
    main()
