
import copy
# move 8 direction


board = [[] for _ in range(4)]
for i in range(4):
    input_data = list(map(int, input().split()))

    fish_row = []
    for j in range(4):
        fish_row.append([input_data[2*j], input_data[2*j + 1] - 1])

    board[i] = fish_row

maxScore = 0

dy = [0, -1, -1, -1, 0, 1, 1, 1]
dx = [-1, -1, 0, 1, 1, 1, 0, -1]


def dfs(x_shark, y_shark, score, board):
    global maxScore

    score += board[x_shark][y_shark][0]
    board[x_shark][y_shark][0] = 0
    maxScore = max(maxScore, score)

    for i in range(1, 17):
        x, y = -1, -1
        for j in range(4):
            for k in range(4):
                if board[j][k] == i:
                    x = j, y = k
                    break

        if x == -1 or y == -1:
            continue

        direct = board[x][y][1]

        for j in range(8):
            tmp_d = (direct+j) % 8

            tmp_x = dx[tmp_d] + x
            tmp_y = dy[tmp_d] + y
            if not(0 <= tmp_x < 4 and 0 <= tmp_y < 4) or (tmp_x == x_shark, tmp_y == y_shark):
                continue

            board[x][y][1] = direct
            board[x][y], board[tmp_x][tmp_y] = board[tmp_x][tmp_y], board[x][y]

            break

    direct_shark = board[x_shark][y_shark][1]

    for i in range(1, 5):

        tmp_x = x_shark + dx[direct_shark]*i
        tmp_y = y_shark + dy[direct_shark]*i
        if (0 <= tmp_x < 4 and 0 <= tmp_y < 4) and board[x][y][0] > 0:
            dfs(tmp_x, tmp_y, score, copy.deepcopy(board))


print(board)
dfs(0, 0, 0, board)
print(maxScore)
