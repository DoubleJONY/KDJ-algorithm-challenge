# 종료 조건
def Check(y,x):
    # 맵 밖을 벗어나거나, 자기자신의 몸과 부딪히는 경우
    if y < 0  or y >= N or x < 0 or x >= N or MyMap[y][x] == 1:
        return True
    return False

N = int(input())
K = int(input())
MyMap = [[0]*N for _ in range(N)]

for i in range(K):
    y,x = map(int, input().split())
    MyMap[y-1][x-1] = 2
MyMap[0][0] = 1

L = int(input())
Time_Table = [-1] * 10000
for _ in range(L):
    idx, turn = input().split()
    idx = int(idx)
    if turn == 'L': turn = 0
    else: turn = 1
    Time_Table[idx] = turn

# 상 하 좌 우
dy = [-1, 1, 0, 0]
dx = [0, 0, -1, 1]

# 상 -> L:좌 / D: 우
# 하 -> L:우 / D: 좌
# 좌 -> L:하 / D: 상
# 우 -> L:상 / D: 하
direction = [[2, 3], [3, 2], [1, 0], [0, 1]]

current_dir_idx, time, y, x = 3, 0, 0, 0
snack = [[0,0]]
# 꼬리~머리 순서

while True:

    time += 1
    n_y = y + dy[current_dir_idx]
    n_x = x + dx[current_dir_idx]

    # 종료 조건 검증
    if Check(n_y, n_x):
        break

    # 사과가 있는 경우 => 꼬리 위치 불변 & 사과 좌표 값을 뱀으로 변경(좌표 추가)
    if MyMap[n_y][n_x] == 2:
        MyMap[n_y][n_x] = 1
        snack.append([n_y, n_x])

    # 사과가 없는 경우 => 꼬리 좌표 제거 & 이동한 좌표 추가
    elif MyMap[n_y][n_x] == 0:
        MyMap[n_y][n_x] = 1
        snack.append([n_y, n_x])
        delete_y, delete_x = snack.pop(0)
        MyMap[delete_y][delete_x] = 0
    # 시간 증가, 기준 좌표값 업데이트

    y, x = n_y, n_x

    # 시간이 끝난 후, 방향 전환 검증 & 방향 전환
    if Time_Table[time] != -1:
        current_dir_idx = direction[current_dir_idx][Time_Table[time]]

print(time)