EMPTY = 0

LEFT = 0
UP = 1
RIGHT = 2
DOWN = 3
DIRECTIONS = [LEFT, UP, RIGHT, DOWN]

DY = [0, -1, 0, 1]
DX = [-1, 0, 1, 0]


def solution(n, map):
    return dfs(n, map, 5)


def dfs(n, map, turn):
    if turn == 0:
        return find_max(n, map)

    best_case = 0
    for d in DIRECTIONS:
        next_map = move_map(n, map, d)
        case = dfs(n, next_map, turn-1)
        best_case = max(best_case, case)
    return best_case


def find_max(n, map):
    max_value = 0
    for r in range(n):
        for c in range(n):
            max_value = max(max_value, map[r][c])
    return max_value


def move_map(n, map, d):
    if d == LEFT:
        map = move_map_left(n, map)
    elif d == UP:
        map = rotate_map(n, map, 90)
        map = move_map_left(n, map)
        map = rotate_map(n, map, 270)
    elif d == RIGHT:
        map = rotate_map(n, map, 180)
        map = move_map_left(n, map)
        map = rotate_map(n, map, 180)
    elif d == DOWN:
        map = rotate_map(n, map, 270)
        map = move_map_left(n, map)
        map = rotate_map(n, map, 90)
    return map


def move_map_left(n, map):
    new_map = []
    for r in range(n):
        new_row = move_row_left(n, map[r])
        new_map.append(new_row)
    return new_map


def move_row_left(n, row):
    new_row = [0] * n

    dst = 0
    for col in range(n):

        block_is_exists = row[col] != EMPTY
        if not block_is_exists:
            continue

        same_number = new_row[dst] == row[col]
        if new_row[dst] == EMPTY:
            new_row[dst] = row[col]
        elif same_number:
            new_row[dst] += row[col]
            dst += 1
        else:
            dst += 1
            new_row[dst] = row[col]

    return new_row


def rotate_map(n, map, angle):
    rotations = angle // 90
    for _ in range(rotations):
        map = rotate_map_clockwise(n, map)
    return map


def rotate_map_clockwise(n, map):
    new_map = make_map(n, 0)
    for r in range(n):
        for c in range(n):
            new_map[c][(n-1)-r] = map[r][c]
    return new_map


def make_map(n, initial_value):
    new_map = []
    for _ in range(n):
        new_row = [initial_value] * n
        new_map.append(new_row)
    return new_map


def main():
    N = int(input())
    
    map = []
    for _ in range(N):
        row = [int(x) for x in input().split()]
        map.append(row)

    answer = solution(N, map)
    print(answer)


if __name__ == '__main__':
    main()
