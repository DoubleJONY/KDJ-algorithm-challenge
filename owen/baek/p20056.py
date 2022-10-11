# nxn
# 1 -> n, n -> 1
# overlap---------------
# m = sum(file balls m) / 5
# s = sum(file balls s) / num(file balls)
# every direction odd even -> 0246 / otherwise 1357
# del 0 m

#ri, ci, mi, si, di
from copy import deepcopy
N,M,K = map(int, input().split())
board = [[[] for _ in range(N)] for _ in range(N)]

for _ in range(M):
    r,c,m,s,d = map(int, input().split())
    if m!=0:
        board[r-1][c-1].append([m,s,d])

dir = [[-1,0],[-1,1],[0,1],[1,1],[1,0],[1,-1],[0,-1],[-1,-1]]

for _ in range(K):
    tmp_board = [[[] for _ in range(N)] for _ in range(N)]

    for i in range(N):
        for j in range(N):
            if board[i][j] != []:
                for f in range(len(board[i][j])):
                    nm,ns,nd = board[i][j][f]
                    nx,ny = (i + dir[nd][0] * ns)%N,  (j + dir[nd][1] * ns)%N
                    tmp_board[nx][ny].append([nm,ns,nd])

    for i in range(N):
        for j in range(N):
            if len(tmp_board[i][j]) > 1:
                tmp_m,tmp_s,tmp_d = 0,0,[]
                cnt = len(tmp_board[i][j])
                for t in range(cnt):
                    tmp_m += tmp_board[i][j][t][0]
                    tmp_s += tmp_board[i][j][t][1]
                    tmp_d.append(tmp_board[i][j][t][2]%2)
                tmp_m = int(tmp_m/5)
                tmp_s = int(tmp_s/cnt)
                tmp_board[i][j] = []
                if tmp_m != 0:
                    if sum(tmp_d) == 0 or sum(tmp_d) == cnt:
                        for sd in range(4):
                            tmp_board[i][j].append([tmp_m,tmp_s,sd * 2])

                    else:
                        for sd in range(4):
                            tmp_board[i][j].append([tmp_m,tmp_s,sd * 2+1])


    board = deepcopy(tmp_board)

answer = 0
for i in range(N):
    for j in range(N):
        if board[i][j] != []:
            for f in range(len(board[i][j])):
                answer += board[i][j][f][0]

print(answer)