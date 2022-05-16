from dataclasses import dataclass, field
from multipledispatch import dispatch

PROBLEM_DESCRIPTION_DOCSTRING = """
이 문제에서는 루빅스 큐브가 모두 풀린 상태에서 시작한다. 윗 면은 흰색, 아랫 면은 노란색, 앞 면은 빨간색, 뒷 면은 오렌지색, 왼쪽 면은 초록색, 오른쪽 면은 파란색이다.

첫째 줄에 큐브를 돌린 횟수 n이 주어진다. (1 ≤ n ≤ 1000)
둘째 줄에는 큐브를 돌린 방법이 주어진다. 각 방법은 공백으로 구분되어져 있으며, 첫 번째 문자는 돌린 면이다. U: 윗 면, D: 아랫 면, F: 앞 면, B: 뒷 면, L: 왼쪽 면, R: 오른쪽 면이다.
두 번째 문자는 돌린 방향이다. +인 경우에는 시계 방향 (그 면을 바라봤을 때가 기준), -인 경우에는 반시계 방향이다.

각 테스트 케이스에 대해서 큐브를 모두 돌린 후의 윗 면의 색상을 출력한다. 첫 번째 줄에는 뒷 면과 접하는 칸의 색을 출력하고, 두 번째, 세 번째 줄은 순서대로 출력하면 된다.
흰색은 w, 노란색은 y, 빨간색은 r, 오렌지색은 o, 초록색은 g, 파란색은 b.

PROBLEM: https://www.acmicpc.net/problem/5373
"""

SURFACE_DIRECTIONS = [{0: "U", 2: "D"}, {0: "B", 2: "F"}, {0: "L", 2: "R"}]
INITIAL_COLORS = {"U": "w", "D": "y", "B": "o", "F": "r", "L": "g", "R": "b"}


@dataclass
class Vec3:
    i: int
    j: int
    k: int

    def __eq__(self, other):
        return self.i == other.i and self.j == other.j and self.k == other.k


def inverted_cross_product(vec1: Vec3, vec2: Vec3):
    return Vec3(
        (vec1.k * vec2.j - vec1.j * vec2.k),
        (vec1.i * vec2.k - vec1.k * vec2.i),
        (vec1.j * vec2.i - vec1.i * vec2.j),
    )


@dispatch(Vec3, Vec3, bool)
def rotate90(vec: Vec3, rotate_axis: Vec3, clockwise: bool = True):
    if vec == rotate_axis:
        return vec

    return (
        inverted_cross_product(vec, rotate_axis)
        if clockwise
        else inverted_cross_product(rotate_axis, vec)
    )


@dispatch(int, int, str, bool)
def rotate90(r: int, c: int, surface: str, clockwise: bool = True):
    return (-c, r) if (surface in ["U", "F", "L"]) ^ clockwise else (c, -r)


@dataclass
class RubixCubeBlockSurface:
    color: str
    surface_norm_vec: Vec3

    def rotate(self, rotate_axis: Vec3, clockwise: bool = True):
        self.surface_norm_vec = rotate90(
            self.surface_norm_vec, rotate_axis, clockwise
        )


@dataclass
class RubixCubeBlock:
    block_surfaces: list

    def __getitem__(self, idx: int):
        return self.block_surfaces[idx]

    def rotate(self, rotate_axis: Vec3, clockwise: bool = True):
        for block_surface in self.block_surfaces:
            block_surface.rotate(rotate_axis, clockwise)


IN_SURFACE_IDX_FNS = {
    "U": lambda r, c: (0, r, c),
    "D": lambda r, c: (2, r, c),
    "B": lambda r, c: (r, 0, c),
    "F": lambda r, c: (r, 2, c),
    "L": lambda r, c: (r, c, 0),
    "R": lambda r, c: (r, c, 2),
}

NORMAL_VECTORS = {
    "U": Vec3(-1, 0, 0),
    "D": Vec3(1, 0, 0),
    "B": Vec3(0, -1, 0),
    "F": Vec3(0, 1, 0),
    "L": Vec3(0, 0, -1),
    "R": Vec3(0, 0, 1),
}


@dataclass
class RubixCube:
    blocks: list = field(default=None)

    def __post_init__(self):
        if self.blocks is None:
            self.blocks = [
                [[None for _ in range(3)] for _ in range(3)] for _ in range(3)
            ]

    def __getitem__(self, index: tuple[int, int, int]):
        i, j, k = index
        return self.blocks[i][j][k]

    def __setitem__(self, index: tuple[int, int, int], block: RubixCubeBlock):
        i, j, k = index
        self.blocks[i][j][k] = block

    def rotate(self, surface: str, clockwise: bool = True):
        in_surface_idx_fn = IN_SURFACE_IDX_FNS[surface]

        tmps = [[None for _ in range(3)] for _ in range(3)]
        for r in range(3):
            for c in range(3):
                in_surface_idx = in_surface_idx_fn(r, c)
                tmps[r][c] = self[in_surface_idx]

        rotate_axis = NORMAL_VECTORS[surface]

        for r in range(3):
            for c in range(3):
                _r, _c = rotate90(r - 1, c - 1, surface, clockwise)
                in_surface_idx = in_surface_idx_fn(_r + 1, _c + 1)
                tmps[r][c].rotate(rotate_axis, clockwise)
                self[in_surface_idx] = tmps[r][c]


def get_block_surfaces(i: int, j: int, k: int):
    p = [i, j, k]
    at_surfaces = [_p != 1 for _p in p]

    if not sum(at_surfaces):
        return

    surf_directions = [
        SURFACE_DIRECTIONS[axis][p[axis]]
        for axis, at_surface in enumerate(at_surfaces)
        if at_surface
    ]

    return [
        RubixCubeBlockSurface(
            color=INITIAL_COLORS[surf_direction],
            surface_norm_vec=NORMAL_VECTORS[surf_direction],
        )
        for surf_direction in surf_directions
    ]


def init_cube():
    cube = RubixCube()

    for i in range(3):
        for j in range(3):
            for k in range(3):
                block_surfaces = get_block_surfaces(i, j, k)
                if not block_surfaces:
                    continue

                cube[i, j, k] = RubixCubeBlock(block_surfaces)

    return cube


def solve(rot_instructions: list[str]):
    cube = init_cube()

    for rot_instruction in rot_instructions:
        surface, rot_d = rot_instruction
        cube.rotate(surface, rot_d == "+")

    rows = []
    for r in range(3):
        row = []
        for c in range(3):
            for surface in cube[0, r, c]:
                if surface.surface_norm_vec != NORMAL_VECTORS["U"]:
                    continue
                row.append(surface.color)
        rows.append("".join(row))

    return "\n".join(rows)


for _ in range(int(input())):
    n_rot = int(input())
    rot_instructions = input().split()

    print(solve(rot_instructions))
