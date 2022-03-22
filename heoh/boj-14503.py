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
