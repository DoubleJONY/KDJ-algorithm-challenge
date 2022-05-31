DESCRIPTION = """
S2D2는 1x1 크기의 칸에 들어있는 양분을 조사해 상도에게 전송하고, 모든 칸에 대해서 조사를 한다. 가장 처음에 양분은 모든 칸에 5만큼 들어있다.

봄에는 나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가한다. 각각의 나무는 나무가 있는 1x1 크기의 칸에 있는 양분만 먹을 수 있다.
하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다.
만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.

여름에는 봄에 죽은 나무가 양분으로 변하게 된다. 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다. 소수점 아래는 버린다.

가을에는 나무가 번식한다. 번식하는 나무는 나이가 5의 배수이어야 하며, 인접한 8개의 칸에 나이가 1인 나무가 생긴다.

겨울에는 S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다. 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.

K년이 지난 후 상도의 땅에 살아있는 나무의 개수를 구하는 프로그램을 작성하시오.

link: https://www.acmicpc.net/problem/16235
"""


DR = [-1, -1, 0, 1, 1, 1, 0, -1]
DC = [0, 1, 1, 1, 0, -1, -1, -1]


class S2D2:
    def __init__(
        self,
        trees: list[list[int]],
        nutrients: list[list[int]],
        fertilize_annually: list[list[int]],
    ):
        self.trees = trees
        self.dead_trees = [[None for _ in self.trees[0]] for _ in self.trees]
        self.nutrients = nutrients
        self.fertilize_annually = fertilize_annually

    def _valid(self, r: int, c: int):
        return 0 <= r < len(self.trees) and 0 <= c < len(self.trees[0])

    def _step_spring(self):
        for r, row in enumerate(self.trees):
            for c, trees in enumerate(row):
                if not trees:
                    continue

                grown_ups = []

                trees = sorted(trees)
                for tree in trees:
                    if tree > self.nutrients[r][c]:
                        break

                    self.nutrients[r][c] -= tree
                    grown_ups.append(tree + 1)

                self.trees[r][c] = grown_ups
                self.dead_trees[r][c] = trees[len(grown_ups) :]

    def _step_summer(self):
        for r, row in enumerate(self.dead_trees):
            for c, dead_trees in enumerate(row):
                if not dead_trees:
                    continue

                nutrient = sum(dead_tree // 2 for dead_tree in dead_trees)
                self.nutrients[r][c] += nutrient
                self.dead_trees[r][c] = None

    def _step_autumn(self):
        for r, row in enumerate(self.trees):
            for c, trees in enumerate(row):
                for tree in sorted(trees, reverse=True):
                    if tree < 5:
                        break
                    if tree % 5:
                        continue

                    for d in range(8):
                        r_adj, c_adj = r + DR[d], c + DC[d]
                        if not self._valid(r_adj, c_adj):
                            continue

                        self.trees[r_adj][c_adj].append(1)

    def _step_winter(self):
        for r in range(len(self.trees)):
            for c in range(len(self.trees[0])):
                self.nutrients[r][c] += self.fertilize_annually[r][c]

    def step(self):
        self._step_spring()
        self._step_summer()
        self._step_autumn()
        self._step_winter()

    @classmethod
    def from_standard_input(cls, size: int, num_trees_to_plant: int):
        trees = [[[] for _ in range(size)] for _ in range(size)]
        nutrients = [[5 for _ in range(size)] for _ in range(size)]
        fertilize_annually = [
            list(map(int, input().split())) for _ in range(size)
        ]

        for _ in range(num_trees_to_plant):
            r, c, age = map(int, input().split())
            trees[r - 1][c - 1].append(age)

        return cls(trees, nutrients, fertilize_annually)

    @property
    def num_total_trees(self):
        return sum(sum(len(trees) for trees in row) for row in self.trees)


size, num_trees_to_plant, years = map(int, input().split())

s2d2 = S2D2.from_standard_input(size, num_trees_to_plant)
for _ in range(years):
    s2d2.step()

print(s2d2.num_total_trees)
