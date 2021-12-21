# https://www.acmicpc.net/problem/14499

from dataclasses import dataclass


@dataclass
class Face:
    UP = 1
    BOTTOM = 6
    LEFT = 4
    RIGHT = 3
    FRONT = 5
    REAR = 2


@dataclass
class Direction:
    EAST = 1
    WEST = 2
    NORTH = 3
    SOUTH = 4


rolls = {
    Direction.EAST: (Face.UP, Face.RIGHT, Face.BOTTOM, Face.LEFT, Face.UP),
    Direction.WEST: (Face.UP, Face.LEFT, Face.BOTTOM, Face.RIGHT, Face.UP),
    Direction.NORTH: (Face.UP, Face.REAR, Face.BOTTOM, Face.FRONT, Face.UP),
    Direction.SOUTH: (Face.UP, Face.FRONT, Face.BOTTOM, Face.REAR, Face.UP),
}
moves = {
    Direction.EAST: (0, 1),
    Direction.WEST: (0, -1),
    Direction.NORTH: (-1, 0),
    Direction.SOUTH: (1, 0),
}


class Dice:
    def __init__(self):
        self.state = {face: 0 for face in range(1, 7)}

    def roll(self, direction: Direction):
        cache = None
        for face in rolls[direction]:
            self.state[face], cache = cache, self.state[face]


class Game:
    def __init__(self):
        m, n, r, c, k = map(int, input().split())

        self.m = m
        self.n = n
        self.board = [list(map(int, input().split())) for _ in range(m)]
        self.sequences = list(map(int, input().split()))

        self.location = (r, c)
        self.dice = Dice()

    def in_bound(self, direction: Direction):
        return (
            0 <= self.location[0] + moves[direction][0] < self.m
            and 0 <= self.location[1] + moves[direction][1] < self.n
        )

    def _step(self, direction: Direction):
        self.dice.roll(direction)
        self.location = (self.location[0] + moves[direction][0], self.location[1] + moves[direction][1])

    def step(self, direction: Direction):
        if self.in_bound(direction):
            self._step(direction)
            r, c = self.location
            if self.board[r][c]:
                self.dice.state[Face.BOTTOM], self.board[r][c] = self.board[r][c], 0
            else:
                self.board[r][c] = self.dice.state[Face.BOTTOM]

            print(self.dice.state[Face.UP])

    @classmethod
    def play(cls):
        game = cls()
        for seq in game.sequences:
            game.step(seq)


Game.play()
