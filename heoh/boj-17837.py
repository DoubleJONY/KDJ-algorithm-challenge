from __future__ import annotations
from typing import Generator

COLOR_WHITE = 0
COLOR_RED = 1
COLOR_BLUE = 2

DIR_RIGHT = 1
DIR_LEFT = 2
DIR_UP = 3
DIR_DOWN = 4

REVERSED_DIR = {
    DIR_RIGHT: DIR_LEFT,
    DIR_LEFT: DIR_RIGHT,
    DIR_UP: DIR_DOWN,
    DIR_DOWN: DIR_UP,
}


def main():
    N, K = map(int, input().split())
    A = [list(map(int, input().split())) for _ in range(N)]
    simulator = Simulator(N, A)
    for _ in range(K):
        r, c, d = map(int, input().split())
        simulator.add_piece(r, c, d)

    answer = simulator.simulate(limit=1000)
    if answer:
        print(answer)
    else:
        print(-1)


class Simulator(object):
    def __init__(self, N: int, A: list[int]) -> None:
        self.N: int
        self.color: int
        self.pieces: list[Piece]
        self.root: list[list[Piece]]

        self.N = N
        self.color = A
        self.pieces = []
        self.root = [[None] * (N+1) for _ in range(N+1)]

    def add_piece(self, r: int, c: int, d: int) -> None:
        piece = Piece(r, c, d)
        self.pieces.append(piece)
        self.put_down(piece, r, c)

    def simulate(self, limit: int) -> int:
        for t in range(1, limit+1):
            for piece in self.pieces:
                self.do_turn(piece)
                if self.count_pieces(piece.row, piece.col) >= 4:
                    return t
        return None

    def do_turn(self, piece: Piece) -> None:
        r, c = piece.row, piece.col
        nr, nc = self.get_next_pos(r, c, piece.dir)
        color = self.get_cell_color(nr, nc)
        if color == COLOR_WHITE:
            self.pick_up(piece)
            self.put_down(piece, nr, nc)
        elif color == COLOR_RED:
            self.pick_up(piece)
            piece = self.reverse(piece)
            self.put_down(piece, nr, nc)
        elif color == COLOR_BLUE:
            piece.dir = REVERSED_DIR[piece.dir]
            nr, nc = self.get_next_pos(r, c, piece.dir)
            if self.get_cell_color(nr, nc) != COLOR_BLUE:
                self.do_turn(piece)

    def get_next_pos(self, r: int, c: int, d: int) -> tuple[int, int]:
        nr = r + {DIR_UP: -1, DIR_DOWN: 1}.get(d, 0)
        nc = c + {DIR_LEFT: -1, DIR_RIGHT: 1}.get(d, 0)
        return nr, nc

    def get_cell_color(self, r: int, c: int) -> int:
        if 1 <= r <= self.N and 1 <= c <= self.N:
            return self.color[r-1][c-1]
        return COLOR_BLUE

    def pick_up(self, piece: Piece) -> None:
        r, c = piece.row, piece.col
        if self.root[r][c] is piece:
            self.root[r][c] = None
        else:
            lower = piece.lower
            piece.lower = None
            lower.upper = None

    def put_down(self, piece: Piece, r: int, c: int) -> None:
        for p in piece.iter_up():
            p.row = r
            p.col = c
        if self.root[r][c] is None:
            self.root[r][c] = piece
        else:
            lower = self.root[r][c].get_top()
            piece.lower = lower
            lower.upper = piece

    def reverse(self, bottom_piece: Piece) -> Piece:
        pieces = list(bottom_piece.iter_up())
        for piece in pieces:
            piece.upper, piece.lower = piece.lower, piece.upper
        return pieces[-1]

    def get_upper_sequence(self, piece: Piece) -> list[Piece]:
        pieces = []
        cur = piece
        while cur:
            pieces.append(cur)
            cur = cur.upper
        return pieces

    def count_pieces(self, r: int, c: int) -> int:
        root = self.root[r][c]
        return root.get_height() if root else 0


class Piece(object):
    def __init__(self, r: int, c: int, d: int) -> None:
        self.row: int
        self.col: int
        self.dir: int
        self.upper: Piece
        self.lower: Piece

        self.row = r
        self.col = c
        self.dir = d
        self.upper = None
        self.lower = None

    def __repr__(self) -> str:
        return f"Piece({self.row}, {self.col}, {self.dir}, {self.get_height()})"

    def get_height(self) -> int:
        if not self.upper:
            return 1
        return 1 + self.upper.get_height()

    def get_top(self) -> Piece:
        if not self.upper:
            return self
        return self.upper.get_top()

    def iter_up(self) -> Generator[Piece]:
        cur = self
        while cur:
            yield cur
            cur = cur.upper


main()
