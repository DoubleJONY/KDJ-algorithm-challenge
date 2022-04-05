# 첫째 줄에 세로 크기 N과 가로 크기 M이 주어진다. (3 ≤ N, M ≤ 50)

# 둘째 줄에 로봇 청소기가 있는 칸의 좌표 (r, c)와 바라보는 방향 d가 주어진다. d가 0인 경우에는 북쪽을, 1인 경우에는 동쪽을, 2인 경우에는 남쪽을, 3인 경우에는 서쪽을 바라보고 있는 것이다.

# 셋째 줄부터 N개의 줄에 장소의 상태가 북쪽부터 남쪽 순서대로, 각 줄은 서쪽부터 동쪽 순서대로 주어진다. 빈 칸은 0, 벽은 1로 주어진다. 지도의 첫 행, 마지막 행, 첫 열, 마지막 열에 있는 모든 칸은 벽이다.

# 로봇 청소기가 있는 칸의 상태는 항상 빈 칸이다.

size = [11,10]
coord_direct=[7, 4, 0]
map = [[1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
       [1, 0, 0, 0, 0, 0, 0, 0, 0, 1],
       [1, 0, 0, 0, 1, 1, 1, 1, 0, 1],
       [1, 0, 0, 1, 1, 0, 0, 0, 0, 1],
       [1, 0, 1, 1, 0, 0, 0, 0, 0, 1],
       [1, 0, 0, 0, 0, 0, 0, 0, 0, 1],
       [1, 0, 0, 0, 0, 0, 0, 1, 0, 1],
       [1, 0, 0, 0, 0, 0, 1, 1, 0, 1],
       [1, 0, 0, 0, 0, 0, 1, 1, 0, 1],
       [1, 0, 0, 0, 0, 0, 0, 0, 0, 1],
       [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]]



def turn_left(direct):
    # 0인 경우에는 북쪽을, 1인 경우에는 동쪽을, 2인 경우에는 남쪽을, 3인 경우에는 서쪽
    return (direct + 1) % 4

def check_space(coord, direct):
    
    if direct == 0:
        
        
        
        
        
DIRS = [
    (-1, 0),
    (0, 1),
    (1, 0),
    (0, -1),
]

N, M = map(int, input().split())
r, c, d = map(int, input().split())

arr = [list(map(int, input().split())) for _ in range(N)]
answer = 0

state = 1
rotate_count = 0
while True:
    if state == 1:
        if arr[r][c] == 0:
            arr[r][c] = -1
            answer += 1
        state = 2
        rotate_count = 0
        continue
    if state == 2:
        drc = DIRS[d]
        if rotate_count == 4 and arr[r-drc[0]][c-drc[1]] == 1:
            break
        if rotate_count == 4:
            r -= drc[0]
            c -= drc[1]
            state = 2
            rotate_count = 0
            continue

        d -= 1
        if d < 0:
            d += 4
        rotate_count += 1
        drc = DIRS[d]
        if arr[r+drc[0]][c+drc[1]] != 0:
            continue
        else:
            r += drc[0]
            c += drc[1]
            state = 1
            rotate_count = 0
            continue

print(answer)
        