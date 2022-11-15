
n, m = map(int, input().split())
board = [list(map(int, input().split())) for _ in range(n)]
mov = [list(map(int, input().split())) for _ in range(m)]

dx = [0,-1,-1,-1,0,1,1,1]
dy = [-1,-1,0,1,1,1,0,-1]


#비바라기   (N, 1), (N, 2), (N-1, 1), (N-1, 2) %n

def copy_water_evapo(cloud_xy, board):
    #대각선 1,3,5,7
    global dx, dy
    
    
    for i in range(len(cloud_xy)):
        for j in [1,3,5,7]:
            
            tmp_x = cloud_xy[i][0] + dx[j]
            tmp_y = cloud_xy[i][1] + dy[j]
            
            if 0 <= tmp_x < n and 0 <= tmp_y < n and board[tmp_x][tmp_y] > 0:

                board[cloud_xy[i][0]][cloud_xy[i][1]] += 1
    
    next_cloud = []  
    for i in range(n):
        for j in range(n):
            if (i,j) not in cloud_xy and board[i][j] >= 2 :
                board[i][j] -= 2
                next_cloud.append((i,j))
                
          
    return board, next_cloud
            
    
def want_rain(n, direc, ntime, board, next_cloud):

    global dx, dy
    
    cloud_xy = []
    for x, y in next_cloud:
        cloud_xy.append(((x+dx[direc]*ntime)%n, (y+dy[direc]*ntime)%n ))
        
    for rx, ry in cloud_xy:
        board[rx][ry] += 1
    
            
   
    board, next_cloud = copy_water_evapo(cloud_xy, board)
    
    return board, next_cloud
   
def main(n, board, mov):
    next_cloud = [(n-1,0),(n-1,1),(n-2,0),(n-2,1)]
    for direc,ntime in mov:
        board, next_cloud = want_rain(n, direc-1, ntime, board, next_cloud)
       
    
    answer = 0
    for line in board:
        answer += sum(line)
    print(answer)

if __name__ == "__main__":
    main(n, board, mov)
    


