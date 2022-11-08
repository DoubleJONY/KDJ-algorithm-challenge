
import numpy as np


n, m = map(int, input().split())
board = np.array([list(map(int, input().split())) for _ in range(n)])



# 상하좌우
dx = [-1,1,0,0]
dy = [0,0,-1,1]

answer = 0



def gravity(board):
    temp = 0
    for c in range(n):
        for r in range(n - 1, -1, -1):
            
            cur_r = r
            while cur_r > 0 and board[cur_r][c] == -2:
                cur_r -= 1
            if cur_r != r and board[cur_r][c] != -1:
                temp = board[cur_r][c]
                board[cur_r][c] = -2
                board[r][c] = temp
    return board

while True:
    #기준블럭 : 블럭 좌표 리스트
    large_block = []
    large_rainbow = 0
    visited = [[0] *n for _ in range(n)]
    
    
    
    for i in range(n):
        for j in range(n):
            if visited[i][j] == 1:
                continue
            
            tmp_block = board[i][j]
            visited[i][j] = 1
            if tmp_block <= 0:
                continue
            tmp_group = [[i,j]]
            que = [[i,j]]
            tmp_rainbow = 0
            while que:
                i_, j_ = que.pop(0)
                for k in range(4):
                    tx, ty = i_ + dx[k], j_ + dy[k]
                    if (tx >= n or ty >= n or tx < 0 or ty < 0) and (board[tx][ty]==0 or board[tx][ty]==tmp_block ) and visited[tx][ty] == 0:
                        tmp_group.append([tx, ty])
                        visited[tx][ty] = 1
                        if board[tx][ty]==0:
                            tmp_rainbow += 1
                            visited[tx][ty] = -1
                            
                        que.append([tx, ty])
                        
            if len(large_block) < len(tmp_group):
                large_block = tmp_group
                large_rainbow = tmp_rainbow
                
            elif len(large_block) == len(tmp_group) and large_rainbow <= tmp_rainbow:
                large_block = tmp_group
                large_rainbow = tmp_rainbow
            
            for ri in range(n):
                for rj in range(n):
                    if visited[ri][rj] == -1:
                            visited[ri][rj] = 0
    if len(large_block) <= 1:
        break
    else:
        answer += len(large_block) ** 2
        
    for bx, by in large_block:
        board[bx][by] = -2
        
               
    gravity(board)
    np.rot90(board, 1)
    gravity(board)
    
print(answer)

