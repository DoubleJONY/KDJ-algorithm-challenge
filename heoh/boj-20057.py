LEFT = 0
DOWN = 1
RIGHT = 2
UP = 3
DY = [0, 1, 0, -1]
DX = [-1, 0, 1, 0]
DIRS = [LEFT, DOWN, RIGHT, UP]

def make_wind_map():
    wind_map = {}
    for facing in DIRS:
        forward = facing
        left = (facing + 1) % len(DIRS)
        backward = (facing + 2) % len(DIRS)
        right = (facing + 3) % len(DIRS)

        wind_map[facing] = {
            (DY[forward] * 2, DX[forward] * 2): 0.05,
            (DY[forward] + DY[left], DX[forward] + DX[left]): 0.10,
            (DY[forward] + DY[right], DX[forward] + DX[right]): 0.10,
            (DY[left], DX[left]): 0.07,
            (DY[right], DX[right]): 0.07,
            (DY[left] * 2, DX[left] * 2): 0.02,
            (DY[right] * 2, DX[right] * 2): 0.02,
            (DY[backward] + DY[left], DX[backward] + DX[left]): 0.01,
            (DY[backward] + DY[right], DX[backward] + DX[right]): 0.01,
        }
    return wind_map

WIND_MAP = make_wind_map()


def main():
    N = int(input())
    A = [list(map(int, input().split())) for _ in range(N)]

    answer = 0

    for (y, x, facing) in tornado(N):
        org = A[y][x]
        remaining = org
        A[y][x] = 0

        for (dy, dx), ratio in WIND_MAP[facing].items():
            ny = y + dy
            nx = x + dx
            moving = int(org * ratio)
            remaining -= moving
            if (0 <= ny < N) and (0 <= nx < N):
                A[ny][nx] += moving
            else:
                answer += moving

        ny = y + DY[facing]
        nx = x + DX[facing]
        moving = remaining
        if (0 <= ny < N) and (0 <= nx < N):
            A[ny][nx] += moving
        else:
            answer += moving

    print(answer)


def tornado(n):
    facing = LEFT
    y = n // 2
    x = n // 2
    count = 1
    turn_count = 1
    length = 1
    remaining = 1
    while count < n*n:
        y += DY[facing]
        x += DX[facing]
        yield (y, x, facing)
        count += 1
        remaining -= 1
        if remaining == 0:
            facing = (facing + 1) % len(DIRS)
            turn_count -= 1
            remaining = length
            if turn_count == 0:
                turn_count = 2
                length += 1


main()
