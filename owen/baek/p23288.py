n, m, k = map(int, input().split())
from collections import deque
#n*m
board = [list(map(int, input().split())) for _ in range(n)]
score_board = [ [0] * m for _ in range(n)]

# A > B인 경우 이동 방향을 90도 시계 방향으로 회전시킨다.
# A < B인 경우 이동 방향을 90도 반시계 방향으로 회전시킨다.
# A = B인 경우 이동 방향에 변화는 없다.
# 북동남서, A > B : +1, A < B : -1, A = B : 0
#칸이 없다면 반대로

dx = [-1,0,1,0]
dy = [0,1,0,-1]

dice = [[0,2,0,0],[4,1,3,6],[0,5,0,0],[0,6,0,0]]



def score_bfs(map_b, score_b):
    global n, m, dx, dy
    visit_ = set()

    for i in range(n):
        
        for j in range(m):
            
            if (i, j) not in visit_:
                que = deque([(i,j)])
                score_list = []
                target = map_b[i][j]
                while que:
                    qx, qy = que.popleft()
                    if (qx, qy) not in visit_ and map_b[qx][qy] == target:
                        visit_.add((qx,qy))
                        score_list.append([qx,qy])

                        for dir in range(len(dx)):
                            scx, scy = qx + dx[dir], qy + dy[dir]
                            if 0 <= scx < n and 0 <= scy < m and map_b[scx][scy] == target:
                                que.append((scx, scy))

                for tx, ty in score_list:
                    score_b[tx][ty] = target * len(score_list)


                    


    return score_b

def dice_(dice, dir):

    if dir  == 0:
        tmp_up = dice[0][1]
        dice[0][1] = dice[1][1]
        dice[1][1] = dice[2][1]
        dice[2][1] = dice[3][1]
        dice[3][1] = tmp_up
        dice[1][3]=dice[3][1]

    elif dir == 2:
        tmp_down = dice[-1][1]
        dice[3][1] = dice[2][1]
        dice[2][1] = dice[1][1]
        dice[1][1] = dice[0][1]
        dice[0][1] = tmp_down 
        dice[1][3]=dice[3][1]


    elif dir == 1:
        tmp_right = dice[1][-1]
        dice[1][3] = dice[1][2]
        dice[1][2] = dice[1][1]
        dice[1][1] = dice[1][0]
        dice[1][0] = tmp_right
        dice[3][1] = dice[1][3]



    elif dir == 3:
        tmp_left = dice[1][0]
        dice[1][0] = dice[1][1]
        dice[1][1] = dice[1][2]
        dice[1][2] = dice[1][3]
        dice[1][3] = tmp_left
        dice[3][1] = dice[1][3]

    return dice
    #동기화
    
def main(board,score_board, dice):
    global n, m, dx, dy, k
    answer = 0
    score_board = score_bfs(board, score_board)
    #start
    dir = 1
    bx, by = 0, 0
    bottom_num = dice[1][-1]
    for i in range(k):
        x, y = bx + dx[dir], by + dy[dir]
        if 0 <= x < n and 0<= y < m:
            answer += score_board[x][y]
            dice = dice_(dice, dir)

            
        else: #반대
            dir = (dir + 2) % len(dx)
            x, y = bx + dx[dir], by + dy[dir]
            answer += score_board[x][y]
            dice = dice_(dice, dir)


        
        bx, by = x, y
   
        bottom_num = dice[1][-1]

        if bottom_num > board[bx][by]:
            dir = (dir + 1) % len(dx)
        elif bottom_num < board[bx][by]:
            dir = (dir - 1) % len(dx)


    print(answer)


if __name__ == "__main__":
    main(board,score_board, dice)