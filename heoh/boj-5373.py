UP = 0
DOWN = 1
FRONT = 2
BACK = 3
LEFT = 4
RIGHT = 5

char_to_direction = {
    'U': UP,
    'D': DOWN,
    'F': FRONT,
    'B': BACK,
    'L': LEFT,
    'R': RIGHT,
}


def main() -> None:
    T = int(input())

    for tc in range(T):
        n = int(input())
        commands = input().split()

        cube = Cube()

        for c in commands:
            direction = char_to_direction[c[0]]
            reverse = c[1] == '-'
            cube.rotate(direction, reverse)

        print(cube.get_plane(UP))


class Cube(object):
    def __init__(self) -> None:
        colors = ['w', 'y', 'r', 'o', 'g', 'b']
        self.cells = [Cube._create_plane(color) for color in colors]

    @staticmethod
    def _create_plane(color) -> list[list[str]]:
        return [[color] * 3 for _ in range(3)]

    def rotate(self, direction, reverse) -> None:
        plane = [
            (direction, 0, 0), (direction, 0, 1), (direction, 0, 2), (direction, 1, 2),
            (direction, 2, 2), (direction, 2, 1), (direction, 2, 0), (direction, 1, 0),
        ]
        belt = {
            UP: [
                (BACK, 0, 2), (BACK, 0, 1), (BACK, 0, 0),
                (RIGHT, 0, 2), (RIGHT, 0, 1), (RIGHT, 0, 0),
                (FRONT, 0, 2), (FRONT, 0, 1), (FRONT, 0, 0),
                (LEFT, 0, 2), (LEFT, 0, 1), (LEFT, 0, 0),
            ],
            DOWN: [
                (FRONT, 2, 0), (FRONT, 2, 1), (FRONT, 2, 2),
                (RIGHT, 2, 0), (RIGHT, 2, 1), (RIGHT, 2, 2),
                (BACK, 2, 0), (BACK, 2, 1), (BACK, 2, 2),
                (LEFT, 2, 0), (LEFT, 2, 1), (LEFT, 2, 2),
            ],
            FRONT: [
                (UP, 2, 0), (UP, 2, 1), (UP, 2, 2),
                (RIGHT, 0, 0), (RIGHT, 1, 0), (RIGHT, 2, 0),
                (DOWN, 0, 2), (DOWN, 0, 1), (DOWN, 0, 0),
                (LEFT, 2, 2), (LEFT, 1, 2), (LEFT, 0, 2),
            ],
            BACK: [
                (UP, 0, 2), (UP, 0, 1), (UP, 0, 0),
                (LEFT, 0, 0), (LEFT, 1, 0), (LEFT, 2, 0),
                (DOWN, 2, 0), (DOWN, 2, 1), (DOWN, 2, 2),
                (RIGHT, 2, 2), (RIGHT, 1, 2), (RIGHT, 0, 2),
            ],
            LEFT: [
                (UP, 0, 0), (UP, 1, 0), (UP, 2, 0),
                (FRONT, 0, 0), (FRONT, 1, 0), (FRONT, 2, 0),
                (DOWN, 0, 0), (DOWN, 1, 0), (DOWN, 2, 0),
                (BACK, 2, 2), (BACK, 1, 2), (BACK, 0, 2),
            ],
            RIGHT: [
                (UP, 2, 2), (UP, 1, 2), (UP, 0, 2),
                (BACK, 0, 0), (BACK, 1, 0), (BACK, 2, 0),
                (DOWN, 2, 2), (DOWN, 1, 2), (DOWN, 0, 2),
                (FRONT, 2, 2), (FRONT, 1, 2), (FRONT, 0, 2),
            ],
        }[direction]
        self._shift_cells(plane, reverse, 2)
        self._shift_cells(belt, reverse, 3)

    def _shift_cells(self, locs, reverse, delta):
        if reverse:
            locs = list(reversed(locs))

        src_colors = [self.get_cell(loc) for loc in locs]
        for src_index in range(len(locs)):
            dst_index = (src_index + delta) % len(locs)
            dst_loc = locs[dst_index]
            self.set_cell(dst_loc, src_colors[src_index])

    def get_cell(self, loc):
        d, r, c = loc
        return self.cells[d][r][c]

    def set_cell(self, loc, value):
        d, r, c = loc
        self.cells[d][r][c] = value

    def get_plane(self, direction) -> str:
        rows = [''.join(row) for row in self.cells[direction]]
        return '\n'.join(rows)


main()
