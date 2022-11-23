n, m = map(int, input().split())

board = [list(map(int, input().split())) for _ in range(n)]
mov = [list(map(int, input().split())) for _ in range(m)]

#2D 2 1D
loc2idx = {}
idx2loc = {}
ball1d = []
sx, sy = int((n+1)//2)-1, int((n+1)//2 )-1
#나선 좌하우상
#1,1,2,2,3,3,4,4
initx, inity = sx, sy
dx = [0,1,0,-1]
dy = [-1,0,1,0]
dir, cntDir = 0, 0
dist = 1
idx_cnt = 0
while len(ball1d) < n**2-1:

    for i in range(dist):
        
        initx = initx + dx[dir]
        inity = inity + dy[dir]
        
        
        
        loc2idx[(initx, inity)] = idx_cnt
        idx2loc[idx_cnt] = (initx, inity)
        ball1d.append( board[initx][inity])
        idx_cnt += 1
        if initx == 0 and inity == 0:
            break
        
    dir = (dir + 1) % 4
    cntDir += 1
    
        
    if cntDir % 2 == 0:
         dist += 1
         
         



answer = 0

#방향 거리
magic_dx = [-1,1,0,0]
magic_dy = [0,0,-1,1]

def hit_ball(ball1d):
    hit_cnt = ball1d.count(-1)
    ball1d = [ball for ball in ball1d if ball != -1]
    ball1d.extend([0]*hit_cnt)
    
    return ball1d
    
for i in range(m):
    mx, my = sx, sy
    magic_dir, magic_dist = mov[i]
    if magic_dist > n - int((n+1)//2):
        magic_dist = n - int((n+1)//2)
    
    for j in range(magic_dist):
        mx = mx + magic_dx[magic_dir - 1]
        my = my + magic_dy[magic_dir - 1]
        ball1d[loc2idx[(mx, my)]] = -1
        
    
       
    ball1d = hit_ball(ball1d)
    
    while True:
        ball_cnt = 0
        target_idx = 0
        for bidx in range(len(ball1d)):
            if ball1d[bidx] == ball1d[target_idx]:
                ball_cnt += 1
                
            else:
                if ball_cnt >= 4:
                    answer += ball1d[target_idx] * ball_cnt
                    
                    for k in range(target_idx, bidx):
                        ball1d[k] = -1
            
                    
                    
                ball_cnt = 1
                target_idx = bidx
        
        if ball1d.count(-1) == 0:
            break
        ball1d = hit_ball(ball1d)

    
    #increase
    increase_cnt = 0
    tmp_ball = []
    target_idx = 0
    ball_cnt = 0
    for bidx in range(len(ball1d)):
        if ball1d[bidx] == ball1d[target_idx]:
            ball_cnt += 1
            
        else:
            increase_cnt += 2
            if ball_cnt == 1:
                tmp_ball.append( [ball_cnt, ball1d[target_idx]])
                
            else:
                tmp_ball.append( [ball_cnt, ball1d[target_idx]])
                
            
            if increase_cnt > (n**2 - 1):
                break
            ball_cnt = 1
            target_idx = bidx
    if len(tmp_ball) < (n**2 - 1):
        for _ in range((n**2 - 1) - len(tmp_ball) ):
            tmp_ball.append([0])

    
    ball1d = []
    for ab in tmp_ball:
        ball1d.extend(ab)
    
    
    ball1d = hit_ball(ball1d)
    
            
            
print(answer)
        


