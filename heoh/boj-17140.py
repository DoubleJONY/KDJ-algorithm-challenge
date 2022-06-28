import itertools
from collections import Counter


def main():
    r, c, k = map(int, input().split())
    A = [list(map(int, input().split())) for _ in range(3)]
    matrix = Matrix(A, Matrix.ORIENTATION_ROW)

    answer = simulate(matrix, r, c, k, limit=100)
    if answer is not None:
        print(answer)
    else:
        print(-1)

def simulate(matrix, r, c, k, limit):
    for t in range(limit+1):
        if matrix.get(r-1, c-1) == k:
            return t

        matrix = do_operation(matrix)

    return None

def do_operation(matrix):
    if matrix.rows >= matrix.cols:
        return do_row_operation(matrix)
    else:
        return do_col_operation(matrix)

def do_row_operation(matrix):
    result = []
    for r in range(matrix.rows):
        values = filter(lambda x: x != 0, [matrix.get(r, c) for c in range(matrix.cols)])
        counter = Counter(values)
        pairs = sorted(counter.items(), key=lambda x: x[1] << 20 | x[0])
        pairs = flatten(pairs)
        result.append(pairs)

    return Matrix(result, Matrix.ORIENTATION_ROW)

def do_col_operation(matrix):
    result = []
    for c in range(matrix.cols):
        values = filter(lambda x: x != 0, [matrix.get(r, c) for r in range(matrix.rows)])
        counter = Counter(values)
        pairs = sorted(counter.items(), key=lambda x: x[1] << 20 | x[0])
        pairs = flatten(pairs)
        result.append(pairs)

    return Matrix(result, Matrix.ORIENTATION_COL)

def flatten(seq):
    return list(itertools.chain.from_iterable(seq))

class Matrix:
    ORIENTATION_ROW = 0
    ORIENTATION_COL = 1

    def __init__(self, values, orient):
        self.values = values
        self.orientation = orient

        if self.orientation == Matrix.ORIENTATION_ROW:
            self.rows = len(self.values)
            self.cols = max(len(seq) for seq in self.values)
        else:
            self.cols = len(self.values)
            self.rows = max(len(seq) for seq in self.values)
        self.rows = min(self.rows, 100)
        self.cols = min(self.cols, 100)


    def get(self, r, c):
        if self.orientation == Matrix.ORIENTATION_ROW:
            if r < len(self.values) and c < len(self.values[r]):
                return self.values[r][c]
            else:
                return 0
        else:
            if c < len(self.values) and r < len(self.values[c]):
                return self.values[c][r]
            else:
                return 0


main()
