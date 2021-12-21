# https://www.acmicpc.net/problem/14500
# timeout solution


class TetrominoSum:
    def __init__(self):
        self.size = list(map(int, input().split()))
        self.board = [list(map(int, input().split())) for _ in range(self.size[0])]
        self.templates = {
            "none": [((0, 0), (0, 1), (1, 0), (1, 1))],
            "rotate": [
                ((0, 0), (0, 1), (0, 2), (0, 3)),
                ((0, 0), (1, 0), (2, 0), (2, 1)),
                ((0, 0), (1, 0), (1, 1), (2, 1)),
                ((0, 0), (1, 0), (1, 1), (2, 0)),
                ((0, 0), (1, 0), (2, 0), (2, -1)),
                ((0, 0), (1, 0), (1, -1), (2, -1)),
                ((0, 0), (1, 0), (1, -1), (2, 0)),
            ],
        }

    def check_in_range(self, tetromino):
        for r, c in tetromino:
            if not (0 <= r < self.size[0] and 0 <= c < self.size[1]):
                return False
        return True

    def rotate_clockwise(self, template):
        pivot, *body = template
        return [pivot] + [(-c, r) for r, c in body]

    def fit_tetrominos(self, pivot):
        tetrominos = []
        for template in self.templates["none"]:
            if self.check_in_range(tetromino := [(r + pivot[0], c + pivot[1]) for r, c in template]):
                tetrominos.append(tetromino)
        for template in self.templates["rotate"]:
            for n_rot in range(4):
                if n_rot:
                    template = self.rotate_clockwise(template)
                if self.check_in_range(tetromino := [(r + pivot[0], c + pivot[1]) for r, c in template]):
                    tetrominos.append(tetromino)
        return tetrominos

    def sum(self, tetromino):
        return sum(self.board[r][c] for r, c in tetromino)

    def find(self):
        maxsum = 0
        for m, row in enumerate(self.board):
            for n, num in enumerate(row):
                if tetrominos := self.fit_tetrominos(pivot=(m, n)):
                    maxsum = max(maxsum, *map(self.sum, tetrominos))
        return maxsum

    @classmethod
    def maxsum(cls):
        return cls().find()


print(TetrominoSum.maxsum())
