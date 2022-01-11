DY = [None, 0, 0, -1, 1]
DX = [None, 1, -1, 0, 0]
RIGHT = 1
LEFT = 2
UP = 3
DOWN = 4


class Dice:
    def __init__(self):
        self.numbers = [None, 0, 0, 0, 0, 0, 0]

    def get_top(self):
        return self.numbers[1]

    def get_bottom(self):
        return self.numbers[6]

    def copy_to_bottom(self, value):
        self.numbers[6] = value

    def move_up(self):
        v = self.numbers
        v[2], v[1], v[5], v[6] = v[1], v[5], v[6], v[2]

    def move_down(self):
        v = self.numbers
        v[2], v[1], v[5], v[6] = v[6], v[2], v[1], v[5]

    def move_left(self):
        v = self.numbers
        v[4], v[1], v[3], v[6] = v[1], v[3], v[6], v[4]

    def move_right(self):
        v = self.numbers
        v[4], v[1], v[3], v[6] = v[6], v[4], v[1], v[3]

N, M, y, x, K = map(int, input().split())
A = list()
for _ in range(N):
    A.append(list(map(int, input().split())))

def check_movable(y, x):
    return (0 <= y <= N-1) and (0 <= x <= M-1)

dice = Dice()

commands = list(map(int, input().split()))
for k in range(K):
    d = commands[k]
    nx = x + DX[d]
    ny = y + DY[d]
    if not check_movable(ny, nx):
        continue

    x = nx
    y = ny
    if d == RIGHT:
        dice.move_right()
    elif d == LEFT:
        dice.move_left()
    elif d == UP:
        dice.move_up()
    elif d == DOWN:
        dice.move_down()
    if A[y][x] == 0:
        A[y][x] = dice.get_bottom()
    else:
        dice.copy_to_bottom(A[y][x])
        A[y][x] = 0

    print(dice.get_top())
