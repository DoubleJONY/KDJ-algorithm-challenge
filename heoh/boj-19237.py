UP = 1
DOWN = 2
LEFT = 3
RIGHT = 4

DY = [None, -1, 1, 0, 0]
DX = [None, 0, 0, -1, 1]

SHARK_ID = 0
SMELL_TIME = 1


class Shark:
    def __init__(self, id, r, c) -> None:
        self.id = id
        self.row = r
        self.col = c
        self.dir = None
        self.next_dirs = None


def main():
    global N, M, k, cells, sharks

    N, M, k = map(int, input().split())

    cells = [[(0, -1)] * N for _ in range(N)]
    sharks = {}

    for r in range(N):
        for c, v in enumerate(map(int, input().split())):
            if v > 0:
                sharks[v] = Shark(v, r, c)

    for i, v in enumerate(map(int, input().split()), 1):
        sharks[i].dir = v

    for i in range(1, M+1):
        sharks[i].next_dirs = {d: list(map(int, input().split())) for d in range(1,5)}

    answer = simulate()
    print(answer)


def simulate():
    global N, M, k, cells, sharks

    put_smells(cells, sharks, 0, k)

    for t in range(1, 1001):
        move_sharks(cells, sharks, t)
        put_smells(cells, sharks, t, k)
        kill_sharks(cells, sharks)
        if len(sharks) == 1:
            return t

    return -1


def put_smells(cells, sharks, t, k):
    for shark in sharks.values():
        r, c = shark.row, shark.col
        if cells[r][c][SMELL_TIME] < t or cells[r][c][SHARK_ID] >= shark.id:
            cells[r][c] = (shark.id, t + k)


def move_sharks(cells, sharks, t):
    global N
    for shark in sharks.values():
        move_shark(cells, shark, t)

def move_shark(cells, shark, t):
    global N
    r, c = shark.row, shark.col
    for d in shark.next_dirs[shark.dir]:
        nr = r + DY[d]
        nc = c + DX[d]
        if not (0 <= nr < N and 0 <= nc < N):
            continue
        if cells[nr][nc][SMELL_TIME] < t:
            shark.row = nr
            shark.col = nc
            shark.dir = d
            return
    for d in shark.next_dirs[shark.dir]:
        nr = r + DY[d]
        nc = c + DX[d]
        if not (0 <= nr < N and 0 <= nc < N):
            continue
        if cells[nr][nc][SHARK_ID] == shark.id:
            shark.row = nr
            shark.col = nc
            shark.dir = d
            return


def kill_sharks(cells, sharks):
    for shark in list(sharks.values()):
        r, c = shark.row, shark.col
        if cells[r][c][SHARK_ID] != shark.id:
            sharks.pop(shark.id)


main()
