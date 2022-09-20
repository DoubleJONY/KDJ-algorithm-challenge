#element : [shark, shark_head, shark_his, time]
# 1, 2, 3, 4는 각각 위, 아래, 왼쪽, 오른쪽
class SHARK_GAME:

    def __init__(self, n, nshark, k,  mapList, orderList, ) -> None:
        self.mapn = n
        self.k = k
        self.mapList = mapList 
        self.orderList = orderList 
        self.nshark = nshark
        self.dx = [-1, 1, 0, 0 ]
        self.dy = [0, 0, -1, 1] 

    def progress_(self):

        for i in range(self.mapn):
            for j in range(self.mapn):
                
                if self.mapList[i][j][-1] != 0 and self.mapList[i][j][2] != 0 and self.mapList[i][j][0] == 0:
                    self.mapList[i][j][-1] = self.mapList[i][j][-1] - 1
                    if self.mapList[i][j][-1] == 0:
                        self.mapList[i][j][2] = 0

                elif self.mapList[i][j][0] != 0 :
                    self.mapList[i][j][-1] = self.k
                    self.mapList[i][j][2] = self.mapList[i][j][0]
        # print(self.mapList)

    def move_(self):

        for m in range(1, len(self.orderList)+1):
            sx, sy = -1, -1

            for i in range(self.mapn):
                for j in range(self.mapn):
                    if self.mapList[i][j][0] == m:
                        sx = i
                        sy = j
                        # print(sx, sy)
                        break
            
            if sx == -1 or sy == -1:
                continue
            
            tmp_x, tmp_y, tmp_dh = [],[],[]
            for d in range(4):
                elementS = self.mapList[sx][sy]
                order_sh = self.orderList[elementS[0] - 1]
                dh = order_sh[elementS[1]-1]

                # print(m,"상어",dh, dh[d])
                dh = dh[d]
                
                # print("check x y : ",(sx + self.dx[dh-1]),(sy + self.dy[dh-1])  )
                
                if not(0<= (sx + self.dx[dh-1] ) < self.mapn and 0<= (sy + self.dy[dh-1] ) < self.mapn):
                    continue

                if self.mapList[sx + self.dx[dh-1]][sy + self.dy[dh-1]][2] == 0 :
                    elementS[1] = dh

                    if self.mapList[sx + self.dx[dh-1]][sy + self.dy[dh-1]][0] < elementS[0] and self.mapList[sx + self.dx[dh-1]][sy + self.dy[dh-1]][0] != 0:
                        self.mapList[sx][sy][0],self.mapList[sx][sy][1]  =  0, 0
                        self.nshark = self.nshark - 1
                    else:
                        self.mapList[sx + self.dx[dh-1]][sy + self.dy[dh-1]][0] = elementS[0]
                        self.mapList[sx + self.dx[dh-1]][sy + self.dy[dh-1]][1] = elementS[1]
                        self.mapList[sx][sy][0],self.mapList[sx][sy][1] = 0, 0
                    tmp_x, tmp_y, tmp_dh = [],[],[]
                    break
                elif self.mapList[sx + self.dx[dh-1]][sy + self.dy[dh-1]][2] == elementS[0]:
                    tmp_x.append(sx + self.dx[dh-1])
                    tmp_y.append(sy + self.dy[dh-1])
                    tmp_dh.append(dh)

            if tmp_x and tmp_y and tmp_dh:
                self.mapList[tmp_x[0]][tmp_y[0]][0] = self.mapList[sx][sy][0]
                self.mapList[tmp_x[0]][tmp_y[0]][1] = tmp_dh[0]
                self.mapList[sx][sy][0], self.mapList[sx][sy][1] = 0,0
                

            
n, m, k = map(int, input().split())
board = [[] for _ in range(n)]

for i in range(n):
    input_data = list(map(int, input().split()))

    board_row = []
    for j in range(n):
        board_row.append([input_data[j],0,0,0])

    board[i] = board_row         

shark_head = list(map(int, input().split()))
order = []
for i in range(m):
    temp = []
    for _ in range(4):
        temp.append(list(map(int, input().split())))
    order.append(temp)  

for sn in range(1,m+1):
    for i in range(n):
        for j in range(n):
            if board[i][j][0] == sn:
                board[i][j][1] = shark_head[sn - 1]
                break

# print(board)

shark1 = SHARK_GAME(n,m,k,board,order)

def play_game(game, nmax = 1000):
    game.progress_()
    for i in range(1,nmax+1):
        game.move_()
        game.progress_()
        if game.nshark == 1:
            return i

    return -1


print(play_game(shark1))


'''
5 4 4
0 0 0 0 3
0 2 0 0 0
1 0 0 0 4
0 0 0 0 0
0 0 0 0 0
4 4 3 1
2 3 1 4
4 1 2 3
3 4 2 1
4 3 1 2
2 4 3 1
2 1 3 4
3 4 1 2
4 1 2 3
4 3 2 1
1 4 3 2
1 3 2 4
3 2 1 4
3 4 1 2
3 2 4 1
1 4 2 3
1 4 2 3


'''