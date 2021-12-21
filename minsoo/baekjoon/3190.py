# https://www.acmicpc.net/problem/3190

from collections import deque

dp = [(0, 1), (1, 0), (0, -1), (-1, 0)]


class Game:
    def __init__(self):
        self.n = int(input())
        self.apples = set(tuple(map(int, input().split())) for _ in range(int(input())))
        self.turns = deque(
            [list(map(lambda x: x if x.isalpha() else int(x), input().split())) for _ in range(int(input()))]
        )
        self.snake = deque([(1, 1)])
        self.heading = 0
        self.time = 0
        self.next = None

    def can_move(self):
        self.next = (self.snake[-1][0] + dp[self.heading][0], self.snake[-1][1] + dp[self.heading][1])
        return 0 < self.next[0] <= self.n and 0 < self.next[1] <= self.n and self.next not in self.snake

    def rotate(self, direction):
        if direction == "D":
            self.heading = (self.heading + 1) % 4
        else:
            self.heading = (self.heading - 1) % 4

    def is_apple(self):
        return self.snake[-1] in self.apples

    def eat_apple(self):
        self.apples.remove(self.snake[-1])

    def step(self):
        if self.turns and self.turns[0][0] == self.time:
            self.rotate(self.turns.popleft()[1])

        if self.can_move():
            self.snake.append(self.next)
            if self.is_apple():
                self.eat_apple()
            else:
                self.snake.popleft()
            return True
        return False

    @classmethod
    def play(cls):
        game = cls()
        while game.step():
            game.time += 1
        return game.time + 1


print(Game.play())
