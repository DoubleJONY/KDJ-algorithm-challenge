ROW = 0
COL = 1
SPEED = 2
DIRECTION = 3
SIZE = 4

UP = 1
DOWN = 2
RIGHT = 3
LEFT = 4


def main():
    R, C, M = map(int, input().split())
    sharks = []
    for _ in range(M):
        r, c, s, d, z = map(int, input().split())
        shark = [r, c, s, d, z]
        sharks.append(shark)

    score = 0
    for i in range(1, C+1):
        shark = pop_nearest_shark(sharks, i)
        # print(shark)
        if shark:
            score += shark[SIZE]
        sharks = update(R, C, sharks)
        # print(sharks)

    print(score)


def pop_nearest_shark(sharks, col):
    nearest_shark = None
    for shark in sharks:
        if (shark[COL] == col) and ((nearest_shark is None) or (shark[ROW] < nearest_shark[ROW])):
            nearest_shark = shark

    if nearest_shark:
        sharks.remove(nearest_shark)
        return nearest_shark
    else:
        return None


def update(R, C, sharks):
    move_sharks(R, C, sharks)
    shark_map = get_biggest_shark_map(sharks)
    filtered_sharks = list(shark_map.values())
    return filtered_sharks


def move_sharks(R, C, sharks):
    for shark in sharks:
        move_shark(R, C, shark)


def move_shark(R, C, shark):
    r, c, s, d, z = shark
    if d == LEFT or d == RIGHT:
        shark[COL], reversed = move_axis(C, shark[COL], shark[SPEED], {RIGHT: 1, LEFT: -1}[shark[DIRECTION]])
        if reversed:
            shark[DIRECTION] = {RIGHT: LEFT, LEFT: RIGHT}[shark[DIRECTION]]
    else:
        shark[ROW], reversed = move_axis(R, shark[ROW], shark[SPEED], {DOWN: 1, UP: -1}[shark[DIRECTION]])
        if reversed:
            shark[DIRECTION] = {DOWN: UP, UP: DOWN}[shark[DIRECTION]]


def move_axis(n, i, speed, direction):
    delta = speed % ((n-1) * 2)
    delta *= direction
    i += delta
    reversed = False
    while True:
        if i > n:
            i  = n - (i - n)
            reversed = not reversed
        elif i < 1:
            i = 1 + (1 - i)
            reversed = not reversed
        else:
            break
    return i, reversed


def get_biggest_shark_map(sharks):
    shark_map = {}
    for shark in sharks:
        r, c, s, d, z = shark
        p = (r, c)
        if p not in shark_map:
            shark_map[p] = shark
            continue
        if z > shark_map[p][SIZE]:
            shark_map[p] = shark
    return shark_map


main()
