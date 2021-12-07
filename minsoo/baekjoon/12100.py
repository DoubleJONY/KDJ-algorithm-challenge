# https://www.acmicpc.net/problem/12100


class Game:
    def __init__(self):
        self.n = int(input())
        self.board = [list(map(int, input().split())) for _ in range(self.n)]

    def rotate_clockwise(self):
        self.board = list(map(lambda x: list(x[::-1]), zip(*self.board)))

    def contract(self):
        is_contracted = False
        board = []
        for row in self.board:
            stack = []
            copied_row = row.copy()
            while copied_row:
                num = copied_row.pop()
                if stack and stack[-1][1] and num == stack[-1][0]:
                    stack[-1][0] *= 2
                    stack[-1][1] = False
                elif num:
                    stack.append([num, True])

            if stack:
                stack, _ = zip(*stack[::-1])

            board.append([0] * (self.n - len(stack)) + list(stack))
            is_contracted |= board[-1] != row

        self.board = board
        return is_contracted

    def get_max(self):
        return max(max(row) for row in self.board)

    def find(self, cnt=0):
        if cnt > 4:
            return self.get_max()

        max_num = 0
        for rot in range(4):
            if rot:
                self.rotate_clockwise()
            prev_board = self.board.copy()
            if self.contract():
                max_num = max(max_num, self.find(cnt + 1))
                self.board = prev_board

        return max_num if max_num else self.get_max()

    @classmethod
    def play(cls):
        return cls().find()


print(Game.play())
