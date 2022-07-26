DESCRIPTION = """
첫째 줄에 체스판의 크기 N, 말의 개수 K가 주어진다.

둘째 줄부터 N개의 줄에 체스판의 정보가 주어진다.
체스판의 정보는 정수로 이루어져 있고, 각 정수는 칸의 색을 의미한다.
0은 흰색, 1은 빨간색, 2는 파란색이다.

다음 K개의 줄에 말의 정보가 1번 말부터 순서대로 주어진다.
말의 정보는 세 개의 정수로 이루어져 있고, 순서대로 행, 열의 번호, 이동 방향이다.
행과 열의 번호는 1부터 시작하고, 이동 방향은 4보다 작거나 같은 자연수이고 1부터 순서대로 →, ←, ↑, ↓의 의미를 갖는다.

같은 칸에 말이 두 개 이상 있는 경우는 입력으로 주어지지 않는다.

턴 한 번은 1번 말부터 K번 말까지 순서대로 이동시키는 것이다.
한 말이 이동할 때 위에 올려져 있는 말도 함께 이동한다.
말의 이동 방향에 있는 칸에 따라서 말의 이동이 다르며 아래와 같다.

A번 말이 이동하려는 칸이
- 흰색인 경우에는 그 칸으로 이동한다. 이동하려는 칸에 말이 이미 있는 경우에는 가장 위에 A번 말을 올려놓는다.
    - A번 말의 위에 다른 말이 있는 경우에는 A번 말과 위에 있는 모든 말이 이동한다.
    - 예를 들어, A, B, C로 쌓여있고, 이동하려는 칸에 D, E가 있는 경우에는 A번 말이 이동한 후에는 D, E, A, B, C가 된다.

- 빨간색인 경우에는 이동한 후에 A번 말과 그 위에 있는 모든 말의 쌓여있는 순서를 반대로 바꾼다.
    - A, B, C가 이동하고, 이동하려는 칸에 말이 없는 경우에는 C, B, A가 된다.
    - A, D, F, G가 이동하고, 이동하려는 칸에 말이 E, C, B로 있는 경우에는 E, C, B, G, F, D, A가 된다.

- 파란색인 경우에는 A번 말의 이동 방향을 반대로 하고 한 칸 이동한다.
    - 방향을 반대로 바꾼 후에 이동하려는 칸이 파란색인 경우에는 이동하지 않고 가만히 있는다.

- 체스판을 벗어나는 경우에는 파란색과 같은 경우이다.

턴이 진행되던 중에 말이 4개 이상 쌓이는 순간 게임이 종료된다.

link: https://www.acmicpc.net/problem/17837
"""

from dataclasses import dataclass

COLORS = {"W": 0, "R": 1, "B": 2}
DIRECTIONS = [(0, 1), (0, -1), (-1, 0), (1, 0)]


@dataclass
class Piece:
    r: int
    c: int
    d: int

    @property
    def forward(self):
        dr, dc = DIRECTIONS[self.d]
        return self.r + dr, self.c + dc

    def turn_around(self):
        self.d ^= 1

    def set_location(self, r: int, c: int):
        self.r = r
        self.c = c


class Chess:
    def __init__(
        self,
        color_properties: list[list[int]],
        piecies: list[Piece],
        board: list[list[list[int]]],
    ):
        self.color_properties = color_properties
        self.piecies = piecies
        self.board = board

    def step(self):
        def valid(r: int, c: int):
            return 0 <= r < len(self.board) and 0 <= c < len(self.board[0])

        for idx, piece in enumerate(self.piecies):
            r, c = piece.r, piece.c

            fr, fc = piece.forward
            color_forward = (
                self.color_properties[fr][fc] if valid(fr, fc) else COLORS["B"]
            )

            if color_forward == COLORS["B"]:
                piece.turn_around()
                fr, fc = piece.forward
                color_forward = (
                    self.color_properties[fr][fc]
                    if valid(fr, fc)
                    else COLORS["B"]
                )

            if color_forward == COLORS["B"]:
                continue

            stacked_piecies = self.board[r][c]

            start_idx = stacked_piecies.index(idx)
            remaining, moving = (
                stacked_piecies[:start_idx],
                stacked_piecies[start_idx:],
            )

            if color_forward == COLORS["R"]:
                moving = moving[::-1]

            self.board[r][c] = remaining

            for _idx in moving:
                self.piecies[_idx].set_location(fr, fc)
            self.board[fr][fc].extend(moving)

            if len(self.board[fr][fc]) > 3:
                return True

        return False

    @classmethod
    def from_stdin(cls, board_size: int, num_piecies: int):
        color_properties = [
            list(map(int, input().split())) for _ in range(board_size)
        ]
        board = [[[] for _ in row] for row in color_properties]

        def make_piece(r: int, c: int, d: int):
            return Piece(r - 1, c - 1, d - 1)

        piecies = [
            make_piece(*map(int, input().split())) for _ in range(num_piecies)
        ]
        for idx, piece in enumerate(piecies):
            board[piece.r][piece.c].append(idx)

        return cls(color_properties, piecies, board)


def play():
    chess = Chess.from_stdin(*map(int, input().split()))
    for t in range(1000):
        done = chess.step()
        if done:
            return t + 1

    return -1


print(play())
