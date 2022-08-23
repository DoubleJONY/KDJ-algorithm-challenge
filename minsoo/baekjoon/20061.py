# https://www.acmicpc.net/problem/20061

from enum import Enum
from dataclasses import dataclass, field


class BlockType(Enum):
    SQUARE = 1
    HORIZONTAL = 2
    VERTICAL = 3


@dataclass
class MonominoDomino:
    num_rows: int
    num_cols: int
    num_green_offset_rows: int = field(default=2)
    num_green_rows: int = field(default=None)
    num_blue_offset_cols: int = field(default=2)
    num_blue_cols: int = field(default=None)

    green: list[list[int]] = field(init=False)
    blue: list[list[int]] = field(init=False)
    score: int = field(init=False)

    def __post_init__(self):
        if self.num_green_rows is None:
            self.num_green_rows = self.num_rows
        if self.num_blue_cols is None:
            self.num_blue_cols = self.num_cols

        self.green = [
            [0 for _ in range(self.num_cols)]
            for _ in range(self.num_green_rows + self.num_green_offset_rows)
        ]
        self.blue = [
            [0 for _ in range(self.num_rows)]
            for _ in range(self.num_blue_cols + self.num_blue_offset_cols)
        ]

        self.score = 0

    def step(self, t: int, r: int, c: int):
        self._locate(t, r, c)
        self._cancel()
        self._offset()

    def _locate(self, t: int, r: int, c: int):
        self.green = locate_block(self.green, BlockType(t), c)

        def rotate_btype(btype: BlockType):
            if btype == BlockType.SQUARE:
                return btype
            if btype == BlockType.HORIZONTAL:
                return BlockType.VERTICAL
            return BlockType.HORIZONTAL

        self.blue = locate_block(self.blue, rotate_btype(BlockType(t)), r)

    def _cancel(self):
        self.green, num_green_cancel = cancel_rows(self.green)
        self.blue, num_blue_cancel = cancel_rows(self.blue)

        self.score += num_green_cancel + num_blue_cancel

    def _offset(self):
        self.green = offset_rows(self.green, self.num_green_offset_rows)
        self.blue = offset_rows(self.blue, self.num_blue_offset_cols)

    def get_status(self):
        return dict(
            score=self.score,
            **{
                color: count_blocks(getattr(self, color))
                for color in ["green", "blue"]
            },
        )


def locate_block(board: list[list[int]], btype: BlockType, c: int):
    lowest_r = 0

    def condition(row: list[int]):
        if btype == BlockType.HORIZONTAL:
            return not (row[c] or row[c + 1])
        return not row[c]

    for ridx, row in enumerate(board):
        if condition(row):
            lowest_r = ridx
            continue
        break

    def make_block():
        if btype == BlockType.SQUARE:
            return [(lowest_r, c)]
        if btype == BlockType.HORIZONTAL:
            return [(lowest_r, c), (lowest_r, c + 1)]
        return [(lowest_r - 1, c), (lowest_r, c)]

    for _r, _c in make_block():
        board[_r][_c] = 1

    return board


def cancel_rows(board: list[list[int]]):
    ridx = len(board) - 1
    num_cols = len(board[0])

    num_cancel = 0
    while not ridx < 0:
        if sum(board[ridx]) == num_cols:
            board = delete_row(board, ridx)
            num_cancel += 1
            continue
        ridx -= 1

    return board, num_cancel


def offset_rows(board: list[list[int]], num_offset: int):
    cnt = sum(sum(board[ridx]) > 0 for ridx in range(num_offset))
    last_ridx = len(board) - 1
    for _ in range(cnt):
        board = delete_row(board, last_ridx)

    return board


def delete_row(board: list[list[int]], ridx: int):
    num_cols = len(board[0])
    board.pop(ridx)
    board = [[0 for _ in range(num_cols)]] + board

    return board


def count_blocks(board: list[list[int]]):
    return sum(sum(row) for row in board)


def main():
    game = MonominoDomino(4, 4)
    for _ in range(int(input())):
        game.step(*map(int, input().split()))

    status = game.get_status()
    print(f"{status['score']}\n{status['green'] + status['blue']}")


if __name__ == "__main__":
    main()
