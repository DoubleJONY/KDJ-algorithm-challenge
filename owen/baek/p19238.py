
from collections import deque
from copy import deepcopy
n, m, f = map(int, input().split())
board = [list(map(int, input().split())) for _ in range(n)]
tx, ty = map(int, input().split())
passenger_info = [list(map(int, input().split())) for _ in range(m)]


visited_ = [[float("-inf")]*n for _ in range(n)]

d = [[0, 1], [0, -1], [1, 0], [-1, 0]]  # 동서남북

# 손님 & 목적지 거리 계산


def bfs(sx, sy, visited):

    global n, d, board
    que = deque()
    que.append([sx, sy])
    visited[sx][sy] = 0

    while que:
        # print("q")
        nx, ny = que.popleft()

        for i in range(4):

            dx = nx + d[i][0]
            dy = ny + d[i][1]

            if (0 <= dx < n) and (0 <= dy < n) and visited[dx][dy] < -2:
                if board[dx][dy] == 0:  # 갈수있는땅
                    visited[dx][dy] = visited[nx][ny] + 1
                    que.append([dx, dy])

                # 갈수없는땅
                elif board[dx][dy] == 1:
                    visited[dx][dy] = -1

    return visited


def find_guest(psg, m, tx, ty, visited):
    # 같은거리면 행, 열 순으로 최소값
    # print("find guest tx ty: ", tx, ty)
    visited = bfs(tx, ty, visited)
    # print(visited)
    tmp_psg = []
    for i in range(m):
        px, py = psg[i][0], psg[i][1]
        desx, desy = psg[i][2], psg[i][3]
        dist = visited[px][py]
        if psg[i][-1] == float("inf"):
            dist = float("inf")
        if dist < 0:
            dist = float("inf")

        tmp_psg.append([px, py, desx, desy, dist])  # 승객 인덱스, 거리
    return sorted(tmp_psg, key=lambda x: (x[-1], x[0], x[1]))


def find_dst(passenger, visited):

    px, py, desx, desy = passenger[0], passenger[1], passenger[2], passenger[3]
    visited = bfs(px, py, visited)
    dist = visited[desx][desy]
    if dist == 0 or dist < -2:
        return -1
    return dist


def main(n, m, f, tx, ty, passenger_info, visited):
    cnt = m
    tx = tx - 1
    ty = ty - 1
    psg = [[passenger_info[i][0] - 1, passenger_info[i][1] - 1,
            passenger_info[i][2] - 1, passenger_info[i][3] - 1, 0] for i in range(len(passenger_info))]
    # print("information : ", psg)
    while cnt:
        # print(tx, ty)
        psg = find_guest(psg, m, tx, ty, deepcopy(visited))
        # print("psg: ", psg)
        if psg[0][0] == -1:
            return print(-1)
        f = f - psg[0][-1]
        # print("f: ", f)
        if f <= 0:
            return print(-1)
        dist = find_dst(psg[0], deepcopy(visited))
        # print("dist: ", dist)
        f = f - dist
        # print("f: ", f)
        if f < 0:
            return print(-1)
        f = f + dist * 2
        # print("f: ", f)
        tx, ty = psg[0][2], psg[0][3]
        cnt -= 1
        # print(cnt)
        psg[0][-1] = float("inf")

    return print(f)


if __name__ == "__main__":

    main(n, m, f, tx, ty, passenger_info, visited_)
