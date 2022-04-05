# https://www.acmicpc.net/problem/14891

gears = [input() for _ in range(4)]


def is_left_rotatable(n):
    if n == 0:
        return False
    return gears[n - 1][2] != gears[n][6]


def is_right_rotatable(n):
    if n == 3:
        return False
    return gears[n][2] != gears[n + 1][6]


def rotate(n, d, depth=0):
    if depth <= 0 and is_left_rotatable(n):
        rotate(n - 1, -d, depth - 1)
    if depth >= 0 and is_right_rotatable(n):
        rotate(n + 1, -d, depth + 1)
    gears[n] = gears[n][-d:] + gears[n][:-d]


def evaluate():
    return sum(int(gear[0]) * 2 ** n for n, gear in enumerate(gears))


for _ in range(int(input())):
    n, d = map(int, input().split())
    rotate(n - 1, d)

print(evaluate())
