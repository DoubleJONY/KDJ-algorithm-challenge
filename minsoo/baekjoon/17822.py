# https://www.acmicpc.net/problem/17822


class RollingDisk:
    def __init__(self, *numbers_on_disk):
        self.numbers = list(numbers_on_disk)
        self.reference = 0

    def __len__(self):
        return len(self.numbers)

    def __getitem__(self, idx: int):
        return self.numbers[(self.reference + idx) % len(self)]

    def __setitem__(self, idx: int, item: int):
        self.numbers[(self.reference + idx) % len(self)] = item

    def __iter__(self):
        for idx in range(len(self.numbers)):
            yield self[idx]

    def roll(self, ccw: bool, *, k: int):
        self.reference += k if ccw else -k
        self.reference %= len(self)


class ConcentricRollingDisks:
    def __init__(self, disks: list[RollingDisk]):
        self.disks = disks

    def __len__(self):
        return len(self.disks)

    def __getitem__(self, idx: int):
        return self.disks[idx]

    def __repr__(self):
        return "\n".join(" ".join(str(n) for n in disk) for disk in self.disks)


def step(concentric_disks: ConcentricRollingDisks, instruction: list[int]):
    n, ccw, k = instruction
    for _n in range(n, len(concentric_disks) + 1, n):
        concentric_disks[_n - 1].roll(ccw, k=k)

    identicals = set()
    for n, disk in enumerate(concentric_disks):
        for idx, number in enumerate(disk):
            if not number:
                continue
            if disk[idx - 1] != number:
                continue

            identicals.add((n, idx))
            identicals.add((n, idx - 1))

        if n == len(concentric_disks) - 1:
            continue

        for idx, number in enumerate(disk):
            if not number:
                continue
            if concentric_disks[n + 1][idx] != number:
                continue

            identicals.add((n, idx))
            identicals.add((n + 1, idx))

    for n, idx in identicals:
        concentric_disks[n][idx] = 0

    if not identicals:
        accumulated = 0
        num_total = 0
        for disk in concentric_disks:
            disk = list(filter(lambda number: number > 0, disk))
            accumulated += sum(disk)
            num_total += len(disk)

        if not num_total:
            return

        mean = accumulated / num_total
        for disk in concentric_disks:
            for idx, number in enumerate(disk):
                if not number:
                    continue
                if number == mean:
                    continue

                disk[idx] += 1 if number < mean else -1


def main():
    num_disks, _, num_steps = map(int, input().split())
    disks = [RollingDisk(*map(int, input().split())) for _ in range(num_disks)]
    instructions = [tuple(map(int, input().split())) for _ in range(num_steps)]

    concentric_disks = ConcentricRollingDisks(disks)
    for instruction in instructions:
        step(concentric_disks, instruction)

    print(sum(sum(disk) for disk in concentric_disks))


if __name__ == "__main__":
    main()
