
import copy

#       ←, ↖,   ↑,  ↗, →, ↘, ↓, ↙
f_dx = [0, -1, -1, -1, 0, 1, 1, 1]
f_dy = [-1, -1, 0, 1, 1, 1, 0, -1]
dx = [-1, 0, 1, 0]
dy = [0, -1, 0, 1]

m, s = map(int, input().split())
fish = [list(map(int, input().split())) for _ in range(m)]
board = [[[] for _ in range(4)] for _ in range(4)]
smell = [[0] * 4 for _ in range(4)]
for x, y, d in fish:
    board[x - 1][y - 1].append(d - 1)

shark = tuple(map(lambda x: int(x) - 1, input().split()))

def move_fish(temp):
    
    res = [[[] for _ in range(4)] for _ in range(4)]

    for x in range(4):
        for y in range(4):
            while temp[x][y]:
                d = temp[x][y].pop()
                for i in range(d, d - 8, -1):
                    i %= 8
                    nx, ny = x + f_dx[i], y + f_dy[i]
                    if (nx, ny) != shark and 0 <= nx < 4 and 0 <= ny < 4 and not smell[nx][ny]:
                        res[nx][ny].append(i)
                        break
                else:
                    res[x][y].append(d)
    return res


def dfs(temp, x, y, dep, cnt, visit):
   
    global max_eat, shark, eat

    if dep == 3:   
        if max_eat < cnt:
            max_eat = cnt
            shark = (x, y)
            eat = visit[:]
        return
    #상은 1, 좌는 2, 하는 3, 우는 4
    for d in range(4):
        nx = x + dx[d]
        ny = y + dy[d]
        if 0 <= nx < 4 and 0 <= ny < 4:
            if (nx, ny) not in visit: 
                visit.append((nx, ny))
                dfs(temp, nx, ny, dep + 1, cnt + len(temp[nx][ny]), visit)
                visit.pop()
            else:  
                dfs(temp, nx, ny, dep + 1, cnt, visit)



for _ in range(s):
    eat = list()
    max_eat = -1
    # 1. 모든 물고기 복제
    temp = copy.deepcopy(board)
    # 2. 물고기 이동
    temp = move_fish(temp)
    # 3. 상어이동 - 백트래킹
    dfs(temp, shark[0], shark[1],0, 0, list())
    for x, y in eat:
        if temp[x][y]:
            temp[x][y] = []
            smell[x][y] = 3   # 3번 돌아야 없어짐
    # 4. 냄새 사라짐 
    for i in range(4):
        for j in range(4):
            if smell[i][j]:
                smell[i][j] -= 1
    # 5. 복제 마법
    for i in range(4):
        for j in range(4):
            board[i][j] += temp[i][j]

# 물고기 수 구하기 
answer = 0
for i in range(4):
    for j in range(4):
        answer += len(board[i][j])

print(answer)