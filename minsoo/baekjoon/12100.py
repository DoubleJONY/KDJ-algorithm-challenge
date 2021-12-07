# https://www.acmicpc.net/problem/12100
# TBA


class Game:
    def __init__(self):
        self.n = int(input())
        self.board = [list(map(int, input().split())) for _ in range(self.n)]

    def rotate_clockwise(self):
        self.board = list(map(lambda x: x[::-1], zip(*self.board)))

    def merge(self):
        is_merged = False
        board = []
        for row in self.board:
            stack = []
            for num in row:
                while stack and num and num == stack[-1]:
                    is_merged = True
                    num *= 2
                    stack.pop()
                if num:
                    stack.append(num)
            board.append([0] * (self.n - len(stack)) + stack)
        self.board = board
        return is_merged

    def get_max(self):
        return max(max(row) for row in self.board)

    def find(self, cnt=0):
        if cnt > 5:
            return self.get_max()

        max_num = 0
        for rot in range(4):
            if rot:
                self.rotate_clockwise()
            if self.merge():
                prev_board = self.board.copy()
                max_num = max(max_num, self.find(cnt + 1))
                self.board = prev_board

        return max_num if max_num else self.get_max()

    @classmethod
    def play(cls):
        game = cls()
        return game.find()


print(Game.play())
