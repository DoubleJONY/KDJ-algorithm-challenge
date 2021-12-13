# https://www.acmicpc.net/problem/13460

from collections import deque
from dataclasses import dataclass
from typing import Optional

tilt = [(-1, 0), (0, -1), (1, 0), (0, 1)]


@dataclass
class Cell:
    BLANK = "."
    WALL = "#"
    RED = "R"
    BLUE = "B"
    GOAL = "O"


class Game:
    def __init__(self):
        row, col = map(int, input().split())
        self.board = [list(input()) for _ in range(row)]
        self.cache = set()

    def get_positions(self):
        positions = {}
        for p_row, row in enumerate(self.board):
            for p_col, cell in enumerate(row):
                if cell == Cell.RED:
                    positions[Cell.RED] = (p_row, p_col)
                elif cell == Cell.BLUE:
                    positions[Cell.BLUE] = (p_row, p_col)

        self.clear_board(**positions)
        return positions

    def set_positions(self, R, B):
        self.board[R[0]][R[1]] = Cell.RED
        self.board[B[0]][B[1]] = Cell.BLUE

    def clear_board(self, R, B):
        self.board[R[0]][R[1]] = Cell.BLANK
        self.board[B[0]][B[1]] = Cell.BLANK

    def add_to_cache(self, positions):
        self.cache.add((positions[Cell.RED], positions[Cell.BLUE]))

    def check_in_cache(self, positions):
        return (positions[Cell.RED], positions[Cell.BLUE]) in self.cache

    def check_ends(self, R, direction):
        return self.board[R[0] + direction[0]][R[1] + direction[1]] == Cell.GOAL

    def blue_in_hole(self, B, direction):
        return self.board[B[0] + direction[0]][B[1] + direction[1]] == Cell.GOAL

    @staticmethod
    def blue_follwoing(R, B, direction):
        return R == (B[0] + direction[0], B[1] + direction[1])

    @staticmethod
    def in_front(R, B, direction) -> Optional[str]:
        if direction[0] and R[1] == B[1]:
            if direction[0] > 0:
                return Cell.RED if R[0] > B[0] else Cell.BLUE
            else:
                return Cell.RED if R[0] < B[0] else Cell.BLUE

        if direction[1] and R[0] == B[0]:
            if direction[1] > 0:
                return Cell.RED if R[1] > B[1] else Cell.BLUE
            else:
                return Cell.RED if R[1] < B[1] else Cell.BLUE

    def positions_after_tilt(self, positions, direction):
        self.set_positions(**positions)

        in_front = self.in_front(**positions, direction=direction)
        first = Cell.RED if in_front is None else in_front
        second = Cell.RED if first == Cell.BLUE else Cell.BLUE

        drow, dcol = direction
        after_positions = {k: item for k, item in positions.items()}

        row, col = positions[first]
        while self.board[row + drow][col + dcol] == Cell.BLANK:
            row += drow
            col += dcol

        after_positions[first] = (row, col)

        self.clear_board(**positions)
        self.set_positions(**after_positions)

        row, col = positions[second]
        while self.board[row + drow][col + dcol] == Cell.BLANK:
            row += drow
            col += dcol

        self.clear_board(**after_positions)

        after_positions[second] = (row, col)
        return after_positions

    @classmethod
    def play(cls):
        game = cls()
        initial_positions = game.get_positions()

        q = deque([(initial_positions, None, 0)])
        while q:
            positions, prev_tilt, cnt = q.popleft()
            game.add_to_cache(positions)

            for n, direction in enumerate(tilt):
                if prev_tilt and n % 2 == prev_tilt % 2:
                    continue

                after_positions = game.positions_after_tilt(positions, direction)
                if game.check_ends(after_positions[Cell.RED], direction):
                    if not game.blue_follwoing(**after_positions, direction=direction):
                        return cnt + 1
                    continue

                if cnt > 9:
                    return -1

                if not game.check_in_cache(after_positions):
                    if not game.blue_in_hole(after_positions[Cell.BLUE], direction):
                        q.append((after_positions, n, cnt + 1))

        return -1


print(Game.play())
